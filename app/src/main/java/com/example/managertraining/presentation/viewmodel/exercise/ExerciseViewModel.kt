package com.example.managertraining.presentation.viewmodel.exercise

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.managertraining.R
import com.example.managertraining.domain.model.ExerciseModel
import com.example.managertraining.domain.model.TrainingModel
import com.example.managertraining.domain.usecase.exercise.contract.CreateExerciseUseCase
import com.example.managertraining.domain.usecase.exercise.contract.DeleteExerciseUseCase
import com.example.managertraining.domain.usecase.exercise.contract.UpdateExerciseUseCase
import com.example.managertraining.presentation.viewmodel.base.SingleLiveEvent
import com.example.managertraining.presentation.viewmodel.exercise.model.ExerciseEvent
import com.example.managertraining.presentation.viewmodel.exercise.model.ExerciseState
import kotlinx.coroutines.launch

class ExerciseViewModel(
    private val exercise: ExerciseModel?,
    private val training: TrainingModel?,
    private val context: Context,
    private val createExerciseUseCase: CreateExerciseUseCase,
    private val deleteExerciseUseCase: DeleteExerciseUseCase,
    private val updateExerciseUseCase: UpdateExerciseUseCase
) : ViewModel() {
    val stateLiveData = MutableLiveData(ExerciseState())
    val eventLiveData = SingleLiveEvent<ExerciseEvent>()


    fun deleteExercise() {
        viewModelScope.launch {
            stateLiveData.setLoading(true)
            training?.id?.let { deleteExerciseUseCase.invoke(it) }
        }
    }

    fun createOrEditExercise(name: String, note: String, image: String) {
        stateLiveData.setLoading(true)
        if (exercise == null) {
            createExercise(name, note, image)
        } else {
            editExercise(exercise.id, name, note)
        }
    }

    private fun editExercise(id: String, name: String, note: String) {
        viewModelScope.launch{
            updateExerciseUseCase.invoke(id, name, note).onSuccess {
                stateLiveData.setLoading(false)

            }.onFailure {
                stateLiveData.setLoading(false)
                eventLiveData.value =
                    ExerciseEvent.SuccessExercise(context.getString(R.string.exercise_edited_success))
            }
        }
    }

    private fun createExercise(name: String, note: String, image: String) {
        viewModelScope.launch {
            training?.id?.let {
                createExerciseUseCase.invoke(it, name, note, image).onSuccess {
                    stateLiveData.setLoading(false)
                    eventLiveData.value =
                        ExerciseEvent.SuccessExercise(context.getString(R.string.exercise_add_success))

                }.onFailure {
                    stateLiveData.setLoading(false)

                }
            }

        }
    }

    private fun MutableLiveData<ExerciseState>.setLoading(value: Boolean) {
        this.value = this.value?.copy(isLoading = value)
    }

    private fun MutableLiveData<ExerciseState>.setMessageError(message: String) {
        this.value = this.value?.copy(isLoading = false, messageError = message)
    }
}