package ru.soldatov.android.cryptoapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory

class MainActivity : AppCompatActivity() {

    private val viewModel: CoinViewModel by lazy {
        ViewModelProvider(
            this,
            AndroidViewModelFactory.getInstance(application)
        )[CoinViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel.loadData()
        viewModel.priceList.observe(this){
            Log.d("MainActivity", it.toString())
        }
    }

}