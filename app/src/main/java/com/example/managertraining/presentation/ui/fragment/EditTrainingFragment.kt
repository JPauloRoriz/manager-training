package com.example.managertraining.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.managertraining.R
import com.example.managertraining.databinding.FragmentEditTrainingBinding
import com.example.managertraining.domain.model.TrainingModel
import com.example.managertraining.presentation.viewmodel.edittraining.EditTrainingViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class EditTrainingFragment : Fragment() {
    private lateinit var binding : FragmentEditTrainingBinding
    private val viewModel by viewModel<EditTrainingViewModel>()
    private val training = arguments?.getParcelable<TrainingModel>(KEY_TRAINING)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditTrainingBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        training?.let { viewModel.verificationTrainingIsEmpty(it) }
    }



    companion object {
        const val KEY_TRAINING: String = "training"
    }

}