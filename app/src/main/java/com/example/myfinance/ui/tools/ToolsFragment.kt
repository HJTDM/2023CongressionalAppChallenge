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
import com.example.myfinance.ui.tools.budget.BudgetActivity
import com.example.myfinance.ui.tools.interest.InterestCalculatorActivity
import com.example.myfinance.ui.tools.stock.StockScreenerActivity
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
            if(tool.name == "Interest Calculator"){
                val intent = Intent(activity, InterestCalculatorActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
                startActivity(intent)
            }
            else if(tool.name == "Budget Planner"){
                val intent = Intent(activity, BudgetActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
                startActivity(intent)
            }
            else if(tool.name == "Tax Calculator"){
                val intent = Intent(activity, TaxCalculatorActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
                startActivity(intent)
            }
            else if(tool.name == "Stock Market"){
                val intent = Intent(activity, StockScreenerActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
                startActivity(intent)
            }
            else{
                val intent = Intent(activity, BudgetActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
                startActivity(intent)
            }
        }

        binding.toolsRecyclerView.adapter = adapter
        binding.toolsRecyclerView.layoutManager = GridLayoutManager(this.context, 2)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}