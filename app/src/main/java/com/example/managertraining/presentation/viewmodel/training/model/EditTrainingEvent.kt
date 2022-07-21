package com.example.managertraining.presentation.viewmodel.training.model

sealed class EditTrainingEvent {
    data class SuccessAddTraining(val message: String) : EditTrainingEvent()
    data class SuccessDeleteTraining(val message: String) : EditTrainingEvent()
}