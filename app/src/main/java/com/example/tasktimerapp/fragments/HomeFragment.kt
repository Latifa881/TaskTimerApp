package com.example.tasktimerapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tasktimerapp.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton


class HomeFragment : Fragment() {

    lateinit var btNavigationView:BottomNavigationView
    lateinit var fab:FloatingActionButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view= inflater.inflate(R.layout.fragment_home, container, false)
//         btNavigationView = view.findViewById(R.id.btNavigationView)
//
//        btNavigationView.background = null
//        btNavigationView.menu.getItem(2).isEnabled = false
//        fab = view.findViewById(R.id.fabMain)
//        fab.setOnClickListener {
//            Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_addTaskFragment)
//            //startActivity(Intent(this, AddTaskActivity::class.java))
//        }

//        btNavigationView.setOnNavigationItemSelectedListener {
//                item ->
//            when (item.itemId) {
//                R.id.home -> {
//
//                }
//                R.id.view -> {
//                    Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_viewTaskFragment)
//                    //startActivity(Intent(this, ViewTaskActivity::class.java))
//
//                }
//                R.id.add -> {
//                    Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_addTaskFragment)
//                   // startActivity(Intent(this, AddTaskActivity::class.java))
//
//                }
//                R.id.summary -> {
//                    Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_summaryTaskFragment)
//                    //startActivity(Intent(this, SummaryTaskActivity::class.java))
//                }
//
//            }
//            true
//        }



        return view
    }

}