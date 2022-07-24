package com.example.managertraining.presentation.viewmodel.exercise.model

import android.net.Uri

data class ExerciseState(
    val isLoading: Boolean = false,
    val showTrash: Boolean = false,
    val showIcAdd: Boolean = true,
    val showTextAdd: Boolean = true,
    val textButtonConfirm: String = "",
    val textTitleAction: String = "",
    var nameExercise: String? = "",
    var noteExercise: String? = "",
    var imageExercise: String = ""
)
