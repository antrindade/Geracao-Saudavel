package com.example.telinhas.home

import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.telinhas.R
import com.example.telinhas.constants.GenerationConstants
import com.example.telinhas.databinding.FragmentHomeBinding
import com.example.telinhas.home.viewmodel.HomeViewModel
import com.example.telinhas.repository.UserPreferences
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel
    private val db = FirebaseFirestore.getInstance()
    private var quantityMl = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        binding = FragmentHomeBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        binding.buttonDrink.setOnClickListener {
            animateUp()
        }

        binding.textViewUndo.setOnClickListener {
            animateDown()
        }

        binding.imageViewSettings.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_settingsFragment)
        }

        binding.imageViewNotify.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_reminderFragment)
        }

        performer()


        return binding.root
    }

    override fun onResume() {
        super.onResume()
        performer()
    }

    private fun visible() {
        binding.textViewName.visibility = View.VISIBLE
        binding.textViewEmail.visibility = View.VISIBLE
        binding.quantityMlResult.visibility = View.VISIBLE
    }

    private fun spinner(): Int {
        quantityMl = when (binding.spinner.selectedItemId.toInt()) {
            0 -> 250
            1 -> 500
            2 -> 750
            else -> 1000
        }
        return quantityMl
    }

    private fun textBold(phrase: String, KeyWord: String): SpannableStringBuilder {
        val spannable = SpannableStringBuilder(phrase)
        val startWord = phrase.indexOf(KeyWord)
        val endWord = startWord + KeyWord.length

        spannable.setSpan(
            StyleSpan(Typeface.BOLD),
            startWord,
            endWord,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        return spannable
    }

    private fun performer() {
        val shared = UserPreferences(requireContext())

        val docRef =
            db.collection(GenerationConstants.Firebase.COLLECTION)
                .document(shared.get(GenerationConstants.User.EMAIL))

        docRef.get().addOnSuccessListener { documentSnapshot ->
            val sum: Long? = documentSnapshot.getLong(GenerationConstants.Firebase.SUM)
            val amountWater = documentSnapshot.getLong(GenerationConstants.Firebase.AMOUNT_OF_WATER)
            val name = documentSnapshot.getString(GenerationConstants.User.NAME)
            val drank: Long? = documentSnapshot.getLong(GenerationConstants.Firebase.DRANK)

            if (sum == null) {
                val map = mapOf(
                    GenerationConstants.Firebase.SUM to amountWater,
                    GenerationConstants.Firebase.DRANK to 0
                )
                db.collection(GenerationConstants.Firebase.COLLECTION)
                    .document(shared.get(GenerationConstants.User.EMAIL))
                    .set(map, SetOptions.merge())

                if (amountWater != null && name != null && drank != null) {

                    shared.store(GenerationConstants.User.NAME, name.toString())

                    binding.textViewName.text = name
                    binding.textViewEmail.text = shared.get(GenerationConstants.User.EMAIL)
                    binding.quantityMltext.text = GenerationConstants.User.PROGRESSION
                    binding.quantityMlResult.text =
                        textBold("$amountWater ml", amountWater.toString())

                    visible()

                    viewModel.saveCloudFire(amountWater, drank)
                }
            } else {

                if (drank != null && amountWater != null) {
                    if (drank < amountWater) {
                        binding.textViewName.text = name
                        binding.textViewEmail.text = shared.get(GenerationConstants.User.EMAIL)
                        binding.quantityMltext.text = GenerationConstants.User.PROGRESSION
                        binding.quantityMlResult.text = textBold("$sum ml", sum.toString())

                        visible()

                        viewModel.saveCloudFire(sum, drank)

                    } else {
                        binding.textViewName.text = name
                        binding.textViewEmail.text = shared.get(GenerationConstants.User.EMAIL)
                        binding.quantityMltext.text = GenerationConstants.User.LEVEL_COMPLETED
                        binding.quantityMlResult.text =
                            textBold("Já bebeu $drank hoje!", drank.toString())

                        visible()

                        viewModel.saveCloudFire(sum, drank)
                    }
                }
            }
        }
    }

    private fun animateUp() {

        val shared = UserPreferences(requireContext())

        quantityMl = spinner()

        val docRef =
            db.collection(GenerationConstants.Firebase.COLLECTION)
                .document(shared.get(GenerationConstants.User.EMAIL))

        docRef.get().addOnSuccessListener { documentSnapshot ->
            val amountWater =
                documentSnapshot.getLong(GenerationConstants.Firebase.AMOUNT_OF_WATER)
            var sum: Long? = documentSnapshot.getLong(GenerationConstants.Firebase.SUM)
            var drank: Long? = documentSnapshot.getLong(GenerationConstants.Firebase.DRANK)

            if (amountWater != null && sum != null && drank != null) {
                drank += quantityMl.toLong()
                sum -= quantityMl.toLong()


                if (drank < amountWater) {
                    binding.textViewName.text = shared.get(GenerationConstants.User.NAME)
                    binding.textViewEmail.text = shared.get(GenerationConstants.User.EMAIL)
                    binding.quantityMltext.text = GenerationConstants.User.PROGRESSION
                    binding.quantityMlResult.text = textBold("$sum ml", sum.toString())

                    viewModel.saveCloudFire(sum, drank)
                } else {
                    binding.textViewName.text = shared.get(GenerationConstants.User.NAME)
                    binding.textViewEmail.text = shared.get(GenerationConstants.User.EMAIL)
                    binding.quantityMltext.text = GenerationConstants.User.LEVEL_COMPLETED
                    binding.quantityMlResult.text =
                        textBold("Já bebeu $drank hoje!", drank.toString())

                    viewModel.saveCloudFire(sum, drank)
                }
            }
        }
    }

    private fun animateDown() {

        val shared = UserPreferences(requireContext())

        quantityMl = spinner()

        val docRef =
            db.collection(GenerationConstants.Firebase.COLLECTION)
                .document(shared.get(GenerationConstants.User.EMAIL))

        docRef.get().addOnSuccessListener { documentSnapshot ->
            val amountWater =
                documentSnapshot.getLong(GenerationConstants.Firebase.AMOUNT_OF_WATER)
            var sum: Long? = documentSnapshot.getLong(GenerationConstants.Firebase.SUM)
            var drank: Long? = documentSnapshot.getLong(GenerationConstants.Firebase.DRANK)

            if (drank != null && sum != null && amountWater != null) {

                drank -= quantityMl.toLong()
                sum += quantityMl.toLong()

                if (drank <= 0 && sum >= amountWater) {
                    drank = 0
                    sum = amountWater
                }

                if (drank < amountWater) {

                    binding.textViewName.text = shared.get(GenerationConstants.User.NAME)
                    binding.textViewEmail.text = shared.get(GenerationConstants.User.EMAIL)
                    binding.quantityMltext.text = GenerationConstants.User.PROGRESSION
                    binding.quantityMlResult.text = textBold("$sum ml", sum.toString())

                    viewModel.saveCloudFire(sum, drank)
                } else {

                    binding.textViewName.text = shared.get(GenerationConstants.User.NAME)
                    binding.textViewEmail.text = shared.get(GenerationConstants.User.EMAIL)
                    binding.quantityMltext.text = GenerationConstants.User.LEVEL_COMPLETED
                    binding.quantityMlResult.text =
                        textBold("Já bebeu $drank hoje!", drank.toString())

                    viewModel.saveCloudFire(sum, drank)
                }
            }
        }
    }
}
