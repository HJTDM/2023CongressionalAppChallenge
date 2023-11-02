package com.example.myfinance.ui.tools.interest

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SeekBar
import android.widget.Spinner
import android.widget.SpinnerAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.myfinance.R
import com.example.myfinance.databinding.ActivityCompoundInterestBinding
import kotlin.math.E
import kotlin.math.pow

class CompoundInterestActivity : AppCompatActivity() {
    companion object {
        const val TOOL = "tool"
    }
    private lateinit var binding: ActivityCompoundInterestBinding
    private var compounded: Double = 1.0
    private var continuallyFlag: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCompoundInterestBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.cPrincipalAmountSeekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                binding.cInputPrincipalAmount.setText(p1.toString())
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }

        })
        binding.cInputInterestRate.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                calculateCompoundInterest()
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })
        binding.cInputInvestmentTime.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                calculateCompoundInterest()
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })
        binding.cInputPrincipalAmount.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                calculateCompoundInterest()
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })

        ArrayAdapter.createFromResource(
            this,
            R.array.compounded_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears.
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner.
            binding.compoundedSpinner.adapter = adapter
        }
        binding.compoundedSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                when (binding.compoundedSpinner.getItemAtPosition(p2).toString()) {
                    "Continually" -> {
                        continuallyFlag = true
                        compounded = 0.0
                    }

                    "Daily" -> {
                        continuallyFlag = false
                        compounded = 365.0
                    }

                    "Weekly" -> {
                        continuallyFlag = false
                        compounded = 52.0
                    }

                    "Monthly" -> {
                        continuallyFlag = false
                        compounded = 12.0
                    }

                    else -> {
                        continuallyFlag = false
                        compounded = 1.0
                    }
                }
                calculateCompoundInterest()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

        }
        calculateCompoundInterest()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Compound Interest Calculator"
    }


    fun calculateCompoundInterest() {
        if(binding.cInputInterestRate.text.toString() == "" || binding.cInputInvestmentTime.text.toString() == ""
            || binding.cInputPrincipalAmount.text.toString() == ""){
            return
        }

        val interest: Double = binding.cInputInterestRate.text.toString().toDouble()
        val time: Double = binding.cInputInvestmentTime.text.toString().toDouble()
        val p1: Double = binding.cInputPrincipalAmount.text.toString().toDouble()
        var principal = p1 * (1 + interest/100.0/compounded).pow(compounded * time)
        if(continuallyFlag)
        {
            principal = p1 * E.pow(interest/100.0 * time)
        }
        binding.compoundPrincipalAndInterest.text = "Principal and Interest:\n $${"%.2f".format(principal)}"
        binding.compoundInterest.text = "Interest:\n $${"%.2f".format(principal-p1)}"
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}