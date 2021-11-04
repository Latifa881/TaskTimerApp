package com.example.tasktimerapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tasktimerapp.R
import com.example.tasktimerapp.adapter.SummaryRVAdapter
import com.example.tasktimerapp.model.MyViewModel


class SummaryTaskFragment : Fragment() {

    lateinit var llNoSummaryTask : LinearLayout
    lateinit var rvSummary: RecyclerView
    lateinit var summaryRVAdapter: SummaryRVAdapter
    private val myViewModel by lazy{ ViewModelProvider(this).get(MyViewModel::class.java)}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_summary_task, container, false)

        llNoSummaryTask = view.findViewById(R.id.llNoSummaryTask)
        rvSummary = view.findViewById(R.id.rvSummary)

//        var fab = view.findViewById<FloatingActionButton>(R.id.fabSummary)
//        fab.setOnClickListener {
//            Navigation.findNavController(view).navigate(R.id.action_summaryTaskFragment_to_addTaskFragment)
//            //startActivity(Intent(this, AddTaskActivity::class.java))
//        }

//        var btSummaryNavigationView = view.findViewById<BottomNavigationView>(R.id.btSummaryNavigationView)
//
//        btSummaryNavigationView.background = null
//        btSummaryNavigationView.menu.getItem(2).isEnabled = false
//
//        btSummaryNavigationView.setOnNavigationItemSelectedListener {
//                item ->
//            when (item.itemId) {
//                R.id.home -> {
//                    Navigation.findNavController(view).navigate(R.id.action_summaryTaskFragment_to_homeFragment)
//                  //  startActivity(Intent(this, MainActivity::class.java))
//                }
//                R.id.view -> {
//                    Navigation.findNavController(view).navigate(R.id.action_summaryTaskFragment_to_viewTaskFragment)
//                   // startActivity(Intent(this, ViewTaskActivity::class.java))
//
//                }
//                R.id.add -> {
//                    Navigation.findNavController(view).navigate(R.id.action_summaryTaskFragment_to_addTaskFragment)
//                   // startActivity(Intent(this, AddTaskActivity::class.java))
//
//                }
//                R.id.summary -> {
//                  //  startActivity(Intent(this, SummaryTaskActivity::class.java))
//                }
//
//            }
//            true
//        }


        myViewModel.getTasks().observe(viewLifecycleOwner, {
                tasks -> summaryRVAdapter.update(tasks)
        })

        summaryRVAdapter = SummaryRVAdapter(this)
        rvSummary.adapter = summaryRVAdapter
        rvSummary.layoutManager = LinearLayoutManager(context)




        return view
    }


}