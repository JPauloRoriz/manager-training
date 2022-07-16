package com.example.managertraining.presentation.viewmodel.edittraining

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.managertraining.R
import com.example.managertraining.domain.model.TrainingModel
import com.example.managertraining.presentation.viewmodel.base.SingleLiveEvent
import com.example.managertraining.presentation.viewmodel.edittraining.model.EditTrainingEvent
import com.example.managertraining.presentation.viewmodel.edittraining.model.EditTrainingState

class EditTrainingViewModel(
    private val context: android.content.Context
) : ViewModel() {

    val stateLiveData = MutableLiveData(EditTrainingState())
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


    init {

    }
}