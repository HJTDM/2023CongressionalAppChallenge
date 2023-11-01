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

    private fun initializeallItems(){
        budgetDatabase.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(dataSnapshot.exists()){
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
                    setTotalBalance()

                    var budgetAmount = dataSnapshot.child("budgetAmount").getValue(Double::class.java)
                    budgetAmount = budgetAmount ?: 0.0
                    setBudgetAmount(budgetAmount.toString())
                    setTotalExpenses()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }
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

    private fun getNewBudgetItemEntry(itemName: String, itemPrice: String) : BudgetItem {
        return BudgetItem(
            name = itemName,
            price = itemPrice.toDouble()
        )
    }

    fun addNewBudgetItem(budgetItemOption: String, budgetItemName: String, budgetItemPrice: String){
        if(budgetItemOption == "expenses"){
            val newItem = getNewBudgetItemEntry(budgetItemName, budgetItemPrice)
            budgetDatabase.child("expenses").push().setValue(newItem)
        }
        else {
            val newItem = getNewBudgetItemEntry(budgetItemName, budgetItemPrice)
            budgetDatabase.child("income").push().setValue(newItem)
        }
    }

    fun isEntryValid(budgetItemName: String, budgetItemPrice: String): Boolean{
        if(budgetItemName.isBlank() || budgetItemPrice.isBlank()){
            return false
        }
        return true
    }

    fun isBudgetAmountValid(budgetAmount: String): Boolean{
        if(budgetAmount.isBlank()){
            return false
        }
        return true
    }

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
//
//    fun retrieveItem(id: Int): LiveData<Item> {
//        return itemDao.getItem(id).asLiveData()
//    }
//
//    private fun updateItem(item: Item) {
//        viewModelScope.launch {
//            itemDao.update(item)
//        }
//    }
//
//    fun deleteItem(item: Item){
//        viewModelScope.launch{
//            itemDao.delete(item)
//        }
//    }
//
//    fun updateItem(
//        itemId: Int,
//        itemName: String,
//        itemPrice: String,
//        itemCount: String
//    ) {
//        val updatedItem = getUpdatedItemEntry(itemId, itemName, itemPrice, itemCount)
//        updateItem(updatedItem)
//    }
    init{
        initializeallItems()
    }
}
