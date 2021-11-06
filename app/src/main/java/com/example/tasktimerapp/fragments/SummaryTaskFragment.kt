package com.example.tasktimerapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tasktimerapp.R
import com.example.tasktimerapp.adapter.SummaryRVAdapter
import com.example.tasktimerapp.callbacks.SwipeGesture
import com.example.tasktimerapp.database.Tasks
import com.example.tasktimerapp.model.MyViewModel
import java.util.*


class SummaryTaskFragment : Fragment() {

    lateinit var llNoSummaryTask : LinearLayout
    lateinit var llSummaryTask:LinearLayout
    lateinit var rvSummary: RecyclerView
    lateinit var summaryRVAdapter: SummaryRVAdapter
    lateinit var tvTotalTasks:TextView
    lateinit var tvTotalTimes:TextView
    lateinit var svSearchTask: SearchView
    lateinit var constraintLayoutSummary:ConstraintLayout
    lateinit var ivSort:ImageView
    val searchArray = arrayListOf<Tasks>()
    val sortArray = arrayListOf<Tasks>()
    var sortFlag:Boolean=true

    private val myViewModel by lazy{ ViewModelProvider(this).get(MyViewModel::class.java)}


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_summary_task, container, false)

        llNoSummaryTask = view.findViewById(R.id.llNoSummaryTask)
        llSummaryTask=view.findViewById(R.id.llSummaryTask)
        rvSummary = view.findViewById(R.id.rvSummary)
        tvTotalTasks=view.findViewById(R.id.tvTotalTasks)
        tvTotalTimes=view.findViewById(R.id.tvTotalTimes)
        svSearchTask=view.findViewById(R.id.svSearchTask)
        constraintLayoutSummary=view.findViewById(R.id.constraintLayoutSummary)
        ivSort=view.findViewById(R.id.ivSort)

        constraintLayoutSummary.setOnClickListener {
            hidKeyBoard()
        }

        myViewModel.getTasks().observe(viewLifecycleOwner, {
                tasks ->
            if (tasks.isEmpty()) {

                llSummaryTask.visibility = View.GONE
               llNoSummaryTask.visibility = View.VISIBLE

            } else {

                llSummaryTask.visibility = View.VISIBLE
                llNoSummaryTask.visibility = View.GONE

            }

            val swipeGesture = object: SwipeGesture(requireContext()){
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                    when(direction){
                        ItemTouchHelper.LEFT ->{summaryRVAdapter.deleteTask(viewHolder.absoluteAdapterPosition)}
                        ItemTouchHelper.RIGHT ->{
                         val updateTaskDetails=summaryRVAdapter.updateTask(viewHolder.absoluteAdapterPosition)
                            val bundle = Bundle()
                            bundle.putInt("Id",updateTaskDetails.id )
                            bundle.putString("Name",updateTaskDetails.name )
                            bundle.putString("Description",updateTaskDetails.description )
                            bundle.putString("TotalTime",updateTaskDetails.totalTimer )

                            val updateFragment=UpdateFragment()
                            updateFragment.setArguments(bundle)

                            requireActivity().supportFragmentManager
                                .beginTransaction()
                                .replace(R.id.frameLayout,updateFragment)
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                                .commit()

                        }
                    }
                }
            }
            val touchHelperDelete = ItemTouchHelper(swipeGesture)
            touchHelperDelete.attachToRecyclerView(rvSummary)
            summaryRVAdapter.update(tasks)
            tvTotalTasks.setText("Total Number of Tasks: ${tasks.size}")
            tvTotalTimes.setText("Total Time Spent on Tasks: ${TotalTime(tasks)}")

            svSearchTask.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    return true
                }
                override fun onQueryTextChange(newText: String): Boolean {
                    if (newText.isNotEmpty()) {
                        searchArray.clear()
                        val search = newText.toLowerCase(Locale.getDefault())
                        tasks.forEach {
                            if (it.name?.toLowerCase(Locale.getDefault()).toString()
                                    .contains(search)
                            ) {
                                searchArray.add(it)
                            }
                        }
                        summaryRVAdapter.update(searchArray)
                    } else {
                        searchArray.clear()
                        searchArray.addAll(tasks)
                        summaryRVAdapter.update(searchArray)
                    }


                    return false
                }
            })
            ivSort.setOnClickListener {
                sortFlag=!sortFlag

                sortArray.clear()
                sortArray.addAll(tasks.sortedByDescending { it.name })
                if(sortFlag)
                {sortArray.reverse()}
                summaryRVAdapter.update(sortArray)
            }

        })

        summaryRVAdapter = SummaryRVAdapter(this)
        rvSummary.adapter = summaryRVAdapter
        rvSummary.layoutManager = LinearLayoutManager(context)


        return view
    }
    fun TotalTime(tasks:List<Tasks>):String{
        //00:00:00
        var hours=0f// index 01
        var minutes=0f//index 34
        var seconds=0f//index 67

        for (i in tasks){
            hours+=i.totalTimer.substring(0,2).toFloat()
            minutes+=i.totalTimer.substring(3,5).toFloat()
            seconds+=i.totalTimer.substring(6,8).toFloat()
        }
        return "$hours:$minutes:$seconds"
    }
    fun hidKeyBoard(){
        // Hide Keyboard
        val imm = ContextCompat.getSystemService(requireContext(), InputMethodManager::class.java)
        imm?.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)
    }



}