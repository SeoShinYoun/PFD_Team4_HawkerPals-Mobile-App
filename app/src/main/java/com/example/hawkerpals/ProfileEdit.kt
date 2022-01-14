package com.example.hawkerpals

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hawkerpals.databinding.ActivityProfileEditBinding

class ProfileEdit : AppCompatActivity() {
    private lateinit var binding: ActivityProfileEditBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.backBtn.setOnClickListener{
            onBackPressed()
        }
    }
}