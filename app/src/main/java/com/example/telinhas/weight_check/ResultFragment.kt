package com.example.telinhas.weight_check

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.telinhas.R
import com.example.telinhas.constants.GenerationConstants
import com.example.telinhas.databinding.FragmentResultBinding
import com.example.telinhas.weight_check.viewmodel.ResultViewModel

class ResultFragment : Fragment() {

    private lateinit var binding: FragmentResultBinding
    private lateinit var viewModel: ResultViewModel
    private val args: ResultFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentResultBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[ResultViewModel::class.java]

        quantityResult()

        binding.buttonNext.setOnClickListener {
            findNavController().navigate(R.id.action_resultFragment_to_homeActivity)
        }

        return binding.root
    }

    private fun quantityResult() {

        val waterResult = args.quantityMl * GenerationConstants.Calculation.WALTER_MULTIPLICATION

        viewModel.saveData(waterResult)

        binding.quantityMl.text = "$waterResult ml"
    }


}