package com.example.telinhas.authentication.recover

import android.content.Context
import android.graphics.Color
import android.view.inputmethod.InputMethodManager
import androidx.navigation.fragment.findNavController
import com.example.telinhas.app.Binding
import com.example.telinhas.authentication.viewmodel.RecoverViewModel
import com.example.telinhas.constants.GenerationConstants
import com.example.telinhas.databinding.FragmentRecoverEmailBinding
import com.example.telinhas.state.DataState
import com.example.telinhas.ui.BaseFragment
import com.google.android.material.snackbar.Snackbar
import org.koin.android.ext.android.inject

@Binding(FragmentRecoverEmailBinding::class)
class RecoverEmailFragment : BaseFragment<FragmentRecoverEmailBinding>() {

    private val viewModel: RecoverViewModel by inject()

    override fun setup() {
        this.observe()
        this.listener()
    }

    private fun observe() {
        viewModel.authentication.observe(viewLifecycleOwner) { uiState ->
            uiState ?:return@observe

            when(uiState) {

                is DataState.Loading -> {

                }

                is DataState.Success -> {
                    message(uiState.data, Color.GREEN)
                    findNavController().popBackStack()
                }

                is DataState.ErrorMessage -> {
                    message(uiState.error, Color.RED)
                }
            }
        }
    }

    private fun listener() {
        binding.buttonSend.setOnClickListener {
            closeKeyboard()
            verifyEmail()
        }
    }

    private fun verifyEmail() {
        val email = binding.editTextRecoveryEmail.text.toString()

        if (email.isEmpty()) {
            message(GenerationConstants.Exception.EMPTY_FIELD, Color.RED)
        } else {
            viewModel.verify(email)
        }

    }

    private fun closeKeyboard() {
        val input = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        input.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    private fun message(msg: String, color: Int) {
        val snack = Snackbar.make(requireView(), msg, Snackbar.LENGTH_SHORT)
        snack.setBackgroundTint(color)
        snack.setTextColor(Color.WHITE)
        snack.show()
    }

}