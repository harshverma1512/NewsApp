package com.example.newsapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.commit
import com.example.newsapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var binding : ActivityMainBinding?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root!!)

        val fragment = IndiaNews()

        binding?.worldNews?.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
        }

        binding?.indiaNews?.setOnClickListener{
            supportFragmentManager.commit {
                replace(R.id.fragmentContainerView,fragment)
            }
        }
    }
}

