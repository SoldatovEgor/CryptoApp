package ru.soldatov.android.cryptoapp.data.network.models

import com.google.gson.JsonObject
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CoinInfoJsonObjectDto(
    @SerializedName("RAW")
    @Expose
    val json: JsonObject? = null
)