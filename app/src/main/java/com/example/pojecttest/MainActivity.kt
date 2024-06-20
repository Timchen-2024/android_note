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
import org.osmdroid.tileprovider.modules.OfflineTileProvider
import org.osmdroid.tileprovider.util.SimpleRegisterReceiver
import org.osmdroid.util.GeoPoint
import java.io.File

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this))
        setContentView(R.layout.activity_main)
        val filepath = "/data/data/com.examaple.projecttest/files/osmdroid/map.sqlite"

        var textview : TextView = findViewById(R.id.time_date)

        textview.text = Date().hours.toString()

        var mapID : MapView = findViewById(R.id.mapID)

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
        val file = File(filepath)
        val tileProvider_ = OfflineTileProvider(
            SimpleRegisterReceiver(this@MainActivity),
            arrayOf(file)
        )
        mapID.tileProvider = tileProvider_

        val mapctrl = mapID.controller
        mapctrl.setZoom(9.5)
        val startpoint = GeoPoint(48.8583,2.2944)
        mapctrl.setCenter(startpoint)
    }
}