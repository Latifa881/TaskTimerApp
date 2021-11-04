package com.example.tasktimerapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.tasktimerapp.singletoneobject.MyTime
import com.example.tasktimerapp.R
import com.example.tasktimerapp.database.Tasks
import com.example.tasktimerapp.fragments.ViewTaskFragment
import com.example.tasktimerapp.model.MyViewModel
import kotlinx.android.synthetic.main.task_row.view.*

class StopwatchRVAdapter (private val activity: ViewTaskFragment):  RecyclerView.Adapter<StopwatchRVAdapter.ItemViewHolder>(){

    private var tasks = emptyList<Tasks>()
    private val myViewModel by lazy{ ViewModelProvider(activity).get(MyViewModel::class.java)}

    class ItemViewHolder (itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {

        return ItemViewHolder(
             LayoutInflater.from(parent.context).inflate(
                 R.layout.task_row,
                 parent,
                 false
             )
         )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        var data = tasks[position]


        holder.itemView.apply {

            var animation : Animation = AnimationUtils.loadAnimation(context, R.anim.roundingalone)
            tvName.text = data.name
            tvDescription.text = data.description


            ivStart.setOnClickListener {

                if (!MyTime.isActive ){
                     if (activity.myTaskId == data.id) {
                         var time = activity.tvStopWatch.text.toString()

                         myViewModel.updateTask(time, activity.myTaskId )
                     }else {

                         var time = activity.tvStopWatch.text.toString()

                         myViewModel.updateTask(time, activity.myTaskId )
                         activity.myTaskId = data.id
                         activity.resetTimer()
                         activity.startTimer()
                         activity.tvTaskName.visibility = View.VISIBLE
                         activity.tvTaskName.text = data.name
                         activity.ivIcanchor.startAnimation(animation)

                     }

                }else {
                    var id = data.id
                    if( activity.myTaskId == data.id){
                        var time = activity.tvStopWatch.text.toString()

                        myViewModel.updateTask(time, id )
                        activity.resetTimer()
                        activity.startTimer()
                        activity.tvTaskName.visibility = View.VISIBLE
                        activity.tvTaskName.text = data.name
                        activity.ivIcanchor.startAnimation(animation)


                } else if (id != activity.myTaskId) {

                        var time = activity.tvStopWatch.text.toString()
                        myViewModel.updateTask(time, activity.myTaskId)
                        activity.myTaskId = id
                        activity.resetTimer()
                        activity.startTimer()
                        activity.tvTaskName.visibility = View.VISIBLE
                        activity.tvTaskName.text = data.name
                        activity.ivIcanchor.startAnimation(animation)
                    }

                }

                MyTime.isActive = !MyTime.isActive

            }
            ivStop.setOnClickListener {
                MyTime.isActive = false

                if (activity.myTaskId == data.id) {
                var time = activity.tvStopWatch.text.toString()
                myViewModel.updateTask(time , data.id)
                activity.resetTimer()
                activity.tvTaskName.visibility = View.GONE
                activity.ivIcanchor.clearAnimation()

                }
            }
        }
    }

    override fun getItemCount() = tasks.size

    fun update(tasks: List<Tasks>){
        this.tasks = tasks
        notifyDataSetChanged()
    }

}