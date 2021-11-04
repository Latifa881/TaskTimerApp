package com.example.tasktimerapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.tasktimerapp.R
import com.example.tasktimerapp.fragments.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    lateinit var frameLayout: FrameLayout
    lateinit var btNavigationView: BottomNavigationView
    lateinit var fab: FloatingActionButton
    lateinit var homeFragment:HomeFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        frameLayout = findViewById(R.id.frameLayout)
        btNavigationView = findViewById(R.id.btNavigationView)

        btNavigationView.background = null
        btNavigationView.menu.getItem(2).isEnabled = false
         fab = findViewById(R.id.fabMain)
        //Now let's the default Fragment
        homeFragment = HomeFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frameLayout,homeFragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()

        fab.setOnClickListener {
            changeFragment(AddTaskFragment())
           // startActivity(Intent(this, AddTaskActivity::class.java))
        }

        btNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    changeFragment(HomeFragment())
               //     startActivity(Intent(this, MainActivity::class.java))
                }
                R.id.view -> {
                    changeFragment(ViewTaskFragment())
                    //startActivity(Intent(this, ViewTaskActivity::class.java))

                }
                R.id.alarm -> {
                    changeFragment(AlarmFragment())
                  //  startActivity(Intent(this, AddTaskActivity::class.java))

                }
                R.id.summary -> {
                    changeFragment(SummaryTaskFragment())
                //    startActivity(Intent(this, SummaryTaskActivity::class.java))
                }

            }
            true
        }

    }
    fun changeFragment(fragment:Fragment){
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frameLayout,fragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()
    }

}