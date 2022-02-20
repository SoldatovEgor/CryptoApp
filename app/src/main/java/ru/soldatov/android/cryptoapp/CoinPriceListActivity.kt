package ru.soldatov.android.cryptoapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import ru.soldatov.android.cryptoapp.adapters.CoinInfoAdapter
import ru.soldatov.android.cryptoapp.databinding.ActivityMainBinding

class CoinPriceListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: CoinInfoAdapter

    private val viewModel: CoinViewModel by lazy {
        ViewModelProvider(
            this,
            AndroidViewModelFactory.getInstance(application)
        )[CoinViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecyclerView()
        viewModel.priceList.observe(this){
            adapter.coinInfoList = it
        }
    }

    private fun setupRecyclerView() {
        adapter = CoinInfoAdapter()
        binding.rvCoinPriceList.adapter = adapter
    }

}