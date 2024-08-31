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
    ): SearchViewHolder {
        return SearchViewHolder(
            ItemLessonsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        // Get current lesson item in list
        val current = getItem(position)

        // Set method called when lesson card is clicked
        holder.itemView.setOnClickListener{
            onItemClicked(current)
        }

        // Bind UI components to lesson card
        holder.bind(current)
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    // View holder for each lesson item
    class SearchViewHolder(private var binding: ItemLessonsItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(lesson: Lesson){
            binding.apply{
                // Set image and name of lesson card
                lessonImage.setImageResource(lesson.imageResourceId)
                lessonName.text = lesson.name
            }
        }
    }

    // Update RecyclerView by comparing items/contents
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