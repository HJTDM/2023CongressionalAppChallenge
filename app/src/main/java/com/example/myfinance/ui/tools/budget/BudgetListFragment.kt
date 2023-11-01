package com.example.myfinance.ui.tools.budget

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myfinance.data.BudgetItem
import com.example.myfinance.databinding.FragmentBudgetListBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database


class BudgetListFragment : Fragment() {

    private var _binding: FragmentBudgetListBinding? = null
    private val binding get() = _binding!!

    private val budgetViewModel: BudgetViewModel by activityViewModels()

    private lateinit var auth: FirebaseAuth
    private lateinit var userDatabase: DatabaseReference
    private lateinit var budgetDatabase: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true ) {
                override fun handleOnBackPressed() {
                    activity?.finish()
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBudgetListBinding.inflate(inflater, container, false)
        (activity as BudgetActivity?)?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = budgetViewModel
            budgetListFragment = this@BudgetListFragment
        }

        (activity as AppCompatActivity).supportActionBar?.title = "Budget Planner"
        auth = Firebase.auth
        userDatabase = auth.currentUser?.let { Firebase.database.reference.child("users").child(it.uid) }!!

        userDatabase.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (!snapshot.hasChild("budget/expenses")) {
                    val databaseReference = userDatabase
                        .child("budget").child("expenses").push()
                    databaseReference.setValue(
                        BudgetItem("", 0.0)
                    )
                }
                if (!snapshot.hasChild("budget/income")) {
                    val databaseReference = userDatabase
                        .child("budget").child("income").push()
                    databaseReference.setValue(
                        BudgetItem("", 0.0)
                    )
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })

        budgetDatabase = auth.currentUser?.let {
            Firebase.database.reference
                .child("users").child(it.uid).child("budget")
        }!!

        budgetDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(dataSnapshot.exists()){
                    val allExpensesItems = mutableListOf<BudgetItem>()
                    for(snapshot in dataSnapshot.child("expenses").children){
                        if(snapshot.getValue(BudgetItem::class.java)?.name?.isNotBlank() == true) {
                            val budgetItem = snapshot.getValue(BudgetItem::class.java)
                            allExpensesItems.add(budgetItem!!)
                        }
                    }
                    budgetViewModel.updateExpensesItems(allExpensesItems)

                    val allIncomeItems = mutableListOf<BudgetItem>()
                    for(snapshot in dataSnapshot.child("income").children){
                        if(snapshot.getValue(BudgetItem::class.java)?.name?.isNotBlank() == true) {
                            val budgetItem = snapshot.getValue(BudgetItem::class.java)
                            allIncomeItems.add(budgetItem!!)
                        }
                    }
                    budgetViewModel.updateIncomeItems(allIncomeItems)

                    budgetViewModel.setTotalExpenses()
                    budgetViewModel.setTotalBalance()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })

        val expensesAdapter = BudgetListAdapter{
//            val action = BudgetListFragmentDirections.actionBudgetListFragmentToAddBudgetItemFragment(it.name)
//            this.findNavController().navigate(action)
        }
        binding.expensesRecyclerView.adapter = expensesAdapter
        budgetViewModel.allExpensesItems.observe(this.viewLifecycleOwner){ items ->
            items.let{
                expensesAdapter.submitList(it)
                budgetViewModel.setTotalBalance()
            }
        }
        binding.expensesRecyclerView.layoutManager = LinearLayoutManager(this.context)

        val incomeAdapter = BudgetListAdapter{
//            val action = BudgetListFragmentDirections.actionBudgetListFragmentToAddBudgetItemFragment(it.name)
//            this.findNavController().navigate(action)
        }
        binding.incomeRecyclerView.adapter = incomeAdapter
        budgetViewModel.allIncomeItems.observe(this.viewLifecycleOwner){ items ->
            items.let{
                incomeAdapter.submitList(it)
                budgetViewModel.setTotalBalance()
            }
        }
        binding.incomeRecyclerView.layoutManager = LinearLayoutManager(this.context)

        binding.totalBudgetCard.setOnClickListener{
            val action = BudgetListFragmentDirections.actionBudgetListFragmentToSetBudgetFragment()
            this.findNavController().navigate(action)
        }

        binding.floatingActionButton.setOnClickListener {
            val action = BudgetListFragmentDirections.actionBudgetListFragmentToAddBudgetItemFragment()
            this.findNavController().navigate(action)
        }
    }
}
