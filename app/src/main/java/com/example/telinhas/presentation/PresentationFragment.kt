package com.example.telinhas.presentation

import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.example.telinhas.R
import com.example.telinhas.app.Binding
import com.example.telinhas.authentication.login.LoginFragment
import com.example.telinhas.constants.GenerationConstants
import com.example.telinhas.databinding.FragmentLoginBinding
import com.example.telinhas.databinding.FragmentPresentationBinding
import com.example.telinhas.presentation.viewmodel.PresentationViewModel
import com.example.telinhas.ui.BaseFragment
import org.koin.android.ext.android.inject

@Binding(FragmentPresentationBinding::class)
class PresentationFragment : BaseFragment<FragmentPresentationBinding>() {

    private val viewModel: PresentationViewModel by inject()

    override fun setup() {
        this.setView()
        this.observe()
        this.listener()
        this.verifyAuthentication()
    }

    private fun setView() {
        setStatusBarColor(R.color.white)
    }

    private fun observe() {
        viewModel.loggedUser.observe(viewLifecycleOwner) { authentication ->
            if (authentication) {
                biometricAuthentication()
            }
        }
    }

    private fun listener() {
        binding.buttonNext.setOnClickListener {
            findNavController().navigate(R.id.action_presentationFragment_to_loginFragment)
        }
    }

    private fun verifyAuthentication() {
        viewModel.verifyAuthentication()
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

    companion object {
        fun newInstance() = PresentationFragment()
    }
}