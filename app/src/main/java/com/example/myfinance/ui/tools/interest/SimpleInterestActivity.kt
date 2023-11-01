package com.example.myfinance.ui.tools.interest

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.example.myfinance.databinding.ActivitySimpleInterestBinding
import kotlin.math.pow

class SimpleInterestActivity : AppCompatActivity() {
    companion object {
        const val TOOL = "tool"
    }
    private lateinit var binding: ActivitySimpleInterestBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySimpleInterestBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.principalAmountSeekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                binding.inputPrincipalAmount.setText(p1.toString())
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }

        })
        binding.inputInterestRate.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                calculateSimpleInterest()
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })
        binding.inputInvestmentTime.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                calculateSimpleInterest()
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })
        binding.inputPrincipalAmount.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                calculateSimpleInterest()
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })
        calculateSimpleInterest()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Simple Interest Calculator"
    }


    fun calculateSimpleInterest() {
        if(binding.inputInterestRate.text.toString() == "" || binding.inputInvestmentTime.text.toString() == ""
            || binding.inputPrincipalAmount.text.toString() == ""){
            return
        }
        val interest: Double = binding.inputInterestRate.text.toString().toDouble()
        val time: Double = binding.inputInvestmentTime.text.toString().toDouble()
        val p1: Double = binding.inputPrincipalAmount.text.toString().toDouble()
        val principal = p1 * (1 + interest/100.0 * time)
        binding.simplePrincipalAndInterest.text = "Principal and Interest: $${"%.2f".format(principal)}"
        binding.simpleInterest.text = "Interest: $${"%.2f".format(principal-p1)}"
        val monthly = (p1 * interest/1200.0)/(1 - (1 + interest/1200.0).pow(12.0 * time*-1))
        binding.simpleMonthlyPayments.text = "Monthly Payments (if applicable): $${"%.2f".format(monthly)}"
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}