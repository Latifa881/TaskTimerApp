package com.example.tasktimerapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tasktimerapp.R
import com.example.tasktimerapp.model.OnBoardingItem

class OnboardingItemsAdapter(private val onBoardingItems: List<OnBoardingItem>) : RecyclerView.Adapter<OnboardingItemsAdapter.OnboardingItemViewHolder> () {

    inner class OnboardingItemViewHolder(view: View) : RecyclerView.ViewHolder(view){

        private val imageOnboarding = view.findViewById<ImageView>(R.id.imageOnboarding)
        private val textTitle = view.findViewById<TextView>(R.id.textTitle)
        private val textDescription = view.findViewById<TextView>(R.id.textDescription)


        fun bind(onBoardingItem: OnBoardingItem)
        {
            imageOnboarding.setImageResource(onBoardingItem.onboardingImage)
            textTitle.text = onBoardingItem.title
            textDescription.text = onBoardingItem.description
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnboardingItemViewHolder {

        return OnboardingItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.onboarding_item_container,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: OnboardingItemViewHolder, position: Int) {

        holder.bind(onBoardingItems[position])
    }

    override fun getItemCount() = onBoardingItems.size

}