package ru.soldatov.android.cryptoapp.presentation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import ru.soldatov.android.cryptoapp.R
import ru.soldatov.android.cryptoapp.databinding.ActivityCoinPriceListBinding
import ru.soldatov.android.cryptoapp.presentation.adapters.CoinInfoAdapter
import javax.inject.Inject

class CoinPriceListActivity : AppCompatActivity() {

    private lateinit var adapter: CoinInfoAdapter

    @Inject
    lateinit var viewModelProvider: ViewModelFactory

    private val binding by lazy {
        ActivityCoinPriceListBinding.inflate(layoutInflater)
    }

    private val component by lazy {
        (application as CoinApp).component
    }

    private lateinit var viewModel: CoinViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this, viewModelProvider)[CoinViewModel::class.java]
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