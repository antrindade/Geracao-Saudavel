package com.example.telinhas.authentication.login

import android.content.Context
import android.graphics.Color
import android.view.inputmethod.InputMethodManager
import androidx.navigation.fragment.findNavController
import com.example.telinhas.R
import com.example.telinhas.app.Binding
import com.example.telinhas.authentication.viewmodel.LoginViewModel
import com.example.telinhas.constants.GenerationConstants
import com.example.telinhas.databinding.FragmentLoginBinding
import com.example.telinhas.enum.ExceptionEnum
import com.example.telinhas.state.DataState
import com.example.telinhas.ui.BaseFragment
import com.google.android.material.snackbar.Snackbar
import org.koin.android.ext.android.inject

@Binding(FragmentLoginBinding::class)
class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    private val viewModel: LoginViewModel by inject()

    override fun setup() {
        this.setView()
        this.observer()
        this.listener()
    }

    private fun setView() {
        setStatusBarColor(R.color.white)
    }

    private fun observer() {
        viewModel.message.observe(viewLifecycleOwner) {
            message(it, Color.RED)
        }
        viewModel.authentication.observe(viewLifecycleOwner) { uiState ->
            uiState ?: return@observe

            when(uiState) {
                is DataState.Loading -> {

                }
                is DataState.ErrorMessage -> {
                   when(uiState.error) {
                        ExceptionEnum.ERROR_INVALID_EMAIL.name -> message(ExceptionEnum.ERROR_INVALID_EMAIL.message, R.color.red)
                        ExceptionEnum.ERROR_WRONG_PASSWORD.name -> message(ExceptionEnum.ERROR_WRONG_PASSWORD.message, R.color.red)
                        ExceptionEnum.ERROR_USER_NOT_FOUND.name -> message(ExceptionEnum.ERROR_USER_NOT_FOUND.message, R.color.red)
                    }
                }
                is DataState.Success -> {
                    if (uiState.data) {
                        message(GenerationConstants.Success.SUCCESSFULLY_LOGGED_IN, Color.GREEN)
                        findNavController().navigate(R.id.action_loginFragment_to_homeActivity2)
                        activity?.finish()
                    }else {
                        message(GenerationConstants.Success.SUCCESSFULLY_LOGGED_IN, Color.GREEN)
                        findNavController().navigate(R.id.action_loginFragment_to_firstMain)
                        activity?.finish()
                    }
                }
            }
        }
    }

    private fun listener() {
        binding.buttonLogin.setOnClickListener {
            closeKeyboard()
            handleLogin()
        }

        binding.textViewRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.textViewRecovery.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_recoverEmailFragment)
        }
    }

    private fun handleLogin() {
        val email = binding.editTextEmail.text.toString()
        val password = binding.editTextPassword.text.toString()

        if (email.isEmpty() || password.isEmpty()) {
            message(GenerationConstants.Exception.EMPTY_FIELD, Color.RED)
        } else {
            viewModel.verifyLogin(email, password)
        }
    }

    private fun closeKeyboard() {
        val input =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        input.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    private fun message(msg: String, color: Int) {
        val snack = Snackbar.make(requireView(), msg, Snackbar.LENGTH_SHORT)
        snack.setBackgroundTint(color)
        snack.setTextColor(Color.WHITE)
        snack.show()
    }

    companion object {
        fun newInstance() = LoginFragment()
    }
}