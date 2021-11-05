package com.example.tasktimerapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.tasktimerapp.R
import com.example.tasktimerapp.database.Tasks
import com.example.tasktimerapp.model.MyViewModel
import com.google.android.material.textfield.TextInputEditText

class AddTaskFragment : Fragment() {
    lateinit var etName: TextInputEditText
    lateinit var etDescription: TextInputEditText
    lateinit var btnAdd: Button
    lateinit var constraintLayoutAdd:ConstraintLayout
    private val myViewModel by lazy { ViewModelProvider(this).get(MyViewModel::class.java) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_task, container, false)
        etName = view.findViewById(R.id.etName)
        etDescription = view.findViewById(R.id.etDescription)
        btnAdd = view.findViewById(R.id.btnAdd)
        constraintLayoutAdd=view.findViewById(R.id.constraintLayoutAdd)
        constraintLayoutAdd.setOnClickListener{
            hidKeyBoard()
        }

        btnAdd.setOnClickListener {

            if (etName.text.toString().isNotEmpty() && etDescription.text.toString().isNotEmpty()) {
                var name = etName.text.toString()
                var description = etDescription.text.toString()

                myViewModel.addTask(Tasks(0, name, description, "00:00:00"))
                var fr = getFragmentManager()?.beginTransaction()
                fr?.replace(R.id.frameLayout, ViewTaskFragment())
                fr?.commit()
                hidKeyBoard()

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