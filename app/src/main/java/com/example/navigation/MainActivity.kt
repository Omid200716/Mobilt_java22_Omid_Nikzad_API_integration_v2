package com.example.navigation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        findViewById<Button>(R.id.button).setOnClickListener { v->
            val intent = Intent(this@MainActivity, MainActivity2::class.java)
            startActivity(intent)
        }

      //  findViewById<Button>(R.id.button).setOnClickListener {
        //    val intent = Intent(this@MainActivity, MainActivity2::class.java)
          //  startActivity(intent)
    }
}