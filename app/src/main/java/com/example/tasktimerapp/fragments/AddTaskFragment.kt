package com.example.tasktimerapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.tasktimerapp.R
import com.example.tasktimerapp.database.Tasks
import com.example.tasktimerapp.model.MyViewModel

class AddTaskFragment : Fragment() {
    lateinit var etName: EditText
    lateinit var etDescription: EditText
    lateinit var btnAdd: Button
    private val myViewModel by lazy{ ViewModelProvider(this).get(MyViewModel::class.java)}
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         val view=inflater.inflate(R.layout.fragment_add_task, container, false)
        etName = view.findViewById(R.id.etName)
        etDescription = view.findViewById(R.id.etDescription)
        btnAdd = view.findViewById(R.id.btnAdd)

//        var fab = view.findViewById<FloatingActionButton>(R.id.fabAdd)
//        fab.setOnClickListener {
//
//            //startActivity(Intent(this, AddTaskActivity::class.java))
//        }

//        var btAddNavigationView = view.findViewById<BottomNavigationView>(R.id.btAddNavigationView)
//
//        btAddNavigationView.background = null
//        btAddNavigationView.menu.getItem(2).isEnabled = false
//
//        btAddNavigationView.setOnNavigationItemSelectedListener {
//                item ->
//            when (item.itemId) {
//                R.id.home -> {
//                    Navigation.findNavController(view).navigate(R.id.action_addTaskFragment_to_homeFragment)
//                    //startActivity(Intent(this, MainActivity::class.java))
//                }
//                R.id.view -> {
//                    Navigation.findNavController(view).navigate(R.id.action_addTaskFragment_to_viewTaskFragment)
//                   // startActivity(Intent(this, ViewTaskActivity::class.java))
//
//                }
//                R.id.add -> {
//                  //  startActivity(Intent(this, AddTaskActivity::class.java))
//
//                }
//                R.id.summary -> {
//                    Navigation.findNavController(view).navigate(R.id.action_addTaskFragment_to_summaryTaskFragment)
//                    //startActivity(Intent(this, SummaryTaskActivity::class.java))
//                }
//
//            }
//            true
//        }
        btnAdd.setOnClickListener {
            if (etName.text.isNotEmpty() && etDescription.text.isNotEmpty()){
                var name = etName.text.toString()
                var description = etDescription.text.toString()

                // add to database
                myViewModel.addTask(Tasks(0,name, description,""))
                var fr = getFragmentManager()?.beginTransaction()
                fr?.replace(R.id.frameLayout, ViewTaskFragment())
                fr?.commit()
             //   Navigation.findNavController(view).navigate(R.id.action_addTaskFragment_to_viewTaskFragment)
               // startActivity(Intent(this, ViewTaskActivity::class.java))


            }else{
                Toast.makeText(context , "Enter a full task" , Toast.LENGTH_SHORT).show()
            }
        }


        return view
    }



}