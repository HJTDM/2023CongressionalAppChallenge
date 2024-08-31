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
import com.example.myfinance.R
import com.example.myfinance.data.BudgetItem
import com.example.myfinance.databinding.FragmentAddBudgetItemBinding

class AddBudgetItemFragment : Fragment() {

    private var _binding: FragmentAddBudgetItemBinding? = null
    private val binding get() = _binding!!

    private val viewModel: BudgetViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate binding
        _binding = FragmentAddBudgetItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Add new item when save button is clicked
        binding.saveAction.setOnClickListener{
            addNewItem()
        }
    }

    /**
     * Called before fragment is destroyed.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        // Hide keyboard if item is saved or back button clicked when keyboard is up
        val inputMethodManager = requireActivity().getSystemService(INPUT_METHOD_SERVICE) as
                InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)
        _binding = null
    }

    // Check if budget item entry is valid (not blank)
    private fun isEntryValid(): Boolean{
        return viewModel.isEntryValid(
            binding.itemName.text.toString(),
            binding.itemPrice.text.toString()
        )
    }

    private fun addNewItem(){
        // Add budget item if valid
        if(isEntryValid()){
            val budgetItemOption = when (binding.budgetItemOptions.checkedRadioButtonId) {
                R.id.option_expenses -> "expenses"
                R.id.option_income -> "income"
                else -> "expenses"
            }

            viewModel.addNewBudgetItem(
                budgetItemOption,
                binding.itemName.text.toString(),
                binding.itemPrice.text.toString()
            )
        }

        val action = AddBudgetItemFragmentDirections.actionAddBudgetItemFragmentToBudgetListFragment()
        findNavController().navigate(action)
    }
}
