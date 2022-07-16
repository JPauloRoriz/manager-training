package com.example.managertraining.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.managertraining.databinding.FragmentHomeBinding
import com.example.managertraining.databinding.FragmentItemTrainingBinding
import com.example.managertraining.domain.model.TrainingModel
import com.example.managertraining.domain.model.UserModel
import com.example.managertraining.presentation.ui.adapter.training.TrainingAdapter


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewPager: ViewPager2
    private lateinit var trainingAdapter: TrainingAdapter
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
        setupView()
        apagar()
        setupListeners()


    }


    private fun setupView() {
        viewPager = binding.viewpager
    }

    private fun setupListeners() {

    }

    private fun setupObservers() {

    }


    private fun apagar() {
        binding.viewpager.isVisible = true

        val fragment = TrainingFragment.newInstance(TrainingModel("1", "", "", ""))
        fragment.isEmpty = true


        val fragments = mutableListOf(
            TrainingFragment.newInstance(
                TrainingModel(
                    id = "1",
                    name = "Braço",
                    description = "DFLKJGIADOHJÕPDHF~IOREHOI´HRE",
                    data = "Segunda feira"
                )
            ),
            TrainingFragment.newInstance(
                TrainingModel(
                    id = "2",
                    name = "Perna",
                    description = "DFLKJGIADOHJÕPDHF~IOREHOI´HRE",
                    data = "Segunda feira"
                )
            ),
            TrainingFragment.newInstance(
                TrainingModel(
                    id = "3",
                    name = "Ombro",
                    description = "DFLKJGIADOHJÕPDHF~IOREHOI´HRE",
                    data = "Segunda feira"
                )
            ),
            TrainingFragment.newInstance(
                TrainingModel(
                    id = "4",
                    name = "Costa",
                    description = "DFLKJGIADOHJÕPDHF~IOREHOI´HRE",
                    data = "Segunda feira"
                )
            )
        )
        fragments.add(fragment)

        trainingAdapter = TrainingAdapter(this, fragments)


        viewPager.adapter = trainingAdapter
    }

    companion object {
        const val KEY_USER: String = "user"
    }


}