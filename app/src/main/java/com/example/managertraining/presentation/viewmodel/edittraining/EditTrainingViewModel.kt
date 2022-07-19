package com.example.managertraining.presentation.viewmodel.edittraining

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.managertraining.R
import com.example.managertraining.domain.exception.DefaultException
import com.example.managertraining.domain.exception.EmptyFildException
import com.example.managertraining.domain.exception.NoConnectionInternetException
import com.example.managertraining.domain.model.TrainingModel
import com.example.managertraining.domain.usecase.training.contract.CreateTrainingUseCase
import com.example.managertraining.domain.usecase.training.contract.DeleteTrainingUseCase
import com.example.managertraining.domain.usecase.training.contract.UpdateTrainingUseCase
import com.example.managertraining.presentation.viewmodel.base.SingleLiveEvent
import com.example.managertraining.presentation.viewmodel.edittraining.model.EditTrainingEvent
import com.example.managertraining.presentation.viewmodel.edittraining.model.EditTrainingState
import kotlinx.coroutines.launch
import java.io.IOException

class EditTrainingViewModel(
    private val context: android.content.Context,
    private val createTrainingUseCase: CreateTrainingUseCase,
    private val updateTrainingUseCase: UpdateTrainingUseCase,
    private val deleteTrainingUseCase: DeleteTrainingUseCase,
) : ViewModel() {

    val stateLiveData = MutableLiveData(
        EditTrainingState(
            textOfButton = context.getString(
                R.string.create_training
            )
        )
    )
    val eventLiveData = SingleLiveEvent<EditTrainingEvent>()


    fun verificationTrainingIsEmpty(training: TrainingModel) {
        if (!training.isEmpty) {
            stateLiveData.value = EditTrainingState(
                textOfButton = context.getString(R.string.edti_training),
                nameDay = context.getString(R.string.name_day, training.data),
                title = context.getString(R.string.edti_training),
                nameTraining = training.name,
                description = training.description
            )
        }
    }

    fun createOrEditTraining(
        trainingIsEmpty: Boolean,
        idUser: String?,
        idTraining: String?,
        nameTraining: String,
        descriptionTraining: String
    ) {
        return if (trainingIsEmpty) {
            createTraining(idUser, nameTraining, descriptionTraining)
        } else {
            editTraining(idTraining, nameTraining, descriptionTraining)
        }
    }

    private fun editTraining(
        idTraining: String?,
        nameTraining: String,
        descriptionTraining: String
    ) {
        stateLiveData.setLoading(true)
        viewModelScope.launch {
            updateTrainingUseCase.invoke(idTraining, nameTraining, descriptionTraining).onSuccess {
                stateLiveData.setLoading(false)
                eventLiveData.value =
                    EditTrainingEvent.SuccessAddTraining(context.getString(R.string.training_edit_success))
            }.onFailure { error ->
                when (error) {
                    is EmptyFildException ->
                        stateLiveData.setMessageError(context.getString(R.string.empty_fild))
                    is IOException ->
                        stateLiveData.setMessageError(context.getString(R.string.not_internet))
                    is NoConnectionInternetException ->
                        stateLiveData.setMessageError(
                            context.getString(R.string.not_internet)
                        )
                    is DefaultException ->
                        stateLiveData.setMessageError(context.getString(R.string.error_default_edit))
                }
            }
        }
    }

    private fun createTraining(idUser: String?, nameTraining: String, descriptionTraining: String) {
        stateLiveData.setLoading(true)

        viewModelScope.launch {
            createTrainingUseCase.invoke(idUser, nameTraining, descriptionTraining).onSuccess {
                stateLiveData.setLoading(false)
                eventLiveData.value =
                    EditTrainingEvent.SuccessAddTraining(context.getString(R.string.training_created_success))
            }.onFailure { error ->
                when (error) {
                    is EmptyFildException ->
                        stateLiveData.setMessageError(context.getString(R.string.empty_fild))
                    is IOException ->
                        stateLiveData.setMessageError(context.getString(R.string.not_internet))
                    is NoConnectionInternetException ->
                        stateLiveData.setMessageError(
                            context.getString(R.string.not_internet)
                        )
                    is DefaultException ->
                        stateLiveData.setMessageError(context.getString(R.string.error_default))
                }
            }
        }
    }

    fun deleteTraining(training: TrainingModel) {
        stateLiveData.setLoading(true)
        viewModelScope.launch {
            deleteTrainingUseCase.invoke(training).onSuccess {
                stateLiveData.setLoading(false)
                eventLiveData.value =
                    EditTrainingEvent.SuccessDeleteTraining(context.getString(R.string.training_delete_success))


            }.onFailure {
                stateLiveData.setLoading(false)
                stateLiveData.setMessageError(it.message.toString())
            }
        }
    }

    private fun MutableLiveData<EditTrainingState>.setLoading(value: Boolean) {
        this.value = this.value?.copy(isLoading = value)
    }

    private fun MutableLiveData<EditTrainingState>.setMessageError(message: String) {
        this.value = this.value?.copy(isLoading = false, messageError = message)
    }


}