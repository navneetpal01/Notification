package com.example.notification

import android.app.NotificationManager
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

   private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    fun counterNotification(counter: Int) {
        //Flag just tells how to read the pending Intent
        val flag = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) PendingIntent.FLAG_IMMUTABLE else 0

        val intent = Intent(context, MainActivity::class.java)
        val notificationClickPendingIntent = PendingIntent.getActivity(
            context,1,intent,flag
        )

        val notification = NotificationCompat
            .Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.baseline_notifications_24)
            .setContentTitle("Counter")
            .setContentText(counter.toString())
            .setStyle(NotificationCompat.BigPictureStyle())
            .setOngoing(true)  //Means that you can't just swipe this notification
            .setContentIntent(notificationClickPendingIntent)
            .addAction(
                R.drawable.baseline_notifications_24,
                "Start",
                getPendingIntentForAction(
                    CounterActions.START,
                    flag,
                    2
                )
            )
            .addAction(
                R.drawable.baseline_notifications_24,
                "Stop",
                getPendingIntentForAction(
                    CounterActions.STOP,
                    flag,
                    3
                )
            )
            .build()
        notificationManager.notify(1,notification)
    }

    private fun getPendingIntentForAction(
        action : CounterActions,
        flag : Int,
        requestCode : Int
    ) : PendingIntent{

        val intent = Intent(context,CounterReceiver::class.java)

        when(action){
            CounterActions.START -> intent.action = CounterActions.START.name
            CounterActions.STOP -> intent.action = CounterActions.STOP.name
        }

        //Cause we need to send it to our BroadCast
        return PendingIntent.getBroadcast(
            context,requestCode,intent,flag
        )
    }


    companion object {
        const val CHANNEL_ID = "counter_channel"
    }


    enum class CounterActions{
        START,
        STOP
    }
}