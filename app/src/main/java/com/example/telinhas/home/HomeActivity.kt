package com.example.telinhas.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.telinhas.R

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        supportActionBar?.hide()
    }
}