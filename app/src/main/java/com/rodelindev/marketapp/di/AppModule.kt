package com.rodelindev.marketapp.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.rodelindev.marketapp.data.remote.RemoteService
import com.rodelindev.marketapp.data.database.AppDatabase
import com.rodelindev.marketapp.data.database.CategoryDao
import com.rodelindev.marketapp.data.database.ProductDao
import com.rodelindev.marketapp.data.datasource.*
import com.rodelindev.marketapp.data.repositories.UserRepository
import com.rodelindev.marketapp.domain.usescases.UseCaseLogin
import com.rodelindev.marketapp.domain.usescases.authenticate_user.AuthenticateUser
import com.rodelindev.marketapp.domain.usescases.validate_email.ValidateEmail
import com.rodelindev.marketapp.domain.usescases.validate_password.ValidatePassword
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRemoteService() : RemoteService {
        return  Retrofit.Builder()
            .baseUrl("http://35.169.242.154:3000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideUserDataSource(sharedPreferences: SharedPreferences, remoteService: RemoteService) : UserDataSource{
        return UserDataSourceImp(sharedPreferences,remoteService)
    }

    @Provides
    @Singleton
    fun provideNewShoppingDataSource(sharedPreferences: SharedPreferences, remoteService: RemoteService) : NewShoppingDataSource{
        return NewShoppingDataSourceImp(sharedPreferences,remoteService)
    }

    @Provides
    @Singleton
    fun provideGenderDataSource(remoteService: RemoteService) : GenderDataSource{
        return GenderDataSourceImp(remoteService)
    }

    @Provides
    @Singleton
    fun provideCategoryDataSource(remoteService: RemoteService, sharedPreferences: SharedPreferences) : CategoryDataSource{
        return CategoryDataSourceImp(remoteService,sharedPreferences)
    }

    @Provides
    @Singleton
    fun provideCategoryLocalDataSource(categoryDao: CategoryDao) : CategoryLocalDataSource{
        return CategoryLocalDataSourceImp(categoryDao)
    }

    @Provides
    @Singleton
    fun provideProductDataSource(remoteService: RemoteService, sharedPreferences: SharedPreferences) : ProductDataSource{
        return ProductDataSourceImp(remoteService,sharedPreferences)
    }

    @Provides
    @Singleton
    fun provideUseCasesLogin(userRepository: UserRepository): UseCaseLogin{
        return UseCaseLogin(
            validateEmail = ValidateEmail(),
            validatePassword = ValidatePassword(),
            authenticateUser = AuthenticateUser(userRepository)
        )
    }

    @Provides
    @Singleton
    fun provideSharePreferences(@ApplicationContext context:Context) : SharedPreferences{

        val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

        return EncryptedSharedPreferences.create(
            "PREFERENCES_TOKEN",
            masterKeyAlias,
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context : Context) : AppDatabase = Room.databaseBuilder(
        context.applicationContext,
        AppDatabase::class.java,
        "BDMarket"
    ).build()

    @Provides
    @Singleton
    fun provideDao(db:AppDatabase) : CategoryDao = db.categoryDao()

    @Provides
    @Singleton
    fun provideProductDao(db:AppDatabase) : ProductDao = db.productDao()

    @Provides
    @Singleton
    fun provideProductLocalDataSource(productDao: ProductDao) : ProductLocalDataSource{
        return ProductLocalDataSourceImp(productDao)
    }

}