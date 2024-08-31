package com.example.myfinance.ui.tools.stock

import android.annotation.SuppressLint
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myfinance.databinding.ActivityStockScreenerBinding
import com.example.myfinance.databinding.ActivityTaxCalculatorBinding

class StockScreenerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStockScreenerBinding
    private var networkFlag: Boolean = false

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        // Network is available for use
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            networkFlag = true
        }

        // Lost network connection
        override fun onLost(network: Network) {
            super.onLost(network)
            networkFlag = false
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Request access to internet
        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .build()
        val connectivityManager = getSystemService(ConnectivityManager::class.java) as ConnectivityManager
        connectivityManager.requestNetwork(networkRequest, networkCallback)

        // Inflate binding
        binding = ActivityStockScreenerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.stockScreenerWebview.settings.javaScriptEnabled = true

        // Load website if there is an internet connection
        if(networkFlag){
            binding.stockScreenerWebview.loadUrl("https://www.marketwatch.com/market-data/us")
        }
        else{
            Toast.makeText(
                this,
                "No Internet Connection.\nPlease Try Again At Another Time.",
                Toast.LENGTH_LONG
            ).show()
        }

        // Allow backwards navigation
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Stock Screener"
    }

    // End activity when navigating backward
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}