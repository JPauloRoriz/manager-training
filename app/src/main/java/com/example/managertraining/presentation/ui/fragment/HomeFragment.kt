package com.example.managertraining.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.managertraining.R
import com.example.managertraining.databinding.FragmentHomeBinding
import com.example.managertraining.domain.model.TrainingModel
import com.example.managertraining.domain.model.UserModel
import com.example.managertraining.presentation.ui.component.adapter.ExerciseAdapter
import com.example.managertraining.presentation.ui.component.adapter.TrainingPageAdapter
import com.example.managertraining.presentation.ui.extension.clearNavigationResult
import com.example.managertraining.presentation.ui.extension.getNavigationResult
import com.example.managertraining.presentation.viewmodel.home.HomeViewModel
import com.example.managertraining.presentation.viewmodel.home.model.HomeEvent
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val user by lazy { arguments?.getParcelable<UserModel>(KEY_USER) }
    private val viewModel by viewModel<HomeViewModel> {
        parametersOf(user)
    }

    var clickTraining: ((TrainingModel) -> Unit)? = null
    private val exerciseAdapter by lazy { ExerciseAdapter() }
    private lateinit var trainingAdapter : TrainingPageAdapter

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
        trainingAdapter = TrainingPageAdapter(requireActivity())
        binding.rvExercise.adapter = exerciseAdapter
        binding.viewpager.adapter = trainingAdapter
    }

    private fun setupListeners() {
        binding.btnFloating.setOnClickListener {
            viewModel.tapOnAddExercise()
        }

        clickTraining = {
            viewModel.tapOnTraining(it)
        }

        trainingAdapter.clickTraining = {
            viewModel.tapOnTraining(it)
        }

        binding.viewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                viewModel.changeTraining(position)
            }

            override fun onPageScrollStateChanged(state: Int) {
            }

        })

        exerciseAdapter.clickExercise = { exerciseModel ->
            viewModel.tapOnEditExercise(exerciseModel)
        }
    }

    private fun setupObservers() {

        this.getNavigationResult<String>(KEY_TRAININGS)?.observe(viewLifecycleOwner) {
            viewModel.getTrainings()
            clearNavigationResult<String>(KEY_TRAININGS)
        }

        this.getNavigationResult<String>(KEY_EXERCISE)?.observe(viewLifecycleOwner) {
            viewModel.getExercisesByTraining()
            clearNavigationResult<String>(KEY_EXERCISE)
        }

        viewModel.stateLiveData.observe(viewLifecycleOwner) { homeState ->
            trainingAdapter.submitList(homeState.listTrainings)
            exerciseAdapter.submitList(homeState.listExercises)
        }

        viewModel.eventLiveData.observe(viewLifecycleOwner) { event ->
            when (event) {
                is HomeEvent.GoToCreateExercise -> {
                    val bundle = bundleOf(
                        ExerciseFragment.KEY_TRAINING to event.idTraining,
                        ExerciseFragment.KEY_EXERCISE to event.exerciseModel
                    )
                    findNavController().navigate(
                        R.id.action_homeFragment_to_ExerciseFragment,
                        bundle
                    )
                }
                is HomeEvent.GoToTraining -> {
                    findNavController().navigate(
                        R.id.action_homeFragment_to_editTrainingFragment,
                        bundleOf(TrainingFragment.KEY_TRAINING to event.training)
                    )
                }
                HomeEvent.GoToInitList -> {
                    binding.viewpager.currentItem = 0
                }
            }
        }
    }


    companion object {
        const val KEY_USER: String = "user"
        const val KEY_TRAININGS: String = "trainings"
        const val KEY_EXERCISE: String = "exercise"
    }


}