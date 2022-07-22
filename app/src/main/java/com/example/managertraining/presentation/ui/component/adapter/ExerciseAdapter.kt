package com.example.managertraining.presentation.ui.component.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.managertraining.databinding.ItemExerciseBinding
import com.example.managertraining.domain.model.ExerciseModel
import com.example.managertraining.presentation.ui.component.adapter.exercise.viewholder.ExerciseViewHolder

class ExerciseAdapter : ListAdapter<ExerciseModel, ExerciseViewHolder>(COMPARATOR) {
    var clickExercise: ((ExerciseModel) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        return ExerciseViewHolder(
            ItemExerciseBinding.inflate(LayoutInflater.from(parent.context), parent, false).root
        )
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        holder.bind(currentList[position], clickExercise)
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    override fun submitList(list: List<ExerciseModel>?) {
        list?.toMutableList()?.let {
            super.submitList(ArrayList(it))
        }
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<ExerciseModel>() {
            override fun areItemsTheSame(oldItem: ExerciseModel, newItem: ExerciseModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: ExerciseModel,
                newItem: ExerciseModel
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}