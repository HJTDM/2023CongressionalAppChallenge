package com.example.myfinance.ui.tools.budget

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myfinance.data.BudgetItem
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import java.text.NumberFormat

class BudgetViewModel : ViewModel() {
    private val auth = Firebase.auth
    private val budgetDatabase = auth.currentUser?.let {
        Firebase.database.reference
            .child("users").child(it.uid).child("budget")
    }!!

    val allExpensesItems = MutableLiveData<List<BudgetItem>>(listOf())
    val allIncomeItems = MutableLiveData<List<BudgetItem>>(listOf())

    private val totalBalance = MutableLiveData(0.0)
    val displayedTotalBalance = MutableLiveData(formattedTotalBalance())

    private val totalExpenses = MutableLiveData(0.0)
    val totalExpensesProgress = MutableLiveData(totalExpenses.value?.toInt())
    val displayedAmountLeft = MutableLiveData<String>()

    private val _budgetAmount = MutableLiveData(0.0)
    val budgetAmountProgress = MutableLiveData(_budgetAmount.value?.toInt())

    fun updateExpensesItems(expensesItems: List<BudgetItem>){
        allExpensesItems.value = expensesItems
    }

    fun updateIncomeItems(incomeItems: List<BudgetItem>){
        allIncomeItems.value = incomeItems
    }

    init{
        initializeAllItems()
    }

    private fun initializeAllItems(){
        budgetDatabase.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(dataSnapshot.exists()){
                    // Initialize expenses items list (with items already in database)
                    val expensesItems = mutableListOf<BudgetItem>()
                    for(snapshot in dataSnapshot.child("expenses").children){
                        if(snapshot.getValue(BudgetItem::class.java)?.name?.isNotBlank() == true) {
                            val budgetItem = snapshot.getValue(BudgetItem::class.java)
                            if (budgetItem != null) {
                                Log.d("BudgetViewModel", budgetItem.price.toString())
                            }
                            expensesItems.add(budgetItem!!)
                        }
                    }

                    // Initialize income items list (with items already in database)
                    val incomeItems = mutableListOf<BudgetItem>()
                    for(snapshot in dataSnapshot.child("income").children){
                        if(snapshot.getValue(BudgetItem::class.java)?.name?.isNotBlank() == true) {
                            val budgetItem = snapshot.getValue(BudgetItem::class.java)
                            if (budgetItem != null) {
                                Log.d("BudgetViewModel", budgetItem.price.toString())
                            }
                            incomeItems.add(budgetItem!!)
                        }
                    }

                    updateExpensesItems(expensesItems)
                    updateIncomeItems(incomeItems)
                    // Calculate total balance
                    setTotalBalance()

                    // Set budget amount to 0 or the current value if present in database
                    var budgetAmount = dataSnapshot.child("budgetAmount").getValue(Double::class.java)
                    budgetAmount = budgetAmount ?: 0.0
                    setBudgetAmount(budgetAmount.toString())
                    // Calculates expenses and also displays amount spent in budget
                    setTotalExpenses()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    // Calculate and format the total balance (income - expenses)
    fun setTotalBalance(){
        var total = 0.0
        for(budgetItem in allExpensesItems.value!!){
            total -= budgetItem.price!!
        }
        for(budgetItem in allIncomeItems.value!!){
            total += budgetItem.price!!
        }
        totalBalance.value = total
        displayedTotalBalance.value = formattedTotalBalance()
    }

    private fun formattedTotalBalance(): String{
        return NumberFormat.getCurrencyInstance().format(totalBalance.value)
    }

    // Calculate the total expenses
    fun setTotalExpenses(){
        var expenses = 0.0
        for(budgetItem in allExpensesItems.value!!){
            expenses += budgetItem.price!!
        }
        totalExpenses.value = expenses
        totalExpensesProgress.value = minOf(totalExpenses.value!!.toInt(), _budgetAmount.value!!.toInt())
        displayedAmountLeft.value = NumberFormat.getCurrencyInstance().format(
            minOf(totalExpenses.value!!, _budgetAmount.value!!)
        ) + " spent out of " + NumberFormat.getCurrencyInstance().format(
            _budgetAmount.value
        )
    }

    // Returns a new budget item with name and price
    private fun getNewBudgetItemEntry(itemName: String, itemPrice: String) : BudgetItem {
        return BudgetItem(
            name = itemName,
            price = itemPrice.toDouble()
        )
    }

    // Adds a new budget item to the database
    fun addNewBudgetItem(budgetItemOption: String, budgetItemName: String, budgetItemPrice: String){
        if(budgetItemOption == "expenses"){
            val newItem = getNewBudgetItemEntry(budgetItemName, budgetItemPrice)
            // Uses push() for an auto generated entry
            budgetDatabase.child("expenses").push().setValue(newItem)
        }
        else {
            val newItem = getNewBudgetItemEntry(budgetItemName, budgetItemPrice)
            // Uses push() for an auto generated entry
            budgetDatabase.child("income").push().setValue(newItem)
        }
    }

    // Checks if budget item is valid (not blank)
    fun isEntryValid(budgetItemName: String, budgetItemPrice: String): Boolean{
        return !(budgetItemName.isBlank() || budgetItemPrice.isBlank())
    }

    // Checks if budget amount is valid (not blank)
    fun isBudgetAmountValid(budgetAmount: String): Boolean{
        return budgetAmount.isNotBlank()
    }

    // Set budget amount in database and update progress and amount left
    fun setBudgetAmount(budgetAmount: String){
        budgetDatabase.child("budgetAmount").setValue(budgetAmount.toDouble())
        _budgetAmount.value = budgetAmount.toDouble()
        budgetAmountProgress.value = _budgetAmount.value!!.toInt()
        displayedAmountLeft.value = NumberFormat.getCurrencyInstance().format(
            minOf(totalExpenses.value!!, _budgetAmount.value!!)
        ) + " spent out of " + NumberFormat.getCurrencyInstance().format(
            _budgetAmount.value
        )
    }
}
