package com.example.managertraining.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.red
import androidx.core.os.bundleOf
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.managertraining.R
import com.example.managertraining.databinding.FragmentItemTrainingBinding
import com.example.managertraining.domain.model.TrainingModel
import com.example.managertraining.presentation.viewmodel.training.TrainingViewModel
import com.example.managertraining.presentation.viewmodel.training.model.TrainingEvent
import org.koin.androidx.viewmodel.ext.android.viewModel


class TrainingFragment : Fragment() {
    private val viewModel by viewModel<TrainingViewModel>()
    private lateinit var binding: FragmentItemTrainingBinding
    var isEmpty: Boolean = false
    private var clickTraining: ((TrainingModel) -> Unit)? = null
    private val training by lazy { arguments?.getSerializable(KEY_TRAINING) as TrainingModel }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentItemTrainingBinding.inflate(inflater, container, false)
        binding.tvNameTraining.text = training.name
        binding.tvDescriptionEditable.text = training.description
        binding.root.setOnClickListener {
            clickTraining?.invoke(training)
        }
        binding.trainingIsEmpty.root.isVisible = isEmpty

        return if (this.training.isEmpty) {
            binding.tvDescription.isGone = true
            binding.trainingIsEmpty.root.isVisible = true
            binding.root.setCardBackgroundColor(R.color.tranparent_empty.red)
            binding.root
        } else {
            binding.root

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListeners()
        setupObservers()
    }

    private fun setupListeners() {
        this.clickTraining = {
            viewModel.tapOnItemTraining(it)
        }

    }

    private fun setupObservers() {
        viewModel.eventLiveData.observe(viewLifecycleOwner) { event ->
            when (event) {

                is TrainingEvent.GoToEditTraining -> {
                    val bundle = bundleOf(EditTrainingFragment.KEY_TRAINING to event.training)
                    findNavController().navigate(
                        R.id.action_homeFragment_to_editTrainingFragment,
                        bundle
                    )
                }
            }
        }
    }


    companion object {
        fun newInstance(trainingModel: TrainingModel): TrainingFragment {
            val args = Bundle().apply {
                putSerializable(KEY_TRAINING, trainingModel)
            }
            val fragment = TrainingFragment()
            fragment.arguments = args
            return fragment
        }
        const val KEY_TRAINING = "training"
    }
}
