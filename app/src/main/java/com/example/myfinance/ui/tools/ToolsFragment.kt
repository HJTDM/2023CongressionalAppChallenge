package com.example.myfinance.ui.tools

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myfinance.databinding.FragmentToolsBinding
import com.example.myfinance.ui.tools.interest.CompoundInterestActivity
import com.example.myfinance.ui.tools.interest.SimpleInterestActivity
import com.example.myfinance.ui.tools.tax.TaxCalculatorActivity

class ToolsFragment : Fragment() {

    private var _binding: FragmentToolsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val toolsViewModel: ToolsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentToolsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = toolsViewModel
            toolsFragment = this@ToolsFragment
        }

        val adapter = ToolsListAdapter{tool ->
            val intent = Intent(activity, TaxCalculatorActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            intent.putExtra(TaxCalculatorActivity.TOOL, tool.name)
            startActivity(intent)
        }
        binding.toolsRecyclerView.adapter = adapter
        binding.toolsRecyclerView.layoutManager = GridLayoutManager(this.context, 2)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}