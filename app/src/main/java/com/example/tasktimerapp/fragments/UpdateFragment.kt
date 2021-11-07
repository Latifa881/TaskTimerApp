package com.example.tasktimerapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.example.tasktimerapp.R
import com.example.tasktimerapp.model.Tasks
import com.example.tasktimerapp.model.MyViewModel
import com.google.android.material.textfield.TextInputEditText


class UpdateFragment : Fragment() {
    lateinit var etUpdateName: TextInputEditText
    lateinit var etUpdateDescription: TextInputEditText
    lateinit var btnUpdate: Button
    lateinit var constraintLayoutUpdate:ConstraintLayout
    private val myViewModel by lazy { ViewModelProvider(this).get(MyViewModel::class.java) }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         val view=inflater.inflate(R.layout.fragment_update, container, false)
        etUpdateName = view.findViewById(R.id.etUpdateName)
        etUpdateDescription = view.findViewById(R.id.etUpdateDescription)
        btnUpdate = view.findViewById(R.id.btnUpdate)
        constraintLayoutUpdate=view.findViewById(R.id.constraintLayoutUpdate)
        constraintLayoutUpdate.setOnClickListener{
            hidKeyBoard()
        }

        var id=0
        var name=""
        var description=""
        var totalTime=""
        val bundle = this.arguments

        if (bundle != null) {
            id=bundle.getInt("Id")
            name= bundle.getString("Name").toString()
            description=bundle.getString("Description").toString()
            totalTime=bundle.getString("TotalTime").toString()
        }
        etUpdateName.setText(name)
        etUpdateDescription.setText(description)

        btnUpdate.setOnClickListener {
            var updatedName = etUpdateName.text.toString()
            var updatedDescription = etUpdateDescription.text.toString()
            if (name.isNotEmpty() && description.isNotEmpty()) {


                myViewModel.updateTaskObj(Tasks(id,updatedName,updatedDescription,totalTime))
                var fr = getFragmentManager()?.beginTransaction()
                fr?.replace(R.id.frameLayout, ViewTaskFragment())
                fr?.commit()
                hidKeyBoard()
                requireActivity().supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.frameLayout,SummaryTaskFragment())
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit()

            } else {
                Toast.makeText(context, "Enter a full task", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }
    fun hidKeyBoard(){
        // Hide Keyboard
        val imm = ContextCompat.getSystemService(requireContext(), InputMethodManager::class.java)
        imm?.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)
    }


}