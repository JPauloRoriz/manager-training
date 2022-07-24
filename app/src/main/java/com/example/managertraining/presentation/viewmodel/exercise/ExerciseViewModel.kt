package com.example.managertraining.presentation.viewmodel.exercise

import android.content.Context
import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.managertraining.R
import com.example.managertraining.domain.exception.DefaultException
import com.example.managertraining.domain.exception.NoConnectionInternetException
import com.example.managertraining.domain.model.ExerciseModel
import com.example.managertraining.domain.usecase.exercise.contract.CreateExerciseUseCase
import com.example.managertraining.domain.usecase.exercise.contract.DeleteExerciseUseCase
import com.example.managertraining.domain.usecase.exercise.contract.SaveImageUseCase
import com.example.managertraining.domain.usecase.exercise.contract.UpdateExerciseUseCase
import com.example.managertraining.presentation.viewmodel.base.SingleLiveEvent
import com.example.managertraining.presentation.viewmodel.exercise.model.ExerciseEvent
import com.example.managertraining.presentation.viewmodel.exercise.model.ExerciseState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ExerciseViewModel(
    private val idTraining: String,
    private var exercise: ExerciseModel? = null,
    private val context: Context,
    private val createExerciseUseCase: CreateExerciseUseCase,
    private val deleteExerciseUseCase: DeleteExerciseUseCase,
    private val updateExerciseUseCase: UpdateExerciseUseCase,
    private val saveImageUseCase: SaveImageUseCase
) : ViewModel() {
    private var currentImage: Bitmap? = null
    val stateLiveData = MutableLiveData(ExerciseState())
    val eventLiveData = SingleLiveEvent<ExerciseEvent>()

    private val isEdition = exercise != null

    init {
        stateLiveData.value = stateLiveData.value?.copy(
            isLoading = false,
            showTrash = isEdition,
            showAddNewItem = exercise?.image.isNullOrEmpty(),
            textButtonConfirm = if (isEdition) context.getString(R.string.edit_exercise) else context.getString(
                R.string.create_exercise
            ),
            textTitleAction = if (isEdition) context.getString(R.string.edit_exercise) else context.getString(
                R.string.tv_add_exercise
            ),
            nameExercise = if (isEdition) exercise?.name else "",
            noteExercise = if (isEdition) exercise?.note else "",
            imageExercise = "",
        )
    }


    fun deleteExercise() {
        viewModelScope.launch {
            stateLiveData.setLoading(true)
            deleteExerciseUseCase.invoke(exercise?.id ?: "").onSuccess {
                stateLiveData.setLoading(false)
                eventLiveData.value =
                    ExerciseEvent.SuccessExercise(context.getString(R.string.exercise_deleted_success))
            }.onFailure { error ->
                stateLiveData.setLoading(false)
                when (error) {
                    is NoConnectionInternetException -> {
                        eventLiveData.value =
                            ExerciseEvent.MessageError(context.getString(R.string.not_internet))
                    }
                    is DefaultException -> {
                        eventLiveData.value =
                            ExerciseEvent.MessageError(context.getString(R.string.error_default_exercise))
                    }
                }
            }
        }
    }

    fun createOrEditExercise(name: String, note: String) {
        stateLiveData.setLoading(true)
        viewModelScope.launch(Dispatchers.IO) {
            uploadImage { urlImage ->
                viewModelScope.launch(Dispatchers.IO) {
                    exercise?.let { exercise ->
                        editExercise(exercise.id, name, note, urlImage)
                    } ?: run {
                        createExercise(name, note, urlImage)
                    }
                }
            }

        }

    }

    private suspend fun uploadImage(success: (String) -> Unit) {
        if (currentImage == null) success(exercise?.image ?: "")
        currentImage?.let {
            stateLiveData.postValue(
                stateLiveData.value?.copy(
                    isLoading = true,
                    showAddNewItem = false
                )
            )
            saveImageUseCase.invoke(currentImage).onSuccess { url ->
                url?.let { success(url) }
            }.onFailure {
                eventLiveData.value =
                    ExerciseEvent.MessageError(context.getString(R.string.error_upload_image))
            }
        }
    }

    private suspend fun editExercise(id: String, name: String, note: String, iamge: String) {
        updateExerciseUseCase.invoke(id, name, note, iamge).onSuccess {
            stateLiveData.setLoading(false)
            eventLiveData.postValue(ExerciseEvent.SuccessExercise(context.getString(R.string.exercise_edited_success)))
        }.onFailure { error ->
            stateLiveData.setLoading(false)
            when (error) {
                is NoConnectionInternetException -> {
                    eventLiveData.value =
                        ExerciseEvent.MessageError(context.getString(R.string.not_internet))
                }
                is DefaultException -> {
                    eventLiveData.value =
                        ExerciseEvent.MessageError(context.getString(R.string.error_default_exercise))
                }
            }
        }
    }

    private suspend fun createExercise(name: String, note: String, image: String) {
        idTraining.let {
            createExerciseUseCase.invoke(it, name, note, image).onSuccess {
                stateLiveData.setLoading(false)
                eventLiveData.postValue(ExerciseEvent.SuccessExercise(context.getString(R.string.exercise_add_success)))

            }.onFailure { error ->
                stateLiveData.setLoading(false)
                when (error) {
                    is NoConnectionInternetException -> {
                        eventLiveData.value =
                            ExerciseEvent.MessageError(context.getString(R.string.not_internet))
                    }
                    is DefaultException -> {
                        eventLiveData.value =
                            ExerciseEvent.MessageError(context.getString(R.string.error_default_crate_exercise))
                    }
                }
            }
        }
    }

    private fun MutableLiveData<ExerciseState>.setLoading(value: Boolean) {
        this.postValue(this.value?.copy(isLoading = value))
    }

    fun saveImage(data: Bitmap?) {
        this.currentImage = data
    }

}