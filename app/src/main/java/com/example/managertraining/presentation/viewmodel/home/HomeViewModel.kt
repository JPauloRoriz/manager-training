package com.example.managertraining.presentation.viewmodel.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.managertraining.domain.model.ExerciseModel
import com.example.managertraining.domain.model.TrainingModel
import com.example.managertraining.domain.model.UserModel
import com.example.managertraining.domain.usecase.exercise.contract.GetExerciseUseCase
import com.example.managertraining.domain.usecase.training.contract.GetTrainingsUseCase
import com.example.managertraining.presentation.viewmodel.base.SingleLiveEvent
import com.example.managertraining.presentation.viewmodel.home.model.HomeEvent
import com.example.managertraining.presentation.viewmodel.home.model.HomeState
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getExerciseUseCase: GetExerciseUseCase,
    private val getTrainingsModelUseCase: GetTrainingsUseCase,
    private val user: UserModel
) : ViewModel() {

    private var currentTraining: TrainingModel? = null
    val stateLiveData = MutableLiveData(HomeState())
    val eventLiveData = SingleLiveEvent<HomeEvent>()

    init {
        getTrainings()
    }


    fun getTrainings() {
        viewModelScope.launch {
            getTrainingsModelUseCase.getTrainings(user.id.toString()).onSuccess { listTrainings ->
                refreshTrainingsWithButtonAdd(listTrainings.toMutableList())
                eventLiveData.value = HomeEvent.GoToInitList
            }.onFailure {

            }
        }
    }

    fun getExercisesByTraining() {
        if (currentTraining?.id.isNullOrEmpty()) return
        currentTraining?.id?.let { idTraining ->
            stateLiveData.setLoadingLogin(true)
            viewModelScope.launch {
                getExerciseUseCase.invoke(idTraining).onSuccess {
                    stateLiveData.setExercises(it)
                }.onFailure {
                    stateLiveData.setLoadingLogin(false)
                }
            }
        }
    }

    fun tapOnTraining(training: TrainingModel) {
        eventLiveData.value = HomeEvent.GoToTraining(training)
    }

    fun tapOnAddExercise() {
        currentTraining?.id?.let { idTraining ->
            eventLiveData.value = HomeEvent.GoToCreateExercise(idTraining, null)
        }
    }

    fun tapOnEditExercise(exercise : ExerciseModel){
        currentTraining?.id?.let { idTraining ->
            eventLiveData.value = HomeEvent.GoToCreateExercise(idTraining, exercise)
        }
    }

    private fun refreshTrainingsWithButtonAdd(trainingModel: MutableList<TrainingModel>) {
        stateLiveData.value = stateLiveData.value?.apply {
            listTrainings.clear()
            listTrainings.addAll(trainingModel.apply {
                add(TrainingModel(idUser = user.id.toString()))
            })
        }
    }

    fun changeTraining(position: Int) {
        stateLiveData.value?.listTrainings?.let { listTrainings ->
            if (listTrainings.size - 1 == position) return
            val currentTraining = listTrainings[position]
            this.currentTraining = currentTraining
            getExercisesByTraining()
        }
    }

    private fun MutableLiveData<HomeState>.setExercises(value: List<ExerciseModel>) {
        this.value = this.value?.copy(isLoading = false, listExercises = value)
    }

    private fun MutableLiveData<HomeState>.setLoadingLogin(value: Boolean) {
        this.value = this.value?.copy(isLoading = value)
    }

    private fun MutableLiveData<HomeState>.setMessageErrorLogin(message: String) {
        this.value = this.value?.copy(isLoading = false, messageError = message)
    }
}