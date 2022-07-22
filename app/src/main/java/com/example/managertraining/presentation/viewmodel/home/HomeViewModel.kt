package com.example.managertraining.presentation.viewmodel.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.managertraining.domain.model.TrainingModel
import com.example.managertraining.domain.model.UserModel
import com.example.managertraining.domain.usecase.exercise.contract.GetExerciseUseCase
import com.example.managertraining.domain.usecase.training.contract.GetTrainingsUseCase
import com.example.managertraining.presentation.viewmodel.base.SingleLiveEvent
import com.example.managertraining.presentation.viewmodel.exercise.model.ExerciseState
import com.example.managertraining.presentation.viewmodel.home.model.HomeEvent
import com.example.managertraining.presentation.viewmodel.home.model.HomeState
import com.example.managertraining.presentation.viewmodel.login.model.LoginState
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getExerciseUseCase : GetExerciseUseCase,
    private val getTrainingsModelUseCase: GetTrainingsUseCase,
    private val user: UserModel
) : ViewModel() {

    val stateLiveData = MutableLiveData(HomeState())
    val eventLiveData = SingleLiveEvent<HomeEvent>()

    init {
        refreshTrainingsWithButtonAdd(mutableListOf())
        getTrainings()
    }


    fun getTrainings() {
        viewModelScope.launch {
            getTrainingsModelUseCase.getTrainings(user.id.toString()).onSuccess { listTrainings ->
                refreshTrainingsWithButtonAdd(listTrainings.toMutableList())
            }.onFailure {

            }
        }
    }

    fun getExercise(training: TrainingModel){
        stateLiveData.setLoadingLogin(true)
        viewModelScope.launch{
                stateLiveData.setLoadingLogin(false)
                getExerciseUseCase.invoke(training.id).onSuccess {
                    stateLiveData.value = HomeState(
                        listExercises = it
                    )
                }.onFailure {
                    stateLiveData.setLoadingLogin(false)
                }
        }
    }

    fun tapOnTraining(training: TrainingModel) {
        eventLiveData.value = HomeEvent.GoToTraining(training)
    }

    fun tapOnAddExercise() {
        eventLiveData.value = HomeEvent.GoToCreateExercise
    }

    private fun refreshTrainingsWithButtonAdd(trainingModel: MutableList<TrainingModel>) {
        stateLiveData.value = stateLiveData.value?.apply {
            listTrainings.clear()
            listTrainings.addAll(trainingModel.apply {
                add(TrainingModel(idUser = user.id.toString()))
            })
        }
    }

    private fun MutableLiveData<HomeState>.setLoadingLogin(value: Boolean) {
        this.value = this.value?.copy(isLoading = value)
    }

    private fun MutableLiveData<HomeState>.setMessageErrorLogin(message: String) {
        this.value = this.value?.copy(isLoading = false, messageError = message)
    }
}