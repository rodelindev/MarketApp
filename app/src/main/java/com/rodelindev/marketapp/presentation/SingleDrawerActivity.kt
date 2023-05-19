package com.rodelindev.marketapp.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.rodelindev.marketapp.R
import com.rodelindev.marketapp.databinding.ActivitySingleDrawerBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SingleDrawerActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySingleDrawerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySingleDrawerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imgMenu.setBackgroundResource(R.drawable.icon_menu)

        val navController = Navigation.findNavController(this, R.id.menuHostFragment)
        NavigationUI.setupWithNavController(binding.navigationView, navController)

        binding.imgMenu.setOnClickListener {

            if (navController.currentDestination?.label == "fragment_categories" ||
                navController.currentDestination?.label == "fragment_location"
            ) {
                binding.drawerLayout.openDrawer(GravityCompat.START)
            } else {
                //TODO Revisar onBackPressed deprecado, verificar
                onBackPressed()
            }
        }

        navController.addOnDestinationChangedListener(NavController.OnDestinationChangedListener { controller, destination, arguments ->
            when (destination.label) {
                "fragment_categories" -> {
                    binding.imgMenu.setBackgroundResource(R.drawable.icon_menu)
                }

                "fragment_product" -> {
                    binding.imgMenu.setBackgroundResource(R.drawable.icon_back)
                }

                "fragment_location" -> {
                    binding.imgMenu.setBackgroundResource(R.drawable.icon_menu)
                }
                "fragment_shopping_cart" -> {
                    binding.imgMenu.setBackgroundResource(R.drawable.icon_menu)
                }
            }
        })
    }
}