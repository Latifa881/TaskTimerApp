package com.example.tasktimerapp.fragments


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
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tasktimerapp.R
import com.example.tasktimerapp.TimerService
import com.example.tasktimerapp.adapter.StopwatchRVAdapter
import com.example.tasktimerapp.model.MyViewModel
import kotlinx.android.synthetic.main.task_row.view.*
import kotlin.math.roundToInt
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt.PromptStateChangeListener
import uk.co.samuelwall.materialtaptargetprompt.extras.backgrounds.RectanglePromptBackground
import uk.co.samuelwall.materialtaptargetprompt.extras.focals.RectanglePromptFocal


class ViewTaskFragment : Fragment() {
    lateinit var stopwatchRvAdapter: StopwatchRVAdapter
    lateinit var tvStopWatch: TextView
    lateinit var tvTaskName: TextView
    lateinit var ivIcanchor: ImageView
    lateinit var llNoViewTaskFragment : LinearLayout
    lateinit var llViewTaskFragment : LinearLayout
    lateinit var rvMain:RecyclerView
    lateinit var ivInfo:ImageView
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
        ivInfo=view.findViewById(R.id.ivInfo)


        myViewModel.getTasks().observe(viewLifecycleOwner, {
                tasks ->

            if (tasks.isEmpty()) {
                llNoViewTaskFragment.visibility = View.VISIBLE
                llViewTaskFragment.visibility = View.GONE

            } else {

                stopwatchRvAdapter.update(tasks)
                llViewTaskFragment.visibility = View.VISIBLE
                llNoViewTaskFragment.visibility = View.GONE

                ivInfo.setOnClickListener {
                    startInfoShowCase()

                }
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
    fun startInfoShowCase(){
        changeVisibility(0)
        MaterialTapTargetPrompt.Builder(this@ViewTaskFragment)
            .setTarget(R.id.ivStart)
            .setBackgroundColour(resources.getColor(R.color.pink))
            .setFocalColour(resources.getColor(R.color.green))
            .setPrimaryText("Start Your Task Stopwatch")
            .setSecondaryText("Click the start icon to start your task stopwatch.")
            .setPromptStateChangeListener(PromptStateChangeListener { prompt, state ->
                if  (state == MaterialTapTargetPrompt.STATE_FOCAL_PRESSED
                    || state == MaterialTapTargetPrompt.STATE_NON_FOCAL_PRESSED){
                    stopwatchInfoShowCase()

                }
            })
            .show()
    }
    fun stopwatchInfoShowCase(){
        MaterialTapTargetPrompt.Builder(this@ViewTaskFragment)
            .setTarget(R.id.tvStopWatch)
            .setBackgroundColour(resources.getColor(R.color.pink))
            .setFocalColour(resources.getColor(R.color.green))
            .setPrimaryText("See Your Active Task Stopwatch")
            .setSecondaryText("You can see you active task name and the time.")
            .setPromptFocal(RectanglePromptFocal())
            .setPromptStateChangeListener(PromptStateChangeListener { prompt, state ->
                if  (state == MaterialTapTargetPrompt.STATE_FOCAL_PRESSED
                    || state == MaterialTapTargetPrompt.STATE_NON_FOCAL_PRESSED){
                    stopInfoShowCase()

                }
            })
            .show()



    }
    fun stopInfoShowCase(){
        MaterialTapTargetPrompt.Builder(this@ViewTaskFragment)
            .setTarget(R.id.ivStop)
            .setBackgroundColour(resources.getColor(R.color.pink))
            .setFocalColour(resources.getColor(R.color.green))
            .setPrimaryText("Stop Your Task Stopwatch")
            .setSecondaryText("Click the Stop icon of the active task to stop the stopwatch.")
            .setPromptStateChangeListener(PromptStateChangeListener { prompt, state ->
                if  (state == MaterialTapTargetPrompt.STATE_FOCAL_PRESSED
                    || state == MaterialTapTargetPrompt.STATE_NON_FOCAL_PRESSED){
                    taskInfoShowCase()
                }
            })
            .show()
    }
    fun taskInfoShowCase(){
        MaterialTapTargetPrompt.Builder(this@ViewTaskFragment)
            .setTarget(R.id.cvTask)
            .setBackgroundColour(resources.getColor(R.color.pink))
            .setFocalColour(resources.getColor(R.color.green))
            .setPrimaryText("Navigate Between Tasks")
            .setSecondaryText("Swipe left and right to move between tasks.")
            .setPromptFocal(RectanglePromptFocal())
            .setPromptStateChangeListener(PromptStateChangeListener { prompt, state ->
                if  (state == MaterialTapTargetPrompt.STATE_FOCAL_PRESSED
                    || state == MaterialTapTargetPrompt.STATE_NON_FOCAL_PRESSED){

                    changeVisibility(1)

                }
            })
            .show()
    }
    fun changeVisibility(status:Int){
        when(status){
            0->{
                llNoViewTaskFragment.visibility = View.GONE
                llViewTaskFragment.visibility = View.GONE
            }
            1->{
                llNoViewTaskFragment.visibility = View.GONE
                llViewTaskFragment.visibility = View.VISIBLE
            }
        }

    }



}