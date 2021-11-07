package com.example.tasktimerapp.adapter



import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.tasktimerapp.R
import com.example.tasktimerapp.model.Tasks
import com.example.tasktimerapp.fragments.SummaryTaskFragment
import com.example.tasktimerapp.model.MyViewModel
import kotlinx.android.synthetic.main.summary_row.view.*
import kotlinx.android.synthetic.main.task_row.view.tvDescription
import kotlinx.android.synthetic.main.task_row.view.tvName

class SummaryRVAdapter(private val activity: SummaryTaskFragment):  RecyclerView.Adapter<SummaryRVAdapter.ItemViewHolder>(){

    private var tasks = emptyList<Tasks>()
    private val myViewModel by lazy{ ViewModelProvider(activity).get(MyViewModel::class.java)}
    class ItemViewHolder (itemView: View): RecyclerView.ViewHolder(itemView)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {

        return ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.summary_row,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        var data = tasks[position]

        holder.itemView.apply {

            tvName.text = data.name
            tvDescription.text = data.description
            tvTotalTime.text = data.totalTimer


        }

    }

    override fun getItemCount() = tasks.size

    fun update(tasks: List<Tasks>){
        this.tasks = tasks
        notifyDataSetChanged()
    }
    fun deleteTask(deleteID :Int){
        myViewModel.deleteTask(tasks[deleteID].id)
    }
    fun updateTask(deleteID :Int): Tasks {
        return tasks[deleteID]
    }

}