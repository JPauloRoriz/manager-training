package com.example.managertraining.presentation.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.managertraining.R
import com.example.managertraining.databinding.FragmentCreateExerciseBinding
import com.example.managertraining.domain.model.UserModel


class CreateExerciseFragment : Fragment() {

    private val user by lazy { arguments?.getSerializable(KEY_USER) as UserModel }
    private lateinit var binding: FragmentCreateExerciseBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateExerciseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        const val KEY_USER: String = "user"
    }


}