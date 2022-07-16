package com.example.managertraining.presentation.viewmodel.training

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.managertraining.domain.model.TrainingModel
import com.example.managertraining.presentation.viewmodel.base.SingleLiveEvent
import com.example.managertraining.presentation.viewmodel.training.model.TrainingEvent
import com.example.managertraining.presentation.viewmodel.training.model.TrainingState

class TrainingViewModel(

) : ViewModel() {

    val stateLiveData = MutableLiveData(TrainingState())
    val eventLiveData = SingleLiveEvent<TrainingEvent>()

    fun tapOnItemTraining(trainingModel: TrainingModel) {
        eventLiveData.value = TrainingEvent.GoToEditTraining(trainingModel)
    }
}