package com.example.managertraining.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.managertraining.databinding.FragmentExerciseBinding
import com.example.managertraining.domain.model.ExerciseModel
import com.example.managertraining.domain.model.TrainingModel
import com.example.managertraining.presentation.viewmodel.exercise.ExerciseViewModel
import com.example.managertraining.presentation.viewmodel.exercise.model.ExerciseEvent
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class ExerciseFragment : Fragment() {

    private val training by lazy { arguments?.getParcelable<TrainingModel>(KEY_TRAINING) }
    private val exercise by lazy { arguments?.getParcelable<ExerciseModel>(KEY_EXERCISE) }
    private val viewModel by viewModel<ExerciseViewModel>() {
        parametersOf(training, exercise)
    }
    private lateinit var binding: FragmentExerciseBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentExerciseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        setupObservers()
    }

    private fun setupListeners() {
        binding.btnCreateOrEdit.setOnClickListener {
            viewModel.createOrEditExercise(
                binding.edtNameExercise.text.toString(),
                binding.edtNoteExercise.text.toString(),
                binding.imgExercise.toString()
            )
        }

        binding.btnDelete.setOnClickListener {
            viewModel.deleteExercise()
        }
    }

    private fun setupObservers() {
        viewModel.stateLiveData.observe(viewLifecycleOwner) { state ->
            binding.progressBar.isVisible = state.isLoading
        }

        viewModel.eventLiveData.observe(viewLifecycleOwner) { event ->
            when (event) {
                is ExerciseEvent.SuccessExercise -> {
                    Toast.makeText(requireContext(), event.message, Toast.LENGTH_LONG).show()
                    findNavController().popBackStack()
                }
            }
        }
    }

    companion object {
        const val KEY_TRAINING: String = "training"
        const val KEY_EXERCISE: String = "exercise"
    }


}