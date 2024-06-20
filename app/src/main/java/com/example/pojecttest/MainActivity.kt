package com.example.pojecttest

import android.os.Bundle
import android.preference.PreferenceManager
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import org.osmdroid.tileprovider.tilesource.XYTileSource
import org.osmdroid.views.MapView
import java.util.Date
import org.osmdroid.config.Configuration.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        var textview : TextView = findViewById(R.id.time_date)

        textview.text = Date().hours.toString()

        var mapID : MapView = findViewById(R.id.mapID)
        getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this))

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
    }
}