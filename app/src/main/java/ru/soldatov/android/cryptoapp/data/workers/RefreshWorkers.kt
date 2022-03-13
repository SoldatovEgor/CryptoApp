package ru.soldatov.android.cryptoapp.data.workers

import android.content.Context
import androidx.work.*
import kotlinx.coroutines.delay
import ru.soldatov.android.cryptoapp.data.database.CoinPriceInfoDao
import ru.soldatov.android.cryptoapp.data.mapper.CoinMapper
import ru.soldatov.android.cryptoapp.data.network.ApiService
import java.lang.Exception
import javax.inject.Inject

class RefreshWorkers (
    appContext: Context,
    params: WorkerParameters,
    private val coinInfoDao: CoinPriceInfoDao,
    private val apiService: ApiService,
    private val mapper: CoinMapper
) : CoroutineWorker(appContext, params) {

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

    class Factory @Inject constructor(
        private val coinInfoDao: CoinPriceInfoDao,
        private val apiService: ApiService,
        private val mapper: CoinMapper
    ): ChildWorkerFactory {

        override fun create(appContext: Context, params: WorkerParameters): ListenableWorker {
            return RefreshWorkers(appContext, params, coinInfoDao, apiService, mapper)
        }
    }

}