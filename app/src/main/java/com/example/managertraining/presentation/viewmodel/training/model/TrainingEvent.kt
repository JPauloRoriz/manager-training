package com.example.managertraining.presentation.viewmodel.training.model

import com.example.managertraining.domain.model.TrainingModel

sealed class TrainingEvent {
    data class GoToEditTraining(val training: TrainingModel) : TrainingEvent()
}