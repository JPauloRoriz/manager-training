package com.example.managertraining.presentation.viewmodel.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.managertraining.domain.model.TrainingModel
import com.example.managertraining.domain.model.UserModel
import com.example.managertraining.domain.usecase.training.contract.GetTrainingsUseCase
import com.example.managertraining.presentation.ui.fragment.TrainingFragment
import com.example.managertraining.presentation.viewmodel.base.SingleLiveEvent
import com.example.managertraining.presentation.viewmodel.home.model.HomeEvent
import com.example.managertraining.presentation.viewmodel.home.model.HomeState
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getTrainingsModelUseCase: GetTrainingsUseCase
) : ViewModel() {

    val stateLiveData = MutableLiveData(HomeState())
    val eventLiveData = SingleLiveEvent<HomeEvent>()


    fun getTrainings(idUser: String): List<TrainingFragment> {
        viewModelScope.launch {
            getTrainingsModelUseCase.getTrainings(idUser).onSuccess { listTrainings ->
                if (listTrainings.isEmpty()) {
                    stateLiveData.value = HomeState(
                        listTrainings = listOf(
                            TrainingModel(
                                idUser = idUser,
                                id = "",
                                name = "",
                                description = "",
                                data = "",
                                isEmpty = true
                            )
                        )
                    )
                } else {
                    val list : MutableList<TrainingModel> = mutableListOf()
                    list.addAll(listTrainings)
                    list.add(
                        TrainingModel(
                            idUser = idUser,
                            id = "",
                            name = "",
                            description = "",
                            data = "",
                            isEmpty = true
                        )
                    )
                    listTrainings.toList()
                    stateLiveData.value = HomeState(listTrainings = list)
                }

            }.onFailure {

            }
        }
        return listOf()
    }

    fun tapOnAddExercise(user: UserModel) {
        eventLiveData.value = HomeEvent.GoToCreateExercise
    }
}