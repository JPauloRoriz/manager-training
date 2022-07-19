package com.example.managertraining.presentation.viewmodel.home.model

import com.example.managertraining.domain.model.TrainingModel

sealed class HomeEvent {
    object GoToCreateExercise : HomeEvent()
    data class GoToTraining(val training: TrainingModel) : HomeEvent()
}