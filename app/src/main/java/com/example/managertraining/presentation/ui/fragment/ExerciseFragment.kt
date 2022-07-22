package com.example.managertraining.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.managertraining.R
import com.example.managertraining.databinding.FragmentExerciseBinding
import com.example.managertraining.domain.model.ExerciseModel
import com.example.managertraining.presentation.ui.extension.setNavigationResult
import com.example.managertraining.presentation.viewmodel.exercise.ExerciseViewModel
import com.example.managertraining.presentation.viewmodel.exercise.model.ExerciseEvent
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class ExerciseFragment : Fragment() {

    private val idTraining by lazy { arguments?.getString(KEY_TRAINING) }
    private val exercise by lazy { arguments?.getParcelable<ExerciseModel>(KEY_EXERCISE) }
    private val viewModel by viewModel<ExerciseViewModel> {
        parametersOf(idTraining, exercise)
    }
    private lateinit var binding: FragmentExerciseBinding

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
                ""
            )
        }

        binding.btnDelete.setOnClickListener {
            viewModel.deleteExercise()
        }
    }

    private fun setupObservers() {
        viewModel.stateLiveData.observe(viewLifecycleOwner) { state ->
            binding.progressBar.isVisible = state.isLoading
            binding.btnCreateOrEdit.text = state.textButtonConfirm
            binding.tvCreateOrEditExercise.text = state.textTitleAction
            binding.tvAddPhoto.isGone = !state.showTrash
            binding.icAddImage.isGone = state.showIcAdd
            binding.tvAddPhoto.isGone = state.showTextAdd
            binding.btnDelete.isGone = !state.showTrash
            binding.edtNameExercise.setText(state.nameExercise)
            binding.edtNoteExercise.setText(state.noteExercise)
            if (exercise?.image.isNullOrEmpty()) {
                Glide.with(requireContext())
                    .load(exercise?.image)
                    .error(R.drawable.ic_image_empty)
                    .placeholder(R.drawable.ic_image_empty)
                    .fitCenter()
                    .into(binding.imgExercise)
            } else {
                binding.imgExercise.setImageResource(R.drawable.ic_image_empty)
            }
        }

        viewModel.eventLiveData.observe(viewLifecycleOwner) { event ->
            when (event) {
                is ExerciseEvent.SuccessExercise -> {
                    this.setNavigationResult(
                        exercise?.id,
                        HomeFragment.KEY_EXERCISE
                    )
                    Toast.makeText(requireContext(), event.message, Toast.LENGTH_LONG).show()
                    findNavController().popBackStack()
                }
                is ExerciseEvent.MessageError -> {
                    Toast.makeText(requireContext(), event.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    companion object {
        const val KEY_TRAINING: String = "training"
        const val KEY_EXERCISE: String = "exercise"
    }


}