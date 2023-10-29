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

    class ToolViewHolder(private var binding: ItemToolsItemBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun bind(tool: Tool) {
            binding.apply{
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
        val tool = list[position]
        holder.itemView.setOnClickListener{
            onItemClicked(tool)
        }
        holder.bind(tool)
    }
}