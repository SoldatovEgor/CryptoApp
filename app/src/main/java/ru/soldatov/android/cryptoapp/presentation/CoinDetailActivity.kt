package ru.soldatov.android.cryptoapp.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import ru.soldatov.android.cryptoapp.databinding.ActivityCoinDetailBinding
import ru.soldatov.android.cryptoapp.data.database.CoinInfoDBModel

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

    private fun initViews(coinPriceInfo: CoinInfoDBModel) {
        with(binding) {
            Picasso.get().load(coinPriceInfo.getFullImageUrl()).into(ivLogoCoin)
            tvFromSymbol.text = coinPriceInfo.fromSymbol
            tvToSymbol.text = coinPriceInfo.toSymbol
            tvPrice.text = coinPriceInfo.price
            tvMinPrice.text = coinPriceInfo.lowDay
            tvMaxPrice.text = coinPriceInfo.highDay
            tvLastMarket.text = coinPriceInfo.lastMarket
            tvLastUpdate.text = coinPriceInfo.getFormattedTime()
            tvFromSymbol.text = coinPriceInfo.fromSymbol
            tvToSymbol.text = coinPriceInfo.toSymbol
        }
    }

    companion object {

        private const val EXTRA_FROM_SYMBOL = "fSym"

        fun newIntent(context: Context, coinPriceInfo: CoinInfoDBModel): Intent {
            return Intent(context, CoinDetailActivity::class.java).apply {
                putExtra(EXTRA_FROM_SYMBOL, coinPriceInfo.fromSymbol)
            }
        }
    }

}