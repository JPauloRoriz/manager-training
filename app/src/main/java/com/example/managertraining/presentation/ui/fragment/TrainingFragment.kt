package com.example.managertraining.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.red
import androidx.core.view.isGone
import androidx.core.view.isInvisible
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


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentItemTrainingBinding.inflate(inflater, container, false)


        arguments?.getParcelable<TrainingModel>(KEY_TRAINING)?.let { training ->
            binding.tvNameTraining.text = training.name
            binding.tvDescriptionEditable.text = training.description
            binding.root.setOnClickListener {
                clickTraining?.invoke(training)
            }
        }
        binding.trainingIsEmpty.root.isVisible = isEmpty

        return if (this.isEmpty) {
            binding.tvDescription.isVisible = false
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
                    val bundle = Bundle().apply {
                        putParcelable(EditTrainingFragment.KEY_TRAINING, event.training)
                    }
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
                putParcelable(KEY_TRAINING, trainingModel)
            }
            val fragment = TrainingFragment()
            fragment.arguments = args
            return fragment
        }

        const val KEY_TRAINING = "training"
    }

}