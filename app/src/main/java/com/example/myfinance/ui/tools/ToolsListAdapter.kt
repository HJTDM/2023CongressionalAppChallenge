package com.example.myfinance.ui.tools

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myfinance.data.DataSource
import com.example.myfinance.data.Tool
import com.example.myfinance.databinding.ItemToolsItemBinding


class ToolsListAdapter(private val onItemClicked: (Tool) -> Unit)
    : RecyclerView.Adapter<ToolsListAdapter.ToolViewHolder>() {

    private val list = DataSource.tools

    // View holder for each tool item
    class ToolViewHolder(private var binding: ItemToolsItemBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun bind(tool: Tool) {
            binding.apply{
                // Set image and name of tool item
                toolImage.setImageResource(tool.imageResourceId)
                toolName.text = tool.name
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToolViewHolder {
        return ToolViewHolder(
            ItemToolsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ToolViewHolder, position: Int) {
        // Get tool item in list
        val tool = list[position]

        // Set method called when tool item is clicked
        holder.itemView.setOnClickListener{
            onItemClicked(tool)
        }

        // Bind UI components to budget item
        holder.bind(tool)
    }
}