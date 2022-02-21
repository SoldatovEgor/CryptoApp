package ru.soldatov.android.cryptoapp.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import ru.soldatov.android.cryptoapp.data.network.ApiFactory.BASE_IMG_URL
import ru.soldatov.android.cryptoapp.utils.converterTimestampToTime

@Entity(tableName = "full_price_list")
data class CoinInfoDBModel(
    @PrimaryKey
    val fromSymbol: String,
    val toSymbol: String?,
    val price: String?,
    val lastUpdate: Long?,
    val highDay: String?,
    val lowDay: String?,
    val lastMarket: String?,
    val imageUrl: String?
)
