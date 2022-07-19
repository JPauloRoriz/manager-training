package com.example.managertraining.presentation.viewmodel.home.model

import com.example.managertraining.domain.model.TrainingModel

data class HomeState(
    var isLoading : Boolean = false,
    var listTrainings : MutableList<TrainingModel> = mutableListOf()
)
