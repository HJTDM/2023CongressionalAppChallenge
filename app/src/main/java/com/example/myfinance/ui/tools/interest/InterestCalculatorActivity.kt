package com.example.myfinance.ui.tools.interest

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
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

        binding = ActivityInterestCalculatorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.interestRateSeekbar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                binding.interestRateSubtitle.text = getString(R.string.interest_rate_percent, progress.toString())
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        ArrayAdapter.createFromResource(
            this,
            R.array.compounded_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.compoundedSpinner.adapter = adapter
            binding.compoundedSpinner.setSelection(adapter.getPosition("Annually"))
            binding.compoundFrequencySubtitle.visibility = View.GONE
            binding.compoundedSpinner.visibility = View.GONE
        }

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

        binding.optionSimple.setOnClickListener{
            binding.compoundFrequencySubtitle.visibility = View.GONE
            binding.compoundedSpinner.visibility = View.GONE
        }

        binding.optionCompound.setOnClickListener {
            binding.compoundFrequencySubtitle.visibility = View.VISIBLE
            binding.compoundedSpinner.visibility = View.VISIBLE
        }

        binding.calculateInterestButton.setOnClickListener{
            calculateCompoundInterest()
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }


    private fun calculateCompoundInterest() {
        if(binding.principalAmount.text.toString().isEmpty()
            || binding.investmentTime.text.toString().isEmpty()){
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
                if(continuallyFlag) {
                    total = principal * E.pow(interestRate * time)
                }
                else{
                    total = principal * (1 + interestRate/compounded).pow(compounded * time)
                }
            }
        }

        binding.totalInterest.text = getString(
            R.string.interest_with_value,
            NumberFormat.getCurrencyInstance().format(total - principal)
        )
        binding.totalAmount.text = getString(
            R.string.total_with_value,
            NumberFormat.getCurrencyInstance().format(total)
        )
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}