package com.rodelindev.marketapp.examples

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.rodelindev.marketapp.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnSend = findViewById<Button>(R.id.btn_send)

        btnSend.setOnClickListener {
            Toast.makeText(this, "Mensaje enviado", Toast.LENGTH_SHORT).show()
        }

    }
}
