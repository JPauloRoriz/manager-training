package com.example.managertraining.presentation.viewmodel.home.model

import com.example.managertraining.domain.model.ExerciseModel
import com.example.managertraining.domain.model.TrainingModel

sealed class HomeEvent {
    object GoToInitList : HomeEvent()
    data class GoToCreateExercise(
        val idTraining: String,
        val exerciseModel: ExerciseModel?
    ) : HomeEvent()

    data class GoToTraining(val training: TrainingModel) : HomeEvent()
}