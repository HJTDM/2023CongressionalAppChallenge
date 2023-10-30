package com.example.myfinance.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myfinance.R
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
                if (p0 == null){
                    searchViewModel.resetLessons()
                    return false
                }
                searchViewModel.filterLessons(p0)
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                if (p0 == null){
                    searchViewModel.resetLessons()
                    return true
                }
                searchViewModel.filterLessons(p0)
                return true
            }
        })

        val adapter = SearchListAdapter{
        }
        binding.unit1RecyclerView.adapter = adapter
        searchViewModel.unit1Lessons.observe(this.viewLifecycleOwner){
                items -> items.let{
            adapter.submitList(it)
        }
        }
        binding.unit1RecyclerView.layoutManager = LinearLayoutManager(this.context)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}