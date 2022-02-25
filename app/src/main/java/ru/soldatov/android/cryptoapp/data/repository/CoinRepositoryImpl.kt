package ru.soldatov.android.cryptoapp.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkManager
import ru.soldatov.android.cryptoapp.data.database.AppDatabase
import ru.soldatov.android.cryptoapp.data.mapper.CoinMapper
import ru.soldatov.android.cryptoapp.data.workers.RefreshWorkers
import ru.soldatov.android.cryptoapp.domain.CoinInfo
import ru.soldatov.android.cryptoapp.domain.CoinInfoRepository

class CoinRepositoryImpl(private val application: Application)
    : CoinInfoRepository {

    private val coinInfoDao = AppDatabase.newInstance(application).coinPriceInfoDao()

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

    override fun loadData() {
        val workManager = WorkManager.getInstance(application)
        workManager.enqueueUniqueWork(
            RefreshWorkers.NAME,
            ExistingWorkPolicy.REPLACE,
            RefreshWorkers.makeRefresh()
        )
    }
}