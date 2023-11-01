package com.example.myfinance.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myfinance.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val searchViewModel: SearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            viewModel = searchViewModel
            lifecycleOwner = viewLifecycleOwner
            searchFragment = this@SearchFragment
        }
        binding.searchBar.clearFocus()
        binding.searchBar.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                if (p0.isNullOrEmpty()){
                    searchViewModel.resetLessons()
                    binding.allLessonsText.visibility = View.VISIBLE
                    return false
                }
                searchViewModel.filterLessons(p0)
                binding.allLessonsText.visibility = View.GONE
                return false
            }

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
        searchViewModel.unit1Lessons.observe(this.viewLifecycleOwner){ lessons ->
            lessons.let{
                unit1Adapter.submitList(it)
            }
        }
        binding.unit1RecyclerView.layoutManager = LinearLayoutManager(this.context)

        val unit2Adapter = SearchListAdapter{}
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}