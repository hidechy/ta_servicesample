package com.example.servicesamplekotlin

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.servicesamplekotlin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {



    private lateinit var binding: ActivityMainBinding



    val timeInstance = Time.getInstance()


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        setContentView(R.layout.activity_main)


        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.tvCount.text = timeInstance.time.toString()


        val buttonStart: Button = findViewById(R.id.btn_start)
        buttonStart.setOnClickListener {
            val intent = Intent(application, TestService::class.java)
            intent.putExtra("REQUEST_CODE", 1)
            startForegroundService(intent)
        }

        val buttonStop: Button = findViewById(R.id.button_stop)
        buttonStop.setOnClickListener {
            val intent = Intent(application, TestService::class.java)
            stopService(intent)
        }
    }
}
