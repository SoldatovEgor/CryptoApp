package ru.soldatov.android.cryptoapp.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import okhttp3.internal.Util
import ru.soldatov.android.cryptoapp.R
import ru.soldatov.android.cryptoapp.databinding.ItemCoinInfoBinding
import ru.soldatov.android.cryptoapp.pojo.CoinPriceInfo

class CoinInfoAdapter(private val context: Context)
    : RecyclerView.Adapter<CoinInfoAdapter.CoinInfoViewHolder>() {

    var coinItemClickListener: ((CoinPriceInfo) -> Unit)? = null

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

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CoinInfoViewHolder, position: Int) {
        val coin = coinInfoList[position]

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
                coin.getFormattedTime()
            )
            Picasso.get().load(coin.getFullImageUrl()).into(ivLogoCoin)
        }
        binding.root.setOnClickListener {
            coinItemClickListener?.invoke(coin)
        }
    }

    override fun getItemCount() = coinInfoList.size

    inner class CoinInfoViewHolder(val binding: ItemCoinInfoBinding)
        : RecyclerView.ViewHolder(binding.root)

}