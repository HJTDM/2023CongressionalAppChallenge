package com.example.myfinance.ui.tools.budget

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myfinance.data.BudgetItem
import com.example.myfinance.data.getFormattedPrice
import com.example.myfinance.databinding.ItemBudgetListItemBinding

class BudgetListAdapter(private val onItemClicked: (BudgetItem) -> Unit)
    : ListAdapter<BudgetItem, BudgetListAdapter.ItemViewHolder>(DiffCallback){

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemViewHolder {
        return ItemViewHolder(
            ItemBudgetListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        // Get current budget item in list
        val item = getItem(position)

        // Set method called when budget item is clicked
        holder.itemView.setOnClickListener{
            onItemClicked(item)
        }

        // Bind UI components to budget item
        holder.bind(item)
    }

    // View holder for each budget item
    class ItemViewHolder(private var binding: ItemBudgetListItemBinding)
        : RecyclerView.ViewHolder(binding.root){
        fun bind(budgetItem: BudgetItem) {
            binding.apply{
                // Set item name and price of budget item
                budgetItemName.text = budgetItem.name
                budgetItemPrice.text = budgetItem.getFormattedPrice()
            }
        }
    }

    // Update RecyclerView by comparing items/contents
    companion object{
        private val DiffCallback = object : DiffUtil.ItemCallback<BudgetItem>(){
            override fun areItemsTheSame(oldBudgetItem: BudgetItem, newBudgetItem: BudgetItem): Boolean {
                return oldBudgetItem == newBudgetItem
            }

            override fun areContentsTheSame(oldBudgetItem: BudgetItem, newBudgetItem: BudgetItem): Boolean {
                return oldBudgetItem.name == newBudgetItem.name
            }
        }
    }
}