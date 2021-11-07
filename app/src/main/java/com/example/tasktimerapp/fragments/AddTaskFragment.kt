package com.example.tasktimerapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.tasktimerapp.model.Tasks
import com.example.tasktimerapp.model.MyViewModel
import com.google.android.material.textfield.TextInputEditText
import com.example.tasktimerapp.R
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt
import uk.co.samuelwall.materialtaptargetprompt.extras.focals.RectanglePromptFocal


class AddTaskFragment : Fragment() {
    lateinit var etName: TextInputEditText
    lateinit var etDescription: TextInputEditText
    lateinit var btnAdd: Button
    lateinit var constraintLayoutAdd:ConstraintLayout
    lateinit var ivInfoAdd:ImageView
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
        ivInfoAdd=view.findViewById(R.id.ivInfoAdd)

        ivInfoAdd.setOnClickListener {
            nameInfoShowCase()
        }
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
    fun changeVisibility(status:Int){
        when(status){
            0->{
                constraintLayoutAdd.visibility = View.GONE
            }
            1->{
                constraintLayoutAdd.visibility = View.VISIBLE
            }
        }

    }
    fun nameInfoShowCase(){
        changeVisibility(0)
        MaterialTapTargetPrompt.Builder(this@AddTaskFragment)
            .setTarget(R.id.etName)
            .setBackgroundColour(resources.getColor(R.color.pink))
            .setFocalColour(resources.getColor(R.color.green))
            .setPrimaryText("Write Your Task Name")
            .setSecondaryText("Write the name of your task here. Do not worry if you mistake in writing it; you can edit it through the summary interface.")
            .setPromptFocal(RectanglePromptFocal())
            .setPromptStateChangeListener(MaterialTapTargetPrompt.PromptStateChangeListener { prompt, state ->
                if (state == MaterialTapTargetPrompt.STATE_FOCAL_PRESSED
                    || state == MaterialTapTargetPrompt.STATE_NON_FOCAL_PRESSED
                ) {

                    descriptionInfoShowCase()
                }
            })
            .show()
    }
    fun descriptionInfoShowCase(){
        MaterialTapTargetPrompt.Builder(this@AddTaskFragment)
            .setTarget(R.id.etDescription)
            .setBackgroundColour(resources.getColor(R.color.pink))
            .setFocalColour(resources.getColor(R.color.green))
            .setPrimaryText("Write Your Task Description")
            .setSecondaryText("Write the description of your task here. Do not worry if you mistake in writing it; you can edit it through the summary interface.")
            .setPromptFocal(RectanglePromptFocal())
            .setPromptStateChangeListener(MaterialTapTargetPrompt.PromptStateChangeListener { prompt, state ->
                if (state == MaterialTapTargetPrompt.STATE_FOCAL_PRESSED
                    || state == MaterialTapTargetPrompt.STATE_NON_FOCAL_PRESSED
                ) {

                    addInfoShowCase()
                }
            })
            .show()

    }
    fun addInfoShowCase(){
        MaterialTapTargetPrompt.Builder(this@AddTaskFragment)
            .setTarget(R.id.btnAdd)
            .setBackgroundColour(resources.getColor(R.color.pink))
            .setFocalColour(resources.getColor(R.color.green))
            .setPrimaryText("Add You Task")
            .setSecondaryText("You can add you tasks after filling all the information by clicking this button.")
            .setPromptFocal(RectanglePromptFocal())
            .setPromptStateChangeListener(MaterialTapTargetPrompt.PromptStateChangeListener { prompt, state ->
                if (state == MaterialTapTargetPrompt.STATE_FOCAL_PRESSED
                    || state == MaterialTapTargetPrompt.STATE_NON_FOCAL_PRESSED
                ) {

                 changeVisibility(1)
                }
            })
            .show()
    }


}