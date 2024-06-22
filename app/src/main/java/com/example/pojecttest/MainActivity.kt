package com.example.pojecttest

import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.pojecttest.databinding.ActivityMainBinding
import org.osmdroid.tileprovider.tilesource.XYTileSource
import org.osmdroid.views.MapView
import java.util.Date
import org.osmdroid.config.Configuration.*
import org.osmdroid.tileprovider.modules.OfflineTileProvider
import org.osmdroid.tileprovider.util.SimpleRegisterReceiver
import org.osmdroid.util.GeoPoint
import java.io.File

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var textview: TextView = findViewById(R.id.time_date)
        textview.text = Date().hours.toString()

        val filepath = "/data/data/com.example.pojecttest/files/osmdroid/map.sqlite"
        getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this))


        val file = File(filepath)
        Log.d("TEST", file.toString())
        val tileProvider_ = OfflineTileProvider(
            SimpleRegisterReceiver(this@MainActivity),
            arrayOf(file)
        )
        binding.apply {
            mapID.tileProvider = tileProvider_
            mapID.setUseDataConnection(false)
            mapID.setTileSource(
                XYTileSource(
                    "Google Maps HD",
                    7,
                    12,
                    256,
                    ".png",
                    arrayOf("")
                )
            )
            val mapcrtl = mapID.controller

            mapcrtl.setZoom(9.5)
            mapcrtl.setCenter(GeoPoint(25,121))
        }
    }
}