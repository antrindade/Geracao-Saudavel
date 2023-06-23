package com.example.telinhas.authentication.register

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.telinhas.authentication.viewmodel.RegisterViewModel
import com.example.telinhas.constants.GenerationConstants
import com.example.telinhas.databinding.FragmentRegisterBinding
import com.google.android.material.snackbar.Snackbar


class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private lateinit var viewModel: RegisterViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        binding = FragmentRegisterBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[RegisterViewModel::class.java]

        binding.buttonSignUp.setOnClickListener {view ->
            closeKeyboard()
            handleSave(view)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observer(view)
    }

    private fun observer(view: View) {
        viewModel.message.observe(viewLifecycleOwner) {
            message(view, it, Color.RED)
        }
        viewModel.authentication.observe(viewLifecycleOwner) {
            if (it) {
                message(view, GenerationConstants.Success.SUCCESSFULLY_REGISTERED, Color.GREEN)
                findNavController().popBackStack()
            }
        }
    }

    private fun handleSave(view: View) {
        val name = binding.editTextName.text.toString()
        val email = binding.editTextEmail.text.toString()
        val password = binding.editTextPassword.text.toString()

        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            message(view, GenerationConstants.Exception.EMPTY_FIELD, Color.RED)
        } else {
            viewModel.verifyRegister(name, email, password)
        }
    }

    private fun closeKeyboard() {
        val input =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        input.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    private fun message(view: View, msg: String, color: Int) {
        val snack = Snackbar.make(view, msg, Snackbar.LENGTH_SHORT)
        snack.setBackgroundTint(color)
        snack.setTextColor(Color.WHITE)
        snack.show()
    }
}