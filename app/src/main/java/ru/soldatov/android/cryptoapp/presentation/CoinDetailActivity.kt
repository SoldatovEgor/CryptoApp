package ru.soldatov.android.cryptoapp.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import ru.soldatov.android.cryptoapp.databinding.ActivityCoinDetailBinding
import ru.soldatov.android.cryptoapp.domain.CoinInfo

class CoinDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCoinDetailBinding

    private val viewModel: CoinViewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[CoinViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoinDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (!intent.hasExtra(EXTRA_FROM_SYMBOL)) {
            finish()
            return
        }
        val fromSymbol = intent.getStringExtra(EXTRA_FROM_SYMBOL) ?: ""
        viewModel.getDetailInfo(fromSymbol).observe(this) {
            initViews(it)
        }
    }

    private fun initViews(coinInfo: CoinInfo) {
        with(binding) {
            Picasso.get().load(coinInfo.imageUrl).into(ivLogoCoin)
            tvFromSymbol.text = coinInfo.fromSymbol
            tvToSymbol.text = coinInfo.toSymbol
            tvPrice.text = coinInfo.price
            tvMinPrice.text = coinInfo.lowDay
            tvMaxPrice.text = coinInfo.highDay
            tvLastMarket.text = coinInfo.lastMarket
            tvLastUpdate.text = coinInfo.lastUpdate
            tvFromSymbol.text = coinInfo.fromSymbol
            tvToSymbol.text = coinInfo.toSymbol
        }
    }

    companion object {

        private const val EXTRA_FROM_SYMBOL = "fSym"

        fun newIntent(context: Context, coinInfo: CoinInfo): Intent {
            return Intent(context, CoinDetailActivity::class.java).apply {
                putExtra(EXTRA_FROM_SYMBOL, coinInfo.fromSymbol)
            }
        }
    }

}