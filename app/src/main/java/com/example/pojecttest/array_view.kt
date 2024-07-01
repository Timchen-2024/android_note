package com.example.pojecttest

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pojecttest.databinding.ActivityArrayViewBinding

class array_view : AppCompatActivity() {
    private lateinit var binding: ActivityArrayViewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_array_view)
        binding = ActivityArrayViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {

            backBtn.setOnClickListener {
                finish()
            }
            wlist.adapter = list_adapter(this@array_view)
            wlist.layoutManager = LinearLayoutManager(this@array_view)
        }

    }
}