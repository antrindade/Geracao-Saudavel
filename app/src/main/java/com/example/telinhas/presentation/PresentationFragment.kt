package com.example.telinhas.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.telinhas.R
import com.example.telinhas.constants.GenerationConstants
import com.example.telinhas.databinding.FragmentPresentationBinding
import com.example.telinhas.presentation.viewmodel.PresentationViewModel

class PresentationFragment : Fragment() {

    private lateinit var binding: FragmentPresentationBinding
    private lateinit var viewModel: PresentationViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View {
        binding = FragmentPresentationBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[PresentationViewModel::class.java]

        binding.buttonNext.setOnClickListener {
            findNavController().navigate(R.id.action_presentationFragment_to_loginFragment)
        }

        viewModel.verifyAuthentication()

        observe()

        return binding.root
    }

    private fun biometricAuthentication() {
        val executor = ContextCompat.getMainExecutor(requireContext())
        val bio =
            BiometricPrompt(this, executor, object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    findNavController().navigate(R.id.action_presentationFragment_to_homeActivity2)
                    activity?.finish()
                }
            })
        val info = BiometricPrompt.PromptInfo.Builder()
            .setTitle(GenerationConstants.Project.TITLE)
            .setSubtitle(GenerationConstants.Biometric.SUBTITLE)
            .setDescription(GenerationConstants.Biometric.DESCRIPTION)
            .setNegativeButtonText(GenerationConstants.Biometric.BUTTON_TEXT)
            .build()

        bio.authenticate(info)
    }

    private fun observe() {
        viewModel.loggedUser.observe(viewLifecycleOwner) { authentication ->
            if (authentication) {
                biometricAuthentication()
            }
        }
    }
}