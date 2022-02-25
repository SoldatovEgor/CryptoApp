package ru.soldatov.android.cryptoapp.data.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkerParameters
import kotlinx.coroutines.delay
import ru.soldatov.android.cryptoapp.data.database.AppDatabase
import ru.soldatov.android.cryptoapp.data.mapper.CoinMapper
import ru.soldatov.android.cryptoapp.data.network.ApiFactory
import java.lang.Exception

class RefreshWorkers(
    appContext: Context,
    params: WorkerParameters
) : CoroutineWorker(appContext, params) {

    private val coinInfoDao = AppDatabase.newInstance(appContext).coinPriceInfoDao()
    private val apiService = ApiFactory.apiServer
    private val mapper = CoinMapper()

    override suspend fun doWork(): Result {
        while (true) {
            try {
                val topCoins = apiService.getTopCoinsInfo(limit = 50)
                val fSyms = mapper.mapNamesListToString(topCoins)
                val jsonContainer = apiService.getFullPriceList(fSyms = fSyms)
                val coinInfoDtoList = mapper.mapJsonContainerToListCoinInfo(jsonContainer)
                val coinInfoList = coinInfoDtoList.map { mapper.mapDtoToDBModel(it) }
                coinInfoDao.insertPriceList(coinInfoList)
            } catch (e: Exception) {

            }
            delay(10000)
        }
    }

    companion object {

        const val NAME = "RefreshWorkers"

        fun makeRefresh(): OneTimeWorkRequest {
            return OneTimeWorkRequestBuilder<RefreshWorkers>().build()
        }
    }

}