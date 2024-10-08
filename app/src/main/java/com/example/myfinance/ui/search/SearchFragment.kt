package com.example.myfinance.ui.search

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myfinance.MainActivity
import com.example.myfinance.databinding.FragmentSearchBinding
import com.example.myfinance.ui.lessons.unit2.lesson5.Unit2Lesson5Activity
import com.example.myfinance.ui.tools.interest.InterestCalculatorActivity

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!
    private val searchViewModel: SearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate binding
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Set up data binding (for LiveData)
        binding.apply {
            viewModel = searchViewModel
            lifecycleOwner = viewLifecycleOwner
            searchFragment = this@SearchFragment
        }

        binding.searchBar.clearFocus()
        binding.searchBar.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                // If searching nothing, show all lessons
                if (p0.isNullOrEmpty()){
                    searchViewModel.resetLessons()
                    binding.allLessonsText.visibility = View.VISIBLE
                    return false
                }

                // Otherwise, filter lessons with the searched keyword
                searchViewModel.filterLessons(p0)
                binding.allLessonsText.visibility = View.GONE
                return false
            }

            // Same as above, but allows lessons filter while typing
            override fun onQueryTextChange(p0: String?): Boolean {
                if (p0.isNullOrEmpty()){
                    searchViewModel.resetLessons()
                    binding.allLessonsText.visibility = View.VISIBLE
                    return true
                }

                searchViewModel.filterLessons(p0)
                binding.allLessonsText.visibility = View.GONE
                return true
            }
        })

        val unit1Adapter = SearchListAdapter{}
        binding.unit1RecyclerView.adapter = unit1Adapter
        // Submit a new list of lessons displayed when list is filtered
        searchViewModel.unit1Lessons.observe(this.viewLifecycleOwner){ lessons ->
            lessons.let{
                unit1Adapter.submitList(it)
            }
        }
        // List is displayed vertically
        binding.unit1RecyclerView.layoutManager = LinearLayoutManager(this.context)

        // TODO: use Lesson parameter to launch different lesson activities
        val unit2Adapter = SearchListAdapter{
            val intent = Intent(activity, Unit2Lesson5Activity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            startActivity(intent)
        }
        binding.unit2RecyclerView.adapter = unit2Adapter
        searchViewModel.unit2Lessons.observe(this.viewLifecycleOwner){ lessons ->
            lessons.let{
                unit2Adapter.submitList(it)
            }
        }
        binding.unit2RecyclerView.layoutManager = LinearLayoutManager(this.context)

        val unit3Adapter = SearchListAdapter{}
        binding.unit3RecyclerView.adapter = unit3Adapter
        searchViewModel.unit3Lessons.observe(this.viewLifecycleOwner){ lessons ->
            lessons.let{
                unit3Adapter.submitList(it)
            }
        }
        binding.unit3RecyclerView.layoutManager = LinearLayoutManager(this.context)

        val unit4Adapter = SearchListAdapter{}
        binding.unit4RecyclerView.adapter = unit4Adapter
        searchViewModel.unit4Lessons.observe(this.viewLifecycleOwner){ lessons ->
            lessons.let{
                unit4Adapter.submitList(it)
            }
        }
        binding.unit4RecyclerView.layoutManager = LinearLayoutManager(this.context)

        val unit5Adapter = SearchListAdapter{}
        binding.unit5RecyclerView.adapter = unit5Adapter
        searchViewModel.unit5Lessons.observe(this.viewLifecycleOwner){ lessons ->
            lessons.let{
                unit5Adapter.submitList(it)
            }
        }
        binding.unit5RecyclerView.layoutManager = LinearLayoutManager(this.context)

        val unit6Adapter = SearchListAdapter{}
        binding.unit6RecyclerView.adapter = unit6Adapter
        searchViewModel.unit6Lessons.observe(this.viewLifecycleOwner){ lessons ->
            lessons.let{
                unit6Adapter.submitList(it)
            }
        }
        binding.unit6RecyclerView.layoutManager = LinearLayoutManager(this.context)
    }

    // When returning from a lesson, clear search bar and focus to hide keyboard
    override fun onResume() {
        super.onResume()

        binding.searchBar.setQuery("", true)
        binding.searchBar.clearFocus()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}