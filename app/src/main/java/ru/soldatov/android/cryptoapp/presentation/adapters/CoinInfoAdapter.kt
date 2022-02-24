package ru.soldatov.android.cryptoapp.presentation.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ru.soldatov.android.cryptoapp.R
import ru.soldatov.android.cryptoapp.databinding.ItemCoinInfoBinding
import ru.soldatov.android.cryptoapp.domain.CoinInfo

class CoinInfoAdapter(private val context: Context)
    : ListAdapter<CoinInfo, CoinInfoViewHolder>(CoinInfoDiffUtilCallback()) {

    var coinItemClickListener: ((CoinInfo) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinInfoViewHolder {
        val binding = ItemCoinInfoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CoinInfoViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CoinInfoViewHolder, position: Int) {
        val coin = getItem(position)

        val binding = holder.binding
        with(binding) {
            tvSymbols.text = String.format(
                context.resources.getString(R.string.symbols_template),
                coin.fromSymbol,
                coin.toSymbol
            )
            tvPrice.text = coin.price.toString()
            tvLastUpdate.text = String.format(
                context.resources.getString(R.string.last_update_template),
                coin.lastUpdate
            )
            Picasso.get().load(coin.imageUrl).into(ivLogoCoin)
        }
        binding.root.setOnClickListener {
            coinItemClickListener?.invoke(coin)
        }
    }
}
