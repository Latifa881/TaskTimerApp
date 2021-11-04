package com.example.tasktimerapp.fragments

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tasktimerapp.R
import com.example.tasktimerapp.TimerService
import com.example.tasktimerapp.adapter.StopwatchRVAdapter
import com.example.tasktimerapp.model.MyViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlin.math.roundToInt


class ViewTaskFragment : Fragment() {
    lateinit var stopwatchRvAdapter: StopwatchRVAdapter
    lateinit var tvStopWatch: TextView
    lateinit var tvTaskName: TextView
    lateinit var ivIcanchor: ImageView
    lateinit var llNoViewTaskFragment : LinearLayout
    lateinit var llViewTaskFragment : LinearLayout
    lateinit var rvMain:RecyclerView
    lateinit var btViewNavigationView:BottomNavigationView
    lateinit var fab:FloatingActionButton
    var timerStarted = false
    private lateinit var serviceIntent: Intent
    private var time = 0.0
    var myTaskId = 0
    private val myViewModel by lazy{ ViewModelProvider(this).get(MyViewModel::class.java)}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
     val view=inflater.inflate(R.layout.fragment_view_task, container, false)
         rvMain = view.findViewById(R.id.rvMain)
        tvStopWatch = view.findViewById(R.id.tvStopWatch)
        ivIcanchor = view.findViewById(R.id.icanchor)
        llNoViewTaskFragment = view.findViewById(R.id.llNoViewTask)
        llViewTaskFragment = view.findViewById(R.id.llViewTask)
        tvTaskName = view.findViewById(R.id.tvTaskName)


        // import font
        // Typeface MLight = Typeface.createFromAsset (getAssets(), path: "fonts/MLight.ttf");
        // Typeface MMedium = Typeface.createFromAsset(getAssets(), path: "fonts/MMedium.ttf");
        // Typeface MRegular = Typeface.createFromAsset(getAssets (), path: "fonts/MRegular.ttf");
        // customize font
        // tvSplash.setTypeface(MRegular);
        // tvSubSplash.setTypeface(MLight);
        // btnget.setTypeface(MMedium);

        myViewModel.getTasks().observe(viewLifecycleOwner, {
                tasks ->

            if (tasks.isEmpty()) {
                llNoViewTaskFragment.visibility = View.VISIBLE
                llViewTaskFragment.visibility = View.GONE

            } else {
                stopwatchRvAdapter.update(tasks)
                llViewTaskFragment.visibility = View.VISIBLE
                llNoViewTaskFragment.visibility = View.GONE
            }

        })


        stopwatchRvAdapter = StopwatchRVAdapter(this)
        rvMain.adapter = stopwatchRvAdapter
        rvMain.layoutManager = LinearLayoutManager(context , LinearLayoutManager.HORIZONTAL , false)


        serviceIntent = Intent(context, TimerService::class.java)
        requireActivity().registerReceiver(updateTime, IntentFilter(TimerService.TIMER_UPDATED))



        return view
    }

    fun resetTimer()
    {
        stopTimer()
        time = 0.0
        tvStopWatch.text = getTimeStringFromDouble(time)
    }

    fun startStopTimer()
    {
        if(timerStarted)
            stopTimer()
        else
            startTimer()
    }

    fun startTimer()
    {
        serviceIntent.putExtra(TimerService.TIME_EXTRA, time)
        requireActivity().startService(serviceIntent)
        timerStarted = true
    }

    fun stopTimer()
    {
        requireActivity().stopService(serviceIntent)
        timerStarted = false
    }

    val updateTime: BroadcastReceiver = object : BroadcastReceiver()
    {
        override fun onReceive(context: Context, intent: Intent)
        {
            time = intent.getDoubleExtra(TimerService.TIME_EXTRA, 0.0)
            tvStopWatch.text = getTimeStringFromDouble(time)
        }
    }

    fun getTimeStringFromDouble(time: Double): String
    {
        val resultInt = time.roundToInt()
        val hours = resultInt % 86400 / 3600
        val minutes = resultInt % 86400 % 3600 / 60
        val seconds = resultInt % 86400 % 3600 % 60

        return makeTimeString(hours, minutes, seconds)
    }

    fun makeTimeString(hour: Int, min: Int, sec: Int): String = String.format("%02d:%02d:%02d", hour, min, sec)



}