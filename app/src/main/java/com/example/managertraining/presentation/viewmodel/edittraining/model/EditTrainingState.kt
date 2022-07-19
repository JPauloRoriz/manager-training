package com.example.managertraining.presentation.viewmodel.edittraining.model

data class EditTrainingState(
    val isLoading : Boolean = false,
    val messageError : String = "",
    val textOfButton: String = "",
    val nameDay : String = "",
    val title : String = "",
    val nameTraining : String = "",
    val description : String = ""
)