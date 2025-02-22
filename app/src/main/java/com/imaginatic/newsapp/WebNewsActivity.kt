package com.imaginatic.newsapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.imaginatic.newsapp.databinding.ActivityWebNewsBinding

class WebNewsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWebNewsBinding
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarWebNews)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        val url = intent.getStringExtra("url") ?: "https://google.com" // Default URL

        binding.wvNewsWeb.settings.javaScriptEnabled = true
        binding.wvNewsWeb.settings.domStorageEnabled = true

        binding.wvNewsWeb.loadUrl(url)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return super.onSupportNavigateUp()
    }
}