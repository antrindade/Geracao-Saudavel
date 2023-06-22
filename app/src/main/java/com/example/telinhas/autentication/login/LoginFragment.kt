package com.example.telinhas.autentication.login

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
import com.example.telinhas.R
import com.example.telinhas.autentication.viewmodel.LoginViewModel
import com.example.telinhas.constants.GenerationConstants
import com.example.telinhas.databinding.FragmentLoginBinding
import com.google.android.material.snackbar.Snackbar

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View {

        binding = FragmentLoginBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        binding.buttonLogin.setOnClickListener { view ->
            closeKeyboard()
            handleLogin(view)
        }

        binding.textViewRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.textViewRecovery.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_recoverEmailFragment)
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
                message(view, GenerationConstants.Success.SUCCESSFULLY_LOGGED_IN, Color.GREEN)
                findNavController().navigate(R.id.action_loginFragment_to_homeActivity2)
                activity?.finish()
            }else {
                message(view, GenerationConstants.Success.SUCCESSFULLY_LOGGED_IN, Color.GREEN)
                findNavController().navigate(R.id.action_loginFragment_to_firstMain)
                activity?.finish()
            }
        }
    }

    private fun handleLogin(view: View) {
        val email = binding.editTextEmail.text.toString()
        val password = binding.editTextPassword.text.toString()

        if (email.isEmpty() || password.isEmpty()) {
            message(view, GenerationConstants.Exception.EMPTY_FIELD, Color.RED)
        } else {
            viewModel.verifyLogin(email, password)
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