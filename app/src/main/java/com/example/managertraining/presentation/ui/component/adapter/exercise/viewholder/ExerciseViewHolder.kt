package com.example.managertraining.presentation.ui.component.adapter.exercise.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.managertraining.R
import com.example.managertraining.databinding.ItemExerciseBinding
import com.example.managertraining.domain.model.ExerciseModel

class ExerciseViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding: ItemExerciseBinding by lazy {
        ItemExerciseBinding.bind(itemView)
    }

    fun bind(exercise: ExerciseModel, clickExercise: ((ExerciseModel) -> Unit)?) {
        with(binding) {

            Glide.with(this.imgExercise.context)
                .load(exercise.image)
                .error(R.drawable.ic_image_empty)
                .placeholder(R.drawable.ic_image_empty)
                .fitCenter()
                .into(this.imgExercise)

            root.setOnClickListener {
                clickExercise?.invoke(exercise)
            }
            tvNameExercise.text = exercise.name
            tvNoteExercise.text = exercise.note
        }
    }
}