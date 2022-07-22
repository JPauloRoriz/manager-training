package com.example.managertraining.presentation.viewmodel.exercise.model

import com.example.managertraining.domain.model.ExerciseModel

data class ExerciseState(
    val isLoading : Boolean = false,
    val messageError : String = "false"
)
