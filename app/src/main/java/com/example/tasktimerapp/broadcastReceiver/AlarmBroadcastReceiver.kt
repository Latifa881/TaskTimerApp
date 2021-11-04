package com.example.tasktimerapp.broadcastReceiver

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.tasktimerapp.R
import com.example.tasktimerapp.fragments.ViewTaskFragment


class AlarmBroadcastReceiver: BroadcastReceiver() {
    private val channelId = "myapp.notifications"
    private val description = "Notification App Example"
    private lateinit var builder:NotificationCompat.Builder

    override fun onReceive(context: Context?, intent: Intent?) {
        val i = Intent(context, ViewTaskFragment::class.java)
        val notificationManager= NotificationManagerCompat.from(context!!)
        intent!!.flags=Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val pendingIntent =
            PendingIntent.getActivity(context, 0, i, 0)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

         builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
            .setContentIntent(pendingIntent)
            .setContentTitle("Task Timer")
            .setContentText("Do your task :)")
            .setAutoCancel(true)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setPriority(NotificationCompat.PRIORITY_HIGH)

            var notificationChannel = NotificationChannel(
                channelId,
                description,
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(notificationChannel)
        }else{
            builder = NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
                .setContentIntent(pendingIntent)
                .setContentTitle("Task Timer")
                .setContentText("Do your task :)")
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH)


        }

        notificationManager.notify(1234, builder.build())

    }
}