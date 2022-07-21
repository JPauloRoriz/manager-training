package com.example.managertraining.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import com.example.managertraining.R
import com.example.managertraining.databinding.FragmentHomeBinding
import com.example.managertraining.domain.model.TrainingModel
import com.example.managertraining.domain.model.UserModel
import com.example.managertraining.presentation.ui.adapter.exercise.ExerciseAdapter
import com.example.managertraining.presentation.ui.adapter.training.TrainingAdapter
import com.example.managertraining.presentation.ui.extension.clearNavigationResult
import com.example.managertraining.presentation.ui.extension.getNavigationResult
import com.example.managertraining.presentation.viewmodel.home.HomeViewModel
import com.example.managertraining.presentation.viewmodel.home.model.HomeEvent
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val user by lazy { arguments?.getSerializable(KEY_USER) as UserModel }
    private val viewModel by viewModel<HomeViewModel> {
        parametersOf(user)
    }

    var clickTraining: ((TrainingModel) -> Unit)? = null
    private val exerciseAdapter by lazy { ExerciseAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupListeners()
        setupObservers()

    }


    private fun setupView() {
        binding.rvExercise.adapter = exerciseAdapter
    }

    private fun setupListeners() {
        binding.btnFloating.setOnClickListener {
//            viewModel.tapOnAddExercise( )
        }

        clickTraining = {
            viewModel.tapOnTraining(it)
        }
    }

    private fun setupObservers() {

        this.getNavigationResult<String>(KEY_TRAININGS)?.observe(viewLifecycleOwner) {
            viewModel.getTrainings()
            clearNavigationResult<String>(KEY_TRAININGS)
        }

        viewModel.stateLiveData.observe(viewLifecycleOwner) { homeState ->
            val listFragments = homeState.listTrainings.map { trainingModel ->
                TrainingFragment.newInstance(trainingModel).apply {
                    clickTraining = this@HomeFragment.clickTraining
                }
            }
            binding.viewpager.adapter = TrainingAdapter(this, listFragments)
        }

        viewModel.eventLiveData.observe(viewLifecycleOwner) { event ->
            when (event) {
                is HomeEvent.GoToCreateExercise -> {
                    val bundle = bundleOf(
                        CreateExerciseFragment.KEY_USER to user
                    )
                    findNavController().navigate(
                        R.id.action_homeFragment_to_createExerciseFragment,
                        bundle
                    )
                }
                is HomeEvent.GoToTraining -> {
                    findNavController().navigate(
                        R.id.action_homeFragment_to_editTrainingFragment,
                        bundleOf(EditTrainingFragment.KEY_TRAINING to event.training)
                    )
                }
            }
        }
    }


    companion object {
        const val KEY_USER: String = "user"
        const val KEY_TRAININGS: String = "trainings"
    }


}