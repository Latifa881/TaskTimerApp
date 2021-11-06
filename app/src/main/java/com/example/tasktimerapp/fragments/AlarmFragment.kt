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
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.tasktimerapp.R
import com.example.tasktimerapp.broadcastReceiver.AlarmBroadcastReceiver
import com.example.tasktimerapp.singletoneobject.MyAlarm
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt
import uk.co.samuelwall.materialtaptargetprompt.extras.focals.RectanglePromptFocal
import java.util.*

class AlarmFragment : Fragment() {
    lateinit var btSelectTime: Button
    lateinit var btSetAlarm: Button
    lateinit var btCancelAlarm:Button
    lateinit var tvAlarmTim: TextView
    lateinit var ivInfoAlarm:ImageView
    lateinit var llAlarm:LinearLayout
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
        tvAlarmTim = view.findViewById(R.id.tvAlarmTime)
        btCancelAlarm=view.findViewById(R.id.btCancelAlarm)
        ivInfoAlarm=view.findViewById(R.id.ivInfoAlarm)
        llAlarm=view.findViewById(R.id.llAlarm)

        ivInfoAlarm.setOnClickListener {
            selectTimeInfoShowCase()

        }

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
    fun changeVisibility(status:Int){
        when(status){
            0->{
                llAlarm.visibility = View.GONE
            }
            1->{
                llAlarm.visibility = View.VISIBLE
            }
        }

    }
    fun selectTimeInfoShowCase(){
        changeVisibility(0)
        MaterialTapTargetPrompt.Builder(this@AlarmFragment)
            .setTarget(R.id.btSelectTime)
            .setBackgroundColour(resources.getColor(R.color.pink))
            .setFocalColour(resources.getColor(R.color.green))
            .setPrimaryText("Set an Alarm for Your Task")
            .setSecondaryText("Click this button to select a time for your alarm.")
            .setPromptFocal(RectanglePromptFocal())
            .setPromptStateChangeListener(MaterialTapTargetPrompt.PromptStateChangeListener { prompt, state ->
                if (state == MaterialTapTargetPrompt.STATE_FOCAL_PRESSED
                    || state == MaterialTapTargetPrompt.STATE_NON_FOCAL_PRESSED
                ) {

                    alarmTimeInfoShowCase()
                }
            })
            .show()
    }
    fun alarmTimeInfoShowCase(){
        MaterialTapTargetPrompt.Builder(this@AlarmFragment)
            .setTarget(R.id.tvAlarmTime)
            .setBackgroundColour(resources.getColor(R.color.pink))
            .setFocalColour(resources.getColor(R.color.green))
            .setPrimaryText("See The Selected Time for Your Alarm")
            .setSecondaryText("You can see the time you have selected before the alarm is set.")
            .setPromptFocal(RectanglePromptFocal())
            .setPromptStateChangeListener(MaterialTapTargetPrompt.PromptStateChangeListener { prompt, state ->
                if (state == MaterialTapTargetPrompt.STATE_FOCAL_PRESSED
                    || state == MaterialTapTargetPrompt.STATE_NON_FOCAL_PRESSED
                ) {
                    setAlarmInfoShowCase()
                }
            })
            .show()

    }
    fun setAlarmInfoShowCase(){
        MaterialTapTargetPrompt.Builder(this@AlarmFragment)
            .setTarget(R.id.btSetAlarm)
            .setBackgroundColour(resources.getColor(R.color.pink))
            .setFocalColour(resources.getColor(R.color.green))
            .setPrimaryText("Set Your Alarm")
            .setSecondaryText("You can set your alarm for the selected time.")
            .setPromptFocal(RectanglePromptFocal())
            .setPromptStateChangeListener(MaterialTapTargetPrompt.PromptStateChangeListener { prompt, state ->
                if (state == MaterialTapTargetPrompt.STATE_FOCAL_PRESSED
                    || state == MaterialTapTargetPrompt.STATE_NON_FOCAL_PRESSED
                ) {
                    cancelAlarmInfoShowCase()
                }
            })
            .show()
    }
    fun cancelAlarmInfoShowCase(){
        MaterialTapTargetPrompt.Builder(this@AlarmFragment)
            .setTarget(R.id.btCancelAlarm)
            .setBackgroundColour(resources.getColor(R.color.pink))
            .setFocalColour(resources.getColor(R.color.green))
            .setPrimaryText("Cancel Your Alarm")
            .setSecondaryText("You can cancel your alarm by clicking this button.")
            .setPromptFocal(RectanglePromptFocal())
            .setPromptStateChangeListener(MaterialTapTargetPrompt.PromptStateChangeListener { prompt, state ->
                if (state == MaterialTapTargetPrompt.STATE_FOCAL_PRESSED
                    || state == MaterialTapTargetPrompt.STATE_NON_FOCAL_PRESSED
                ) {
                   changeVisibility(1)
                }
            })
            .show()

    }
}