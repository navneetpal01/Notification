package com.example.notification

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Icon
import android.os.Build
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.core.app.NotificationCompat


class CounterNotification(
    private val context: Context
) {

    //Flag just tells how to read the pending Intent
    val flag = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) PendingIntent.FLAG_IMMUTABLE else 0

    val intent = Intent(context, MainActivity::class.java)
    val notificationClickPendingIntent = PendingIntent.getActivity(
        context,1,intent,flag
    )


    fun counterNotification(counter: Int) {
        val notification = NotificationCompat
            .Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.baseline_notifications_24)
            .setContentTitle("Counter")
            .setContentText(counter.toString())
            .setStyle(NotificationCompat.BigPictureStyle())
            .setOngoing(true)  //Means that you can't just swipe this notification
            .setContentIntent()


    }


    companion object {
        const val CHANNEL_ID = "counter_channel"
    }
}