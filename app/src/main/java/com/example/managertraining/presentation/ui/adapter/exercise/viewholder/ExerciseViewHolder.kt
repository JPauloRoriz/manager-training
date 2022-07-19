package com.example.managertraining.presentation.ui.adapter.exercise

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.managertraining.databinding.ItemExerciseBinding

class ExerciseViewHolder(view : View) : RecyclerView.ViewHolder(view) {
    private val binding: ItemExerciseBinding by lazy {
        ItemExerciseBinding.bind(itemView)
    }
}