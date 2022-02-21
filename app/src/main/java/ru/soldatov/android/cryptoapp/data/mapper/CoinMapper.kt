package ru.soldatov.android.cryptoapp.data.mapper

import com.google.gson.Gson
import ru.soldatov.android.cryptoapp.data.database.CoinInfoDBModel
import ru.soldatov.android.cryptoapp.data.network.models.CoinInfoDto
import ru.soldatov.android.cryptoapp.data.network.models.CoinInfoJsonObjectDto
import ru.soldatov.android.cryptoapp.data.network.models.CoinListNamesDto
import ru.soldatov.android.cryptoapp.domain.CoinInfo

class CoinMapper {

    fun mapDtoToDBModel(dto: CoinInfoDto): CoinInfoDBModel {
        return CoinInfoDBModel(
            fromSymbol = dto.fromSymbol,
            toSymbol = dto.toSymbol,
            price = dto.price,
            lastUpdate = dto.lastUpdate,
            highDay = dto.highDay,
            lowDay = dto.lowDay,
            lastMarket = dto.lastMarket,
            imageUrl = dto.imageUrl
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
            lastUpdate = dbModel.lastUpdate,
            highDay = dbModel.highDay,
            lowDay = dbModel.lowDay,
            lastMarket = dbModel.lastMarket,
            imageUrl = dbModel.imageUrl
        )
    }
}