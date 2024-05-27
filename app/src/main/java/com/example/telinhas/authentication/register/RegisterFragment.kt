package com.example.telinhas.authentication.register

import android.content.Context
import android.graphics.Color
import android.view.inputmethod.InputMethodManager
import androidx.navigation.fragment.findNavController
import com.example.telinhas.app.Binding
import com.example.telinhas.authentication.viewmodel.RegisterViewModel
import com.example.telinhas.constants.GenerationConstants
import com.example.telinhas.databinding.FragmentRegisterBinding
import com.example.telinhas.state.DataState
import com.example.telinhas.ui.BaseFragment
import com.google.android.material.snackbar.Snackbar
import org.koin.android.ext.android.inject


@Binding(FragmentRegisterBinding::class)
class RegisterFragment : BaseFragment<FragmentRegisterBinding>() {

    private val viewModel: RegisterViewModel by inject()

    override fun setup() {
        observer()
        listener()
    }

    private fun observer() {
        viewModel.authentication.observe(viewLifecycleOwner) {uiState ->
            uiState ?: return@observe

            when(uiState) {
                is DataState.ErrorMessage -> {
                    message(uiState.error, Color.RED)
                }
                DataState.Loading -> {

                }
                is DataState.Success -> {
                    message(uiState.data, Color.GREEN)
                    findNavController().popBackStack()
                }
            }
        }
    }

    private fun listener() {
        binding.buttonSignUp.setOnClickListener {
            closeKeyboard()
            handleSave()
        }
    }

    private fun handleSave() {
        val name = binding.editTextName.text.toString()
        val email = binding.editTextEmail.text.toString()
        val password = binding.editTextPassword.text.toString()

        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            message(GenerationConstants.Exception.EMPTY_FIELD, Color.RED)
        } else {
            viewModel.verifyRegister(name, email, password)
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
}