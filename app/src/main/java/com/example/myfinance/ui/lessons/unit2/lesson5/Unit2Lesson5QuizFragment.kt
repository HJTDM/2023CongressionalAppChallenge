package com.example.myfinance.ui.lessons.unit2.lesson5

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.example.myfinance.R
import com.example.myfinance.databinding.FragmentUnit2Lesson5MainBinding
import com.example.myfinance.databinding.FragmentUnit2Lesson5QuizBinding
import com.example.myfinance.ui.tools.budget.BudgetActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database

class Unit2Lesson5QuizFragment : Fragment() {

    private var _binding: FragmentUnit2Lesson5QuizBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth
    private lateinit var userDatabase: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate binding
        _binding = FragmentUnit2Lesson5QuizBinding.inflate(inflater, container, false)
        // Hide results and home button
        binding.results.visibility = View.GONE
        binding.returnToHomeButton.visibility = View.GONE

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth
        userDatabase = auth.currentUser?.let { Firebase.database.reference.child("users").child(it.uid) }!!

        // Bind buttons to methods
        binding.submitButton.setOnClickListener{
            displayCorrectAnswers()
        }

        binding.returnToHomeButton.setOnClickListener{
            activity?.finish()
        }
    }

    private fun displayCorrectAnswers(){
        // Return if not all questions are answered
        if(binding.question1Options.checkedRadioButtonId == View.NO_ID ||
            binding.question2Options.checkedRadioButtonId == View.NO_ID ||
            binding.question3Options.checkedRadioButtonId == View.NO_ID ||
            binding.question4Options.checkedRadioButtonId == View.NO_ID ||
            binding.question5Options.checkedRadioButtonId == View.NO_ID){
            Toast.makeText(this.context, "Please answer all questions before submitting", Toast.LENGTH_SHORT).show()
            return
        }

        // Check answers and adjust score
        var score = 0
        if(binding.question1Options.checkedRadioButtonId == R.id.question_1_option_3){
            score++
        }
        if(binding.question2Options.checkedRadioButtonId == R.id.question_2_option_3){
            score++
        }
        if(binding.question3Options.checkedRadioButtonId == R.id.question_3_option_1){
            score++
        }
        if(binding.question4Options.checkedRadioButtonId == R.id.question_4_option_2){
            score++
        }
        if(binding.question5Options.checkedRadioButtonId == R.id.question_5_option_4){
            score++
        }

        // Display results
        binding.results.text = getString(R.string.results, score, 5)
        binding.results.visibility = View.VISIBLE
        binding.returnToHomeButton.visibility = View.VISIBLE

        // Update score for lesson in database
        userDatabase.child("lessons").child("unit2").child("lesson5").setValue(score)
    }
}
