package com.example.myfinance.ui.tools.interest

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
import com.example.myfinance.databinding.ActivityInterestCalculatorBinding
import java.text.NumberFormat
import kotlin.math.E
import kotlin.math.pow

class InterestCalculatorActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInterestCalculatorBinding
    private var compounded: Double = 1.0
    private var continuallyFlag: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate binding
        binding = ActivityInterestCalculatorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Update interest rate text when seek bar changes
        binding.interestRateSeekbar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                binding.interestRateSubtitle.text = getString(R.string.interest_rate_percent, progress.toString())
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        // Creates and sets drop down for compound interest time frames
        ArrayAdapter.createFromResource(
            this,
            R.array.compounded_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.compoundedSpinner.adapter = adapter
            binding.compoundedSpinner.setSelection(adapter.getPosition("Annually"))
            // Compound interest option is hidden by default
            binding.compoundFrequencySubtitle.visibility = View.GONE
            binding.compoundedSpinner.visibility = View.GONE
        }

        // Updates variables when selecting a compound interest time frame
        binding.compoundedSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
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
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }

        // Selecting simple interest hides compound interest dropdown
        binding.optionSimple.setOnClickListener{
            binding.compoundFrequencySubtitle.visibility = View.GONE
            binding.compoundedSpinner.visibility = View.GONE
        }

        // Selecting compound interest shows compound interest dropdown
        binding.optionCompound.setOnClickListener {
            binding.compoundFrequencySubtitle.visibility = View.VISIBLE
            binding.compoundedSpinner.visibility = View.VISIBLE
        }

        binding.calculateInterestButton.setOnClickListener{
            calculateCompoundInterest()
        }

        // Allow for navigating back
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun calculateCompoundInterest() {
        // Return if principal amount and time fields are empty
        if(binding.principalAmount.text.toString().isEmpty()
            || binding.investmentTime.text.toString().isEmpty()){
            Toast.makeText(
                this,
                "Please enter a principal amount and investment time",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        var total = 0.0
        val principal = binding.principalAmount.text.toString().toDouble()
        val interestRate = binding.interestRateSeekbar.progress / 100.0
        val time: Double = binding.investmentTime.text.toString().toDouble()

        when(binding.interestOptions.checkedRadioButtonId){
            R.id.option_simple -> {
                total = principal + (principal * interestRate * time)
            }
            R.id.option_compound -> {
                // Using variables prevents repetition of the same equation
                if(continuallyFlag) {
                    total = principal * E.pow(interestRate * time)
                }
                else{
                    total = principal * (1 + interestRate/compounded).pow(compounded * time)
                }
            }
        }

        // Update final interest and total (interest + principal)
        binding.totalInterest.text = getString(
            R.string.interest_with_value,
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