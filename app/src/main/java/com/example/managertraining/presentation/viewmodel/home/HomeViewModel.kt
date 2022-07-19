package com.example.managertraining.presentation.viewmodel.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.managertraining.domain.model.TrainingModel
import com.example.managertraining.domain.model.UserModel
import com.example.managertraining.domain.usecase.training.contract.GetTrainingsUseCase
import com.example.managertraining.presentation.viewmodel.base.SingleLiveEvent
import com.example.managertraining.presentation.viewmodel.home.model.HomeEvent
import com.example.managertraining.presentation.viewmodel.home.model.HomeState
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getTrainingsModelUseCase: GetTrainingsUseCase,
    user: UserModel
) : ViewModel() {

    val stateLiveData = MutableLiveData(HomeState())
    val eventLiveData = SingleLiveEvent<HomeEvent>()

    init {
        addTraining(
            mutableListOf(TrainingModel(idUser = user.id))
        )
        getTrainings(user.id.toString())
    }


    fun getTrainings(idUser: String) {
        viewModelScope.launch {
            getTrainingsModelUseCase.getTrainings(idUser).onSuccess { listTrainings ->
                addTraining(listTrainings.toMutableList())
            }.onFailure {

            }
        }
    }

    fun tapOnTraining(training: TrainingModel) {
        eventLiveData.value = HomeEvent.GoToTraining(training)
    }

    fun tapOnAddExercise(user: UserModel) {
        eventLiveData.value = HomeEvent.GoToCreateExercise
    }

    private fun addTraining(trainingModel: MutableList<TrainingModel>) {
        stateLiveData.value = stateLiveData.value?.apply {
            listTrainings.addAll(0, trainingModel)
        }
    }
}