package com.example.tasktimerapp.broadcastReceiver


import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.tasktimerapp.R
import com.example.tasktimerapp.fragments.ViewTaskFragment


class AlarmBroadcastReceiver: BroadcastReceiver() {
    private val channelId = "taskTimerNotifications"
    override fun onReceive(context: Context?, intent: Intent?) {

        val i = Intent(context, ViewTaskFragment::class.java)
        intent!!.flags=Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val pendingIntent = PendingIntent.getActivity(context, 0, i, 0)

      val  builder = NotificationCompat.Builder(context!!, channelId)
            .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
            .setContentTitle("Task Timer")
            .setContentText("It is the time to do your task :)")
            .setAutoCancel(true)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
           .setContentIntent(pendingIntent)
        val notificationManager= NotificationManagerCompat.from(context)
        notificationManager.notify(123, builder.build())



    }
}