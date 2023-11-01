package com.example.myfinance.ui.tools.tax

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.net.NetworkRequest
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.webkit.WebView
import android.widget.SeekBar
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.myfinance.databinding.ActivitySimpleInterestBinding
import com.example.myfinance.databinding.ActivityTaxCalculatorBinding
import kotlin.math.pow

class TaxCalculatorActivity : AppCompatActivity() {
    companion object {
        const val TOOL = "tool"
    }
    private lateinit var binding: ActivityTaxCalculatorBinding
    private var networkFlag: Boolean = false

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        // network is available for use
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            networkFlag = true
        }

        // Network capabilities have changed for the network
        override fun onCapabilitiesChanged(
            network: Network,
            networkCapabilities: NetworkCapabilities
        ) {
            super.onCapabilitiesChanged(network, networkCapabilities)
            val unmetered = networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_NOT_METERED)
        }

        // lost network connection
        override fun onLost(network: Network) {
            super.onLost(network)
            networkFlag = false
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .build()
        val connectivityManager = getSystemService(ConnectivityManager::class.java) as ConnectivityManager
        connectivityManager.requestNetwork(networkRequest, networkCallback)

        binding = ActivityTaxCalculatorBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.taxCalculatorWebview.settings.javaScriptEnabled = true
        if(networkFlag){
            binding.taxCalculatorWebview.loadUrl("https://www.nerdwallet.com/taxes/tax-calculator")
        }else{
            Toast.makeText(this, "No Internet Connection.\nPlease Try Again At Another Time.", Toast.LENGTH_LONG).show()
        }


        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Tax Calculator"
    }


    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}