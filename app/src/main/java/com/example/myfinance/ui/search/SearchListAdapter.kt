package com.example.myfinance.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myfinance.data.Lesson
import com.example.myfinance.databinding.ItemLessonsItemBinding


class SearchListAdapter(private val onItemClicked: (Lesson) -> Unit)
    : ListAdapter<Lesson, SearchListAdapter.SearchViewHolder>(DiffCallback) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchListAdapter.SearchViewHolder {
        return SearchViewHolder(ItemLessonsItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: SearchListAdapter.SearchViewHolder, position: Int) {
        val current = getItem(position)
        holder.itemView.setOnClickListener{
            onItemClicked(current)
        }
        holder.bind(current)
    }

    class SearchViewHolder(private var binding: ItemLessonsItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(lesson: Lesson){
            binding.apply{
                lessonImage.setImageResource(lesson.imageResourceId)
                lessonName.text = lesson.name
            }
        }
    }
    companion object{
        private val DiffCallback = object: DiffUtil.ItemCallback<Lesson>(){
            override fun areItemsTheSame(oldLesson: Lesson, newLesson: Lesson): Boolean {
                return oldLesson == newLesson
            }

            override fun areContentsTheSame(oldLesson: Lesson, newLesson: Lesson): Boolean {
                return oldLesson.name == newLesson.name
            }
        }

    }
}