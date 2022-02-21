package ru.soldatov.android.cryptoapp.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import kotlinx.coroutines.delay
import ru.soldatov.android.cryptoapp.data.database.AppDatabase
import ru.soldatov.android.cryptoapp.data.mapper.CoinMapper
import ru.soldatov.android.cryptoapp.data.network.ApiFactory
import ru.soldatov.android.cryptoapp.domain.CoinInfo
import ru.soldatov.android.cryptoapp.domain.CoinInfoRepository

class CoinRepositoryImpl(application: Application)
    : CoinInfoRepository {

    private val coinInfoDao = AppDatabase.newInstance(application).coinPriceInfoDao()
    private val apiService = ApiFactory.apiServer

    private val mapper = CoinMapper()

    override fun getCoinInfoList(): LiveData<List<CoinInfo>> {
        return Transformations.map(coinInfoDao.getPriceList()) { it ->
            it.map {
                mapper.mapDBModelToEntity(it)
            }
        }
    }

    override fun getCoinInfo(fromSymbol: String): LiveData<CoinInfo> {
        return Transformations.map(coinInfoDao.getPrice(fromSymbol)) {
            mapper.mapDBModelToEntity(it)
        }
    }

    override suspend fun loadData() {
        while (true) {
            val topCoins = apiService.getTopCoinsInfo(limit = 50)
            val fSyms = mapper.mapNamesListToString(topCoins)
            val jsonContainer = apiService.getFullPriceList(fSyms = fSyms)
            val coinInfoDtoList = mapper.mapJsonContainerToListCoinInfo(jsonContainer)
            val coinInfoList = coinInfoDtoList.map { mapper.mapDtoToDBModel(it) }
            coinInfoDao.insertPriceList(coinInfoList)
            delay(10000)
        }
    }
}