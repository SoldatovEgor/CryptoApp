package ru.soldatov.android.cryptoapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ru.soldatov.android.cryptoapp.databinding.ItemCoinInfoBinding
import ru.soldatov.android.cryptoapp.pojo.CoinPriceInfo

class CoinInfoAdapter : RecyclerView.Adapter<CoinInfoAdapter.CoinInfoViewHolder>() {

    var coinInfoList = listOf<CoinPriceInfo>()
        set (value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinInfoViewHolder {
        val binding = ItemCoinInfoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CoinInfoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CoinInfoViewHolder, position: Int) {
        val coin = coinInfoList[position]
        val binding = holder.binding
        with(binding) {
            tvSymbols.text = "${coin.fromSymbol} / ${coin.toSymbol}"
            tvPrice.text = coin.price.toString()
            tvLastUpdate.text = coin.getFormattedTime()
            Picasso.get().load(coin.getFullImageUrl()).into(ivLogoCoin)
        }
    }

    override fun getItemCount() = coinInfoList.size

    inner class CoinInfoViewHolder(val binding: ItemCoinInfoBinding)
        : RecyclerView.ViewHolder(binding.root)

}