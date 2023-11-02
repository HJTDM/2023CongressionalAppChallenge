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

//    private val navigationArgs: ItemDetailFragmentArgs by navArgs()

    private var _binding: FragmentAddBudgetItemBinding? = null
    private val binding get() = _binding!!

    private val viewModel: BudgetViewModel by activityViewModels()

    lateinit var budgetItem: BudgetItem

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddBudgetItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val id = navigationArgs.itemId
//        if(id > 0){
//            viewModel.retrieveItem(id).observe(this.viewLifecycleOwner){selectedItem ->
//                item = selectedItem
//                bind(item)
//            }
//        }
//        else{
            binding.saveAction.setOnClickListener{
                addNewItem()
            }
//        }
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

    private fun isEntryValid(): Boolean{
        return viewModel.isEntryValid(
            binding.itemName.text.toString(),
            binding.itemPrice.text.toString()
        )
    }

    private fun addNewItem(){
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
//
//    private fun bind(item: Item){
//        val price = "%.2f".format(item.itemPrice)
//        binding.apply {
//            itemName.setText(item.itemName, TextView.BufferType.SPANNABLE)
//            itemPrice.setText(price, TextView.BufferType.SPANNABLE)
//            itemCount.setText(item.quantityInStock.toString(), TextView.BufferType.SPANNABLE)
//            saveAction.setOnClickListener{
//                updateItem()
//            }
//        }
//    }
//
//    private fun updateItem(){
//        if(isEntryValid()){
//            viewModel.updateItem(
//                this.navigationArgs.itemId,
//                this.binding.itemName.text.toString(),
//                this.binding.itemPrice.text.toString(),
//                this.binding.itemCount.text.toString()
//            )
//            val action = AddItemFragmentDirections.actionAddItemFragmentToItemListFragment()
//            findNavController().navigate(action)
//        }
//    }
}
