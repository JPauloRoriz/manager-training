package com.example.managertraining.presentation.viewmodel.home.model

import com.example.managertraining.domain.model.TrainingModel

data class HomeState(
    val isLoading : Boolean = false,
    val listTrainings : List<TrainingModel> = listOf()
)
