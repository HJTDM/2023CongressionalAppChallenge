package com.example.myfinance.ui.lessons.unit2.lesson5

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myfinance.databinding.FragmentUnit2Lesson5MainBinding
import com.example.myfinance.ui.tools.budget.BudgetActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database

class Unit2Lesson5MainFragment : Fragment() {

    private var _binding: FragmentUnit2Lesson5MainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Make back button finish activity from this fragment
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true ) {
                override fun handleOnBackPressed() {
                    activity?.finish()
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate binding
        _binding = FragmentUnit2Lesson5MainBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set assessment button to navigate to quiz fragment
        binding.assessmentButton.setOnClickListener{
            val action = Unit2Lesson5MainFragmentDirections.actionUnit2Lesson5MainFragmentToUnit2Lesson5QuizFragment()
            this.findNavController().navigate(action)
        }
    }
}
