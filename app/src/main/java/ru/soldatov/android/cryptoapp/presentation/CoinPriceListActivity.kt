package ru.soldatov.android.cryptoapp.presentation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import ru.soldatov.android.cryptoapp.R
import ru.soldatov.android.cryptoapp.databinding.ActivityCoinPriceListBinding
import ru.soldatov.android.cryptoapp.presentation.adapters.CoinInfoAdapter

class CoinPriceListActivity : AppCompatActivity() {

    private lateinit var adapter: CoinInfoAdapter

    private val binding by lazy {
        ActivityCoinPriceListBinding.inflate(layoutInflater)
    }

    private val viewModel: CoinViewModel by lazy {
        ViewModelProvider(
            this,
            AndroidViewModelFactory.getInstance(application)
        )[CoinViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupRecyclerView()
        viewModel.coinInfoList.observe(this){
            Log.d("CoinPriceListActivity", it.size.toString())
            adapter.submitList(it)
        }
    }

    private fun launchActivity(fromSymbol: String) {
        val intent = CoinDetailActivity.newIntent(this, fromSymbol)
        startActivity(intent)
    }

    private fun launchFragment(fromSymbol: String) {
        supportFragmentManager.popBackStack()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, CoinDetailFragment.newInstance(fromSymbol))
            .addToBackStack(null)
            .commit()
    }

    private fun isOnePaneMode() = binding.fragmentContainer == null

    private fun setupRecyclerView() {
        adapter = CoinInfoAdapter(this)
        binding.rvCoinPriceList.adapter = adapter
        binding.rvCoinPriceList.itemAnimator = null
        setupClickListener()
    }

    private fun setupClickListener() {
        adapter.coinItemClickListener = {
            if (isOnePaneMode()) {
                launchActivity(it.fromSymbol)
            } else {
                launchFragment(it.fromSymbol)
            }
        }
    }

}