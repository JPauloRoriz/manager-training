package com.example.managertraining.presentation.ui.component.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.managertraining.domain.model.TrainingModel

class PagerDiffUtil(
    private val oldList: List<TrainingModel>,
    private val newList: List<TrainingModel>
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}