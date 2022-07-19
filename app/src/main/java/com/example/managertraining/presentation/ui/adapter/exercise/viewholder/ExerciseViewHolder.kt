package com.example.managertraining.presentation.ui.adapter.exercise.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.managertraining.databinding.ItemExerciseBinding
import com.example.managertraining.domain.model.ExerciseModel

class ExerciseViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding: ItemExerciseBinding by lazy {
        ItemExerciseBinding.bind(itemView)
    }

    fun bind(exercise: ExerciseModel) {
        with(binding) {
            Glide.with(this.imgExercise.context)
                .load(exercise.image)
                .into(this.imgExercise)
        }

        binding.tvNameExercise.text = exercise.name
        binding.tvNoteExercise.text = exercise.note
    }
}