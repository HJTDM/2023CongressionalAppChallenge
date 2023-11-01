package com.example.myfinance.ui.tools.budget

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.myfinance.databinding.FragmentSetBudgetBinding

class SetBudgetFragment : Fragment() {

//    private val navigationArgs: ItemDetailFragmentArgs by navArgs()

    private var _binding: FragmentSetBudgetBinding? = null
    private val binding get() = _binding!!

    private val viewModel: BudgetViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSetBudgetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.saveBudgetAction.setOnClickListener{
            setBudgetAmount()
        }
    }

    /**
     * Called before fragment is destroyed.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        // Hide keyboard.
        val inputMethodManager = requireActivity().getSystemService(INPUT_METHOD_SERVICE) as
                InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)
        _binding = null
    }

    private fun isBudgetAmountValid(): Boolean{
        return viewModel.isBudgetAmountValid(
            binding.budgetAmount.text.toString()
        )
    }

    private fun setBudgetAmount(){
        if(isBudgetAmountValid()){
            viewModel.setBudgetAmount(binding.budgetAmount.text.toString())
        }

        val action = SetBudgetFragmentDirections.actionSetBudgetFragmentToBudgetListFragment()
        findNavController().navigate(action)
    }
}
