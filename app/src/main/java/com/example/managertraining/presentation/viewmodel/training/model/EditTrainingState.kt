package com.example.managertraining.presentation.viewmodel.training.model

data class EditTrainingState(
    val isLoading : Boolean = false,
    val messageError : String = "",
    val textOfButton: String = "",
    val nameDay : String = "",
    val title : String = "",
    var nameTraining : String = "",
    var description : String = ""
)