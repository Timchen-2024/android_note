package com.example.pojecttest

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pojecttest.databinding.ActivityItemPageBinding

class item_page : AppCompatActivity() {
    private lateinit var binding: ActivityItemPageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_item_page)
        binding = ActivityItemPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var fileName = intent.getStringExtra("fileName")
        if (fileName != null) {
            Log.d("Testtttttttttt",fileName)
        }
        binding.apply {
            backBtn.setOnClickListener {
                finish()
            }
            weatherList.adapter = weatherList_adapter(this@item_page, fileName!!)
            weatherList.layoutManager = LinearLayoutManager(this@item_page)
        }
    }
}