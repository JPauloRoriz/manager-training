package com.example.managertraining.presentation.ui.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.managertraining.R
import com.example.managertraining.databinding.FragmentEditTrainingBinding
import com.example.managertraining.domain.model.TrainingModel
import com.example.managertraining.presentation.viewmodel.edittraining.EditTrainingViewModel
import com.example.managertraining.presentation.viewmodel.edittraining.model.EditTrainingEvent
import org.koin.androidx.viewmodel.ext.android.viewModel


class EditTrainingFragment : Fragment() {
    private lateinit var binding: FragmentEditTrainingBinding
    private val viewModel by viewModel<EditTrainingViewModel>()
    private val training by lazy { arguments?.getSerializable(KEY_TRAINING) as TrainingModel }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditTrainingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setupListeners()
        setupObservers()
        setupViews()
    }

    private fun setupViews() {
        viewModel.verificationTrainingIsEmpty(training)
        binding.btnDelete.isGone = training.isEmpty
    }


    private fun setupListeners() {
        binding.btnCreateOrEdit.setOnClickListener {
            viewModel.createOrEditTraining(
                training.isEmpty,
                training.idUser,
                training.id,
                binding.edtNameTraining.text.toString(),
                binding.edtDescriptionTraining.text.toString()
            )
        }

        binding.btnDelete.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle(getString(R.string.atention))
                .setMessage(getString(R.string.delete_training_message))
                .setPositiveButton(getString(R.string.yes)) { dialog, which ->
                    viewModel.deleteTraining(training)
                }
                .setNegativeButton(getString(R.string.not)) { dialog, witch ->
                    dialog.dismiss()
                }
                .create()
                .show()
        }
    }

    private fun setupObservers() {
        viewModel.stateLiveData.observe(viewLifecycleOwner) { state ->
            binding.tvMessageError.text = state.messageError
            binding.progressBar.isVisible = state.isLoading
            binding.btnCreateOrEdit.text = state.textOfButton
            binding.tvCrieteOrEditTraining.text = state.textOfButton
            binding.edtNameTraining.setText(state.nameTraining)
            binding.edtDescriptionTraining.setText(state.description)
            binding.edtDescriptionTraining.setText(state.description)
        }

        viewModel.eventLiveData.observe(viewLifecycleOwner) { event ->
            when (event) {
                is EditTrainingEvent.SuccessAddTraining -> {
                    Toast.makeText(requireContext(), event.message, Toast.LENGTH_LONG).show()
                    findNavController().popBackStack()
                }
                is EditTrainingEvent.SuccessDeleteTraining -> {
                    Toast.makeText(requireContext(), event.message, Toast.LENGTH_LONG).show()
                    findNavController().popBackStack()
                }
            }
        }
    }


    companion object {
        const val KEY_TRAINING: String = "training"
    }

}