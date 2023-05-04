package com.rodelindev.marketapp.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rodelindev.marketapp.R
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SingleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single)
    }
}