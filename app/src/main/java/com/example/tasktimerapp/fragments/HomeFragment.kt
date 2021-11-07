package com.example.tasktimerapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.tasktimerapp.R
import com.example.tasktimerapp.adapter.OnboardingItemsAdapter
import com.example.tasktimerapp.model.OnBoardingItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.FloatingActionButton


class HomeFragment : Fragment() {
    lateinit var  onboardingItemsAdapter: OnboardingItemsAdapter
    lateinit var indicatorsContainer: LinearLayout
    lateinit var imageNext:ImageView
    lateinit var btGetStarted:MaterialButton
    lateinit var onboardingViewPager2:ViewPager2

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view= inflater.inflate(R.layout.fragment_home, container, false)
        indicatorsContainer = view.findViewById(R.id.indicatorsContainer)
        imageNext=view.findViewById(R.id.imageNext)
        btGetStarted= view.findViewById(R.id.btGetStarted)
        onboardingViewPager2 = view.findViewById(R.id.onboardingViewPager)

        setOnboardingItems()
        setupIndicators()
        setCurrentIndicator(0)

        return view
    }
    private fun setOnboardingItems(){

        onboardingItemsAdapter = OnboardingItemsAdapter(
            listOf(
                OnBoardingItem(
                    R.drawable.icget,
                    "Manage You Task",
                    "Organize all your to do's and tasks."
                ),
                OnBoardingItem(
                    R.drawable.timer,
                    "Work On Time",
                    "When you are overwhelmed by the amount of work you have on your plate, stop and rethink."
                ),
                OnBoardingItem(
                    R.drawable.time,
                    "Get Reminder On Time",
                    "If you usually get distracted ,now you can be notified to do your tasks."
                ),
                OnBoardingItem(
                    R.drawable.task,
                    "View Your History Tasks",
                    "You can see all your tasks with the time spend on each task. You can also edit and delete your tasks."
                ),
                )
        )
        onboardingViewPager2.adapter = onboardingItemsAdapter
        onboardingViewPager2.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentIndicator(position)
            }
        })
        (onboardingViewPager2.getChildAt(0)as RecyclerView).overScrollMode =
            RecyclerView.OVER_SCROLL_NEVER
        imageNext.setOnClickListener {
            if (onboardingViewPager2.currentItem+1 < onboardingItemsAdapter.itemCount){
                onboardingViewPager2.currentItem+=1
            }else{
                navigateToAddFragment()
            }
        }
        btGetStarted.setOnClickListener {
            navigateToAddFragment()
        }


    }
    private fun navigateToAddFragment(){
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.frameLayout,AddTaskFragment())
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()
    }
    private fun setupIndicators(){

        val indicators = arrayOfNulls<ImageView>(onboardingItemsAdapter.itemCount)
        val layoutParams: LinearLayout.LayoutParams = LinearLayout.LayoutParams(WRAP_CONTENT , WRAP_CONTENT)
        layoutParams.setMargins(8,0,8,0)

        for (i in indicators.indices){
            indicators[i] = ImageView(context)
            indicators[i]?.let {
                it.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.indicator_inactive_background
                    )
                )
                it.layoutParams = layoutParams
                indicatorsContainer.addView(it)
            }
        }

    }
    private fun setCurrentIndicator( position: Int){
        val childCount = indicatorsContainer.childCount
        for (i in 0 until childCount){
            val imageView = indicatorsContainer.getChildAt(i) as ImageView
            if (i == position){
                imageView.setImageDrawable(ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.indecator_active_background
                )
                )
            }else {
                imageView.setImageDrawable(ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.indicator_inactive_background
                )
                )
            }
        }
    }

}