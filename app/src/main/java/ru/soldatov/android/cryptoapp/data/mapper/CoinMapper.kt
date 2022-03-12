package ru.soldatov.android.cryptoapp.data.mapper

import com.google.gson.Gson
import ru.soldatov.android.cryptoapp.data.database.CoinInfoDBModel
import ru.soldatov.android.cryptoapp.data.network.models.CoinInfoDto
import ru.soldatov.android.cryptoapp.data.network.models.CoinInfoJsonObjectDto
import ru.soldatov.android.cryptoapp.data.network.models.CoinListNamesDto
import ru.soldatov.android.cryptoapp.domain.CoinInfo
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class CoinMapper @Inject constructor() {

    fun mapDtoToDBModel(dto: CoinInfoDto): CoinInfoDBModel {
        return CoinInfoDBModel(
            fromSymbol = dto.fromSymbol,
            toSymbol = dto.toSymbol,
            price = dto.price,
            lastUpdate = dto.lastUpdate,
            highDay = dto.highDay,
            lowDay = dto.lowDay,
            lastMarket = dto.lastMarket,
            imageUrl = BASE_IMG_URL + dto.imageUrl
        )
    }

    fun mapJsonContainerToListCoinInfo(container: CoinInfoJsonObjectDto): List<CoinInfoDto> {
        val result = mutableListOf<CoinInfoDto>()
        val jsonObject = container.json ?: return result
        val coinKeySet = jsonObject.keySet()
        for (coinKey in coinKeySet) {
            val currencyJson = jsonObject.getAsJsonObject(coinKey)
            val currencyKeySet = currencyJson.keySet()
            for (currencyKey in currencyKeySet) {
                val priceInfo = Gson().fromJson(
                    currencyJson.getAsJsonObject(currencyKey),
                    CoinInfoDto::class.java
                )
                result.add(priceInfo)
            }
        }
        return result
    }

    fun mapNamesListToString(namesList: CoinListNamesDto): String {
        return namesList.names?.map {
            it.coinName?.name
        }?.joinToString(",") ?: ""
    }

    fun mapDBModelToEntity(dbModel: CoinInfoDBModel): CoinInfo {
        return CoinInfo(
            fromSymbol = dbModel.fromSymbol,
            toSymbol = dbModel.toSymbol,
            price = dbModel.price,
            lastUpdate = converterTimestampToTime(dbModel.lastUpdate),
            highDay = dbModel.highDay,
            lowDay = dbModel.lowDay,
            lastMarket = dbModel.lastMarket,
            imageUrl = dbModel.imageUrl
        )
    }

    private fun converterTimestampToTime(timestamp: Long?): String {
        if (timestamp == null) return ""
        val stamp = Timestamp(timestamp * 1000)
        val date = Date(stamp.time)
        val pattern = "HH:mm:ss"
        val sdf = SimpleDateFormat(pattern, Locale.getDefault())
        sdf.timeZone = TimeZone.getDefault()
        return sdf.format(date)
    }

    companion object {

        private const val BASE_IMG_URL = "https://cryptocompare.com"
    }
}