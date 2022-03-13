package ru.soldatov.android.cryptoapp.data.workers

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import ru.soldatov.android.cryptoapp.data.database.CoinPriceInfoDao
import ru.soldatov.android.cryptoapp.data.mapper.CoinMapper
import ru.soldatov.android.cryptoapp.data.network.ApiService
import javax.inject.Inject

class RefreshWorkersFactory @Inject constructor(
    private val coinInfoDao: CoinPriceInfoDao,
    private val apiService: ApiService,
    private val mapper: CoinMapper
): WorkerFactory() {

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        return RefreshWorkers(
            appContext,
            workerParameters,
            coinInfoDao,
            apiService,
            mapper
        )
    }
}