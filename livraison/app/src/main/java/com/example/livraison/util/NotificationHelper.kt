package com.example.livraison.util

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat

class NotificationHelper (private val ctx: Context){
    private val nm = ctx.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    private val channelId = "livraison_channel"
    init {
        if (android.os.Build.VERSION.SDK_INT>= android.os.Build.VERSION_CODES.O) {
           nm.createNotificationChannel(
               NotificationChannel(channelId, "Livraison Alerts", NotificationManager.IMPORTANCE_DEFAULT)

           )
        }
    }
    fun show(text:String){
        val n = NotificationCompat.Builder(ctx, channelId)
            .setContentTitle("Gestion Livraison")
            .setContentText(text)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .build()
        nm.notify((0..1000).random(), n)
    }
}