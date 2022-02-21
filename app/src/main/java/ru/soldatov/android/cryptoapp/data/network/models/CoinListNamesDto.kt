package ru.soldatov.android.cryptoapp.data.network.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CoinListNamesDto(
    @SerializedName("Data")
    @Expose
    val names: List<CoinNamesContainerDto>?  = null
)
