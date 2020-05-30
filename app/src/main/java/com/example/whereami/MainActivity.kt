package com.example.whereami

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity() {
    private var notificationManager : NotificationManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        notificationManager=getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        createNotificationChannel("com.example.whereami",
            "Notification Demo",
            "This a example of notify"
        )

        btnLogin.setOnClickListener{val intent = Intent(this,login::class.java)
            sendNotificationIntent()
            startActivity(intent)}

        btnRegistro.setOnClickListener{val intent = Intent(this,registro::class.java)
            sendNotificationIntent()
            startActivity(intent)}

        btnAcercade.setOnClickListener{val intent = Intent(this,Acercade::class.java)
            finish()
            startActivity(intent)
        }
    }

    private fun createNotificationChannel(id: String, name:String,description: String){
        val importance = NotificationManager.IMPORTANCE_LOW
        val channel = NotificationChannel(id,name,importance)
        channel.description=description
        channel.enableLights(true)
        channel.lightColor= Color.RED
        channel.enableVibration(true)
        channel.vibrationPattern= longArrayOf(100,200,200,400,500,400,300,200,400)
        notificationManager?.createNotificationChannel(channel)
    }

    fun sendNotificationIntent(){
        val notificationId =102
        val resultIntent=Intent(this,Acercade::class.java)
        val pendingIntent= PendingIntent.getActivity(this, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        val channelId="com.example.whereami"
        val notification = NotificationCompat.Builder(this@MainActivity,channelId)
            .setContentTitle("Recomienda Nuestra App! :D")
            .setContentText("No olvides recomendar nuestra App para poder ayudar a más personas.")
            .setSmallIcon(android.R.drawable.ic_dialog_map)
            .setChannelId(channelId)
            .setContentIntent(pendingIntent)
            .build()
        notificationManager?.notify(notificationId, notification)
    }

}
