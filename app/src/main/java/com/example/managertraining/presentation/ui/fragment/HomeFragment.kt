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
import com.example.managertraining.domain.model.UserModel
import com.example.managertraining.presentation.ui.adapter.exercise.ExerciseAdapter
import com.example.managertraining.presentation.ui.adapter.training.TrainingAdapter
import com.example.managertraining.presentation.viewmodel.home.HomeViewModel
import com.example.managertraining.presentation.viewmodel.home.model.HomeEvent
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel by viewModel<HomeViewModel>()
    private lateinit var viewPager: ViewPager2
    private lateinit var trainingAdapter: TrainingAdapter
    private val exerciseAdapter by lazy { ExerciseAdapter() }
    private val user by lazy { arguments?.getSerializable(KEY_USER) as UserModel }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getTrainings(user.id.toString())
        setupView()
        setupListeners()
        setupObservers()

    }


    private fun setupView() {
        val fragments = viewModel.getTrainings(user.id.toString())
        trainingAdapter = TrainingAdapter(this, fragments)
        viewPager = binding.viewpager
        viewPager.adapter = trainingAdapter
        binding.rvExercise.adapter = exerciseAdapter
    }

    private fun setupListeners() {
        binding.btnFloating.setOnClickListener {
            viewModel.tapOnAddExercise(user)
        }
    }

    private fun setupObservers() {
        viewModel.stateLiveData.observe(viewLifecycleOwner) { homeState ->
            val listFragments = homeState.listTrainings.map { trainingModel ->
                TrainingFragment.newInstance(trainingModel)
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
            }
        }
    }


    companion object {
        const val KEY_USER: String = "user"
    }


}