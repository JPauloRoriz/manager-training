package com.example.managertraining.presentation.ui.adapter.training

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.managertraining.presentation.ui.fragment.TrainingFragment

class TrainingAdapter(
    fragment: Fragment,
    private val fragments: List<TrainingFragment>
) : FragmentStateAdapter(fragment) {

    override fun getItemCount() = fragments.size

    override fun createFragment(position: Int): Fragment = fragments[position]


}