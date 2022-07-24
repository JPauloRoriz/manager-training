package com.example.managertraining.presentation.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.aminography.choosephotohelper.ChoosePhotoHelper
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
    private lateinit var choosePhotoHelper: ChoosePhotoHelper
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
        setupListeners(savedInstanceState)
        setupObservers()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        choosePhotoHelper.onActivityResult(requestCode, resultCode, data)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        choosePhotoHelper.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        choosePhotoHelper.onSaveInstanceState(outState)
    }


    private fun setupListeners(savedInstanceState: Bundle?) {
        choosePhotoHelper = ChoosePhotoHelper.with(this)
            .asBitmap()
            .withState(savedInstanceState)
            .alwaysShowRemoveOption(true)
            .build { image ->
                viewModel.saveImage(image)
                binding.imgExercise.setImageBitmap(image)
            }

        binding.btnCreateOrEdit.setOnClickListener {
            viewModel.createOrEditExercise(
                binding.edtNameExercise.text.toString(),
                binding.edtNoteExercise.text.toString(),
            )
        }

        binding.btnDelete.setOnClickListener {
            viewModel.deleteExercise()
        }

        binding.imgExercise.setOnClickListener {
            choosePhotoHelper.showChooser()
        }
    }

    private fun setupObservers() {
        viewModel.stateLiveData.observe(viewLifecycleOwner) { state ->
            binding.progressBar.isVisible = state.isLoading
            binding.btnCreateOrEdit.text = state.textButtonConfirm
            binding.tvCreateOrEditExercise.text = state.textTitleAction
            binding.icAddImage.isVisible = state.showAddNewItem
            binding.tvAddPhoto.isVisible = state.showAddNewItem
            binding.btnDelete.isVisible = state.showTrash
            binding.edtNameExercise.setText(state.nameExercise)
            binding.edtNoteExercise.setText(state.noteExercise)

            if (!exercise?.image.isNullOrEmpty()) {
                Glide.with(requireContext())
                    .load(exercise?.image)
                    .error(R.drawable.ic_image_empty)
                    .placeholder(R.drawable.ic_image_empty)
                    .fitCenter()
                    .into(binding.imgExercise)
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