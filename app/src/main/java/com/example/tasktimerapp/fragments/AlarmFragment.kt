package com.example.tasktimerapp.fragments

import android.app.*
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import com.example.tasktimerapp.R
import com.example.tasktimerapp.broadcastReceiver.AlarmBroadcastReceiver
import com.example.tasktimerapp.singletoneobject.MyAlarm
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.google.android.material.timepicker.TimeFormat.CLOCK_12H
import java.util.*

class AlarmFragment : Fragment() {
    lateinit var btSelectTime: Button
    lateinit var btSetAlarm: Button
    lateinit var btCancelAlarm:Button
    lateinit var tvAlarmTim: TextView
    private lateinit var timePiker: MaterialTimePicker
    private lateinit var calendar: Calendar
    private lateinit var alarmManager: AlarmManager
    private lateinit var pendingIntent: PendingIntent


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_alarm, container, false)
        createNotificationChannel()
        btSelectTime = view.findViewById(R.id.btSelectTime)
        btSetAlarm = view.findViewById(R.id.btSetAlarm)
        tvAlarmTim = view.findViewById(R.id.tvAlarmTim)
        btCancelAlarm=view.findViewById(R.id.btCancelAlarm)

        btSelectTime.setOnClickListener {
            showTimPicker()
        }
        btSetAlarm.setOnClickListener {
            SetAlarm()
        }
        btCancelAlarm.setOnClickListener {
            cancelAlarm()
        }
        if (MyAlarm.isAlarmCreated){
         tvAlarmTim.text=MyAlarm.alarmTime
        }
        return view
    }



    fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "taskTimerNotifications"
            val name: CharSequence = "taskTimerNotificationsChannel"
            val description = "Chanel for Alarm Manager"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(channelId, name, importance)
            channel.description = description
            val notificationManager =
                requireActivity().getSystemService(AppCompatActivity.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun showTimPicker() {
        timePiker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_12H)
            .setHour(12)
            .setMinute(0)
            .setTitleText("Select Alarm Time")
            .build()
        timePiker.show(requireActivity().getSupportFragmentManager(), "taskTimerNotifications")
        timePiker.addOnPositiveButtonClickListener {
            if (timePiker.hour > 12) {
                tvAlarmTim.text = String.format("%2d", timePiker.hour - 12) + " : " + String.format(
                    "%2d",
                    timePiker.minute
                ) + " PM"
            } else {
                tvAlarmTim.text = String.format("%2d", timePiker.hour) + " : " + String.format(
                    "%2d",
                    timePiker.minute
                ) + " AM"
            }

            calendar = Calendar.getInstance()
            calendar[Calendar.HOUR_OF_DAY] = timePiker.hour
            calendar[Calendar.MINUTE] = timePiker.minute
            calendar[Calendar.SECOND] = 0
            calendar[Calendar.MILLISECOND] = 0

        }


    }
    fun SetAlarm() {
    alarmManager= requireActivity().getSystemService(ALARM_SERVICE) as AlarmManager
        val intent=Intent(context, AlarmBroadcastReceiver::class.java)
        pendingIntent= PendingIntent.getBroadcast(context, 0, intent,0)
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,pendingIntent
        )
        MyAlarm.isAlarmCreated=true
        MyAlarm.alarmTime=tvAlarmTim.text.toString()

        Toast.makeText(context,"Alarm set Successfully",Toast.LENGTH_LONG).show()

    }
    fun cancelAlarm(){
        alarmManager= requireActivity().getSystemService(ALARM_SERVICE) as AlarmManager
        val intent=Intent(context, AlarmBroadcastReceiver::class.java)
        pendingIntent= PendingIntent.getBroadcast(context, 0, intent,0)
        alarmManager.cancel(pendingIntent)
        Toast.makeText(context,"Alarm Cancelled",Toast.LENGTH_LONG).show()
        MyAlarm.isAlarmCreated=false
        tvAlarmTim.text="You didn't set an alarm yet"
    }
}