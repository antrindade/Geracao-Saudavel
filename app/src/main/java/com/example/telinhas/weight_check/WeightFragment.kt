package com.example.telinhas.weight_check

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
import com.example.telinhas.constants.GenerationConstants
import com.example.telinhas.databinding.FragmentWeightBinding
import com.example.telinhas.weight_check.viewmodel.WeightViewModel
import com.google.android.material.snackbar.Snackbar

class WeightFragment : Fragment() {

    private lateinit var binding: FragmentWeightBinding
    private lateinit var viewModel: WeightViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentWeightBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[WeightViewModel::class.java]

        binding.buttonSend.setOnClickListener {view ->
            handleSave(view)
            closeKeyboard()
        }

        return binding.root
    }

    private fun handleSave(view: View) {
        val quantityKg = binding.editTextPeso.text.toString()

        if (quantityKg.isEmpty()) {
            message(view, GenerationConstants.Exception.EMPTY_FIELD, Color.RED)
        } else {
            viewModel.saveData(quantityKg)

            val action =
                WeightFragmentDirections.actionWeightFragmentToResultFragment(quantityKg.toInt())

            findNavController().navigate(action)
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