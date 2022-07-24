package com.example.managertraining.data.model

import android.net.Uri
import android.widget.ImageView
import com.google.common.io.Resources

data class ExerciseResponse(
    var id: String = "",
    val name: String = "",
    val idTraining: String = "",
    var image: String = "",
    val note: String = ""
)
