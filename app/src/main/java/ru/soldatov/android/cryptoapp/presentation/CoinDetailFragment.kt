package ru.soldatov.android.cryptoapp.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import ru.soldatov.android.cryptoapp.R

class CoinDetailFragment : Fragment() {

    private lateinit var ivLogoCoin: ImageView
    private lateinit var tvFromSymbol: TextView
    private lateinit var tvToSymbol: TextView
    private lateinit var tvPrice: TextView
    private lateinit var tvMinPrice: TextView
    private lateinit var tvMaxPrice: TextView
    private lateinit var tvLastMarket: TextView
    private lateinit var tvLastUpdate: TextView

    private val viewModel by lazy {
        ViewModelProvider(this)[CoinViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_coin_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
        val fromSymbol = getSymbol()
        viewModel.getDetailInfo(fromSymbol).observe(viewLifecycleOwner) {
            Picasso.get().load(it.imageUrl).into(ivLogoCoin)
            tvFromSymbol.text = it.fromSymbol
            tvToSymbol.text = it.toSymbol
            tvPrice.text = it.price
            tvMinPrice.text = it.lowDay
            tvMaxPrice.text = it.highDay
            tvLastMarket.text = it.lastMarket
            tvLastUpdate.text = it.lastUpdate
            tvFromSymbol.text = it.fromSymbol
            tvToSymbol.text = it.toSymbol
        }
    }

    private fun getSymbol(): String {
        return requireArguments().getString(EXTRA_FROM_SYMBOL, "")
    }

    private fun initViews(view: View) {
        ivLogoCoin = view.findViewById(R.id.ivLogoCoin)
        tvFromSymbol = view.findViewById(R.id.tvFromSymbol)
        tvToSymbol = view.findViewById(R.id.tvToSymbol)
        tvPrice = view.findViewById(R.id.tvPrice)
        tvMinPrice = view.findViewById(R.id.tvMinPrice)
        tvMaxPrice = view.findViewById(R.id.tvMaxPrice)
        tvLastMarket = view.findViewById(R.id.tvLastMarket)
        tvLastUpdate = view.findViewById(R.id.tvLastUpdate)
    }

    companion object {

        private const val EXTRA_FROM_SYMBOL = "fSym"

        fun newInstance(fromSymbols: String): Fragment {
            return CoinDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_FROM_SYMBOL, fromSymbols)
                }
            }
        }
    }

}