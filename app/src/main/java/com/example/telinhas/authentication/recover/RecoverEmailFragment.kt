package com.example.telinhas.authentication.recover

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
import com.example.telinhas.authentication.viewmodel.RecoverViewModel
import com.example.telinhas.constants.GenerationConstants
import com.example.telinhas.databinding.FragmentRecoverEmailBinding
import com.google.android.material.snackbar.Snackbar

class RecoverEmailFragment : Fragment() {

    private lateinit var binding: FragmentRecoverEmailBinding
    private lateinit var viewModel: RecoverViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        binding = FragmentRecoverEmailBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[RecoverViewModel::class.java]

        binding.buttonSend.setOnClickListener { view ->
            closeKeyboard()
            verifyEmail(view)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observe(view)
    }

    private fun observe(view: View) {
        viewModel.authentication.observe(viewLifecycleOwner) {
            message(view, GenerationConstants.Success.EMAIL_SEND_SUCCESSFULLY, Color.GREEN)
            findNavController().popBackStack()
        }
        viewModel.message.observe(viewLifecycleOwner) { message ->
            message(view, message, Color.RED)
        }
    }

    private fun verifyEmail(view: View) {
        val email = binding.editTextRecoveryEmail.text.toString()

        if (email.isEmpty()) {
            message(view, GenerationConstants.Exception.EMPTY_FIELD, Color.RED)
        } else {
            viewModel.verify(email)
        }

    }

    private fun closeKeyboard() {
        val input = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        input.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    private fun message(view: View, msg: String, color: Int) {
        val snack = Snackbar.make(view, msg, Snackbar.LENGTH_SHORT)
        snack.setBackgroundTint(color)
        snack.setTextColor(Color.WHITE)
        snack.show()
    }

}