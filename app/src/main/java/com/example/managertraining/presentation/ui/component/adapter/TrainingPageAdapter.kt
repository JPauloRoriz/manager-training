package com.example.managertraining.presentation.ui.component.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.managertraining.domain.model.TrainingModel
import com.example.managertraining.presentation.ui.fragment.TrainingComponentFragment

class TrainingPageAdapter(
    context: FragmentActivity,
) : FragmentStateAdapter(context) {

    var clickTraining: ((TrainingModel) -> Unit)? = null
    private var items: MutableList<TrainingModel> = mutableListOf()

    override fun getItemCount() = items.size

    override fun createFragment(position: Int): Fragment =
        TrainingComponentFragment.newInstance(items[position]).apply {
            clickTraining = this@TrainingPageAdapter.clickTraining
        }

    fun submitList(newItems: List<TrainingModel>) {
        val callback = PagerDiffUtil(this.items.toList(), newItems)
        val diff = DiffUtil.calculateDiff(callback)
        this.items.clear()
        this.items.addAll(newItems)
        diff.dispatchUpdatesTo(this)
    }

}