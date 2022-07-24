package com.example.managertraining.presentation.viewmodel.exercise.model

import android.net.Uri

data class ExerciseState(
    val isLoading: Boolean = false,
    val showTrash: Boolean = false,
    val showAddNewItem: Boolean = true,
    val textButtonConfirm: String = "",
    val textTitleAction: String = "",
    var nameExercise: String? = "",
    var noteExercise: String? = "",
    var imageExercise: String = ""
)
