package com.example.managertraining.presentation.viewmodel.exercise.model

sealed class ExerciseEvent {
    data class SuccessExercise(val message : String) : ExerciseEvent()
}