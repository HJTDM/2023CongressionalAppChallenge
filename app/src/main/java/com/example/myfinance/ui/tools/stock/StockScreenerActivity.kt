package com.example.myfinance.ui.tools.stock

import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.myfinance.databinding.ActivityStockScreenerBinding

class StockScreenerActivity : AppCompatActivity() {
    companion object {
        const val TOOL = "tool"
    }

    private lateinit var binding: ActivityStockScreenerBinding
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
            val unmetered =
                networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_NOT_METERED)
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
        val connectivityManager =
            getSystemService(ConnectivityManager::class.java) as ConnectivityManager
        connectivityManager.requestNetwork(networkRequest, networkCallback)

        binding = ActivityStockScreenerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.stockScreenerWebview.webViewClient = MyWebViewClient()
        binding.stockScreenerWebview.settings.javaScriptEnabled = true
        if (networkFlag) {
            binding.stockScreenerWebview.loadUrl("https://finance.yahoo.com/")
        } else {
            Toast.makeText(
                this,
                "No Internet Connection.\nPlease Try Again At Another Time.",
                Toast.LENGTH_LONG
            ).show()
        }


        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Stock Screener"
    }

    private class MyWebViewClient : WebViewClient() {

        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
            if (Uri.parse(url).host == "https://finance.yahoo.com/") {
                // This is your website, so don't override. Let your WebView load
                // the page.
                return false
            }
            // Otherwise, the link isn't for a page on your site, so launch another
            // Activity that handles URLs.
            Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
                view?.context?.startActivity(this)
            }
            return true
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}
