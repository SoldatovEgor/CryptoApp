package ru.soldatov.android.cryptoapp.presentation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import ru.soldatov.android.cryptoapp.presentation.adapters.CoinInfoAdapter
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
            Log.d("MainAct", it.size.toString())
            adapter.coinInfoList = it
        }
    }

    private fun setupRecyclerView() {
        adapter = CoinInfoAdapter(this)
        binding.rvCoinPriceList.adapter = adapter
        setupClickListener()
    }

    private fun setupClickListener() {
        adapter.coinItemClickListener = {
            val intent = CoinDetailActivity.newIntent(this, it)
            startActivity(intent)
        }
    }

}