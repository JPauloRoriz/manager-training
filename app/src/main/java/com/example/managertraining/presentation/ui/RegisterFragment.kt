package com.example.managertraining.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.managertraining.databinding.FragmentRegisterBinding
import com.example.managertraining.presentation.viewmodel.register.RegisterViewModel
import com.example.managertraining.presentation.viewmodel.register.model.RegisterEvent
import kotlinx.coroutines.runBlocking
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterFragment : Fragment() {

    private val viewModel by viewModel<RegisterViewModel>()
    private lateinit var binding: FragmentRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        setupObservers()
    }

    private fun setupListeners() {
        binding.btnConfirm.setOnClickListener {
            runBlocking {
                viewModel.tapOnRegister(
                    binding.edtInputName.text.toString(),
                    binding.edtInputLogin.text.toString(),
                    binding.edtInputPassword.text.toString(),
                    binding.edtInputConfirmPassword.text.toString()
                )
            }
        }
    }

    private fun setupObservers() {
        viewModel.stateLiveData.observe(viewLifecycleOwner) { state ->
            binding.pbLoading.isVisible = state.isLoading
            binding.tvMessageError.text = state.messageError
        }

        viewModel.eventLiveData.observe(viewLifecycleOwner) { event ->
            when (event) {
                is RegisterEvent.SuccessRegister -> {
                    Toast.makeText(requireContext(), event.message, Toast.LENGTH_SHORT).show()
                    findNavController ().popBackStack()
                }
            }
        }

    }

}