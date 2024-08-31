package com.example.myfinance.ui.tools.tax

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myfinance.R
import com.example.myfinance.data.DataSource
import com.example.myfinance.databinding.ActivityInterestCalculatorBinding
import com.example.myfinance.databinding.ActivityTaxCalculatorBinding
import java.text.NumberFormat
import kotlin.math.E
import kotlin.math.pow

class TaxCalculatorActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTaxCalculatorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate binding
        binding = ActivityTaxCalculatorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Creates and sets drop down for US states
        ArrayAdapter.createFromResource(
            this,
            R.array.states_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.stateSpinner.adapter = adapter
            binding.stateSpinner.setSelection(adapter.getPosition("Alabama"))
            binding.salesTaxRate.setText(R.string.default_tax_rate)
        }

        // Updates tax input field when selecting a state
        binding.stateSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val state = binding.stateSpinner.getItemAtPosition(p2).toString()
                binding.salesTaxRate.setText(DataSource.stateSalesTaxes.getOrDefault(state, 4.00).toString())
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }

        binding.calculateSalesTaxButton.setOnClickListener{
            calculateSalesTax()
        }

        // Allow for navigating back
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Tax Calculator"
    }

    private fun calculateSalesTax() {
        // Return if principal amount and tax rate fields are empty
        if(binding.principalAmount.text.toString().isEmpty()
            || binding.salesTaxRate.text.toString().isEmpty()){
            Toast.makeText(
                this,
                "Please enter a principal amount and sales tax rate",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        var total = 0.0
        val principal = binding.principalAmount.text.toString().toDouble()
        val taxRate: Double = binding.salesTaxRate.text.toString().toDouble()

        total = principal + (taxRate / 100.0) * principal

        // Update final tax and total (interest + principal)
        binding.totalTax.text = getString(
            R.string.tax_with_value,
            NumberFormat.getCurrencyInstance().format(total - principal)
        )
        binding.totalAmount.text = getString(
            R.string.total_with_value,
            NumberFormat.getCurrencyInstance().format(total)
        )
    }

    // Finish activity when back button is clicked
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}