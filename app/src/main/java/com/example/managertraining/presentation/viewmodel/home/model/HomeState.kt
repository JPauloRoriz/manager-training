package com.example.managertraining.presentation.viewmodel.home.model

import com.example.managertraining.domain.model.ExerciseModel
import com.example.managertraining.domain.model.TrainingModel

data class HomeState(
    var isLoadingTraining: Boolean = false,
    var isLoadingExercise: Boolean = false,
    val messageError: String = "",
    var listTrainings: MutableList<TrainingModel> = mutableListOf(),
    var listExercises: List<ExerciseModel> = listOf(),
    var messageExerciseEmpty: Boolean = false
)
