package com.example.pojecttest

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.preference.PreferenceManager
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.pojecttest.databinding.ActivityMainBinding
import org.osmdroid.tileprovider.tilesource.XYTileSource
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


        val channelId = "channel_id"
        val notification_id = 1

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "channel_name"
            val descriptionText = "channel_description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val mChannel = NotificationChannel(channelId, name, importance)
            mChannel.description = descriptionText
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(mChannel)
        }


        val filepath = "/data/data/com.example.pojecttest/files/osmdroid/map.sqlite"
        getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this))


        val file = File(filepath)
        file.outputStream().use { assets.open("map.sqlite").copyTo(it) }
        val tileProvider_ = OfflineTileProvider(
            SimpleRegisterReceiver(this@MainActivity), arrayOf(file)
        )
        binding.apply {
            mapID.apply {
                tileProvider = tileProvider_
                setUseDataConnection(false)
                setTileSource(
                    XYTileSource(
                        "Google Maps HD", 7, 12, 256, ".png", arrayOf("")
                    )
                )
                controller.apply {
                    setZoom(9.5)
                    setCenter(GeoPoint(25.0168, 121.4628))
                }
            }
            btn.setOnClickListener {
                val builder = NotificationCompat.Builder(this@MainActivity, channelId)
                    .setSmallIcon(R.drawable.ic_launcher_foreground).setContentTitle("這是通知")
                    .setContentText("對 這是通知").setPriority(NotificationCompat.PRIORITY_DEFAULT)

                with(NotificationManagerCompat.from(this@MainActivity)) {
                    requestPermissions(arrayOf(Manifest.permission.POST_NOTIFICATIONS),1)
                    if (ActivityCompat.checkSelfPermission(
                            this@MainActivity, Manifest.permission.POST_NOTIFICATIONS
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        Toast.makeText(this@MainActivity, "沒有通知權限", Toast.LENGTH_SHORT).show()
                    }
                    notify(notification_id, builder.build())
                }
            }
        }
    }
}