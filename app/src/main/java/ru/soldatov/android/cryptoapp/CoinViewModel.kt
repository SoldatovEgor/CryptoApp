package ru.soldatov.android.cryptoapp

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.google.gson.Gson
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import ru.soldatov.android.cryptoapp.api.ApiFactory
import ru.soldatov.android.cryptoapp.database.AppDatabase
import ru.soldatov.android.cryptoapp.pojo.CoinPriceInfo
import ru.soldatov.android.cryptoapp.pojo.CoinPriceInfoRawData
import java.util.*
import java.util.concurrent.TimeUnit

class CoinViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.newInstance(application)
    private val compositeDisposable = CompositeDisposable()

    val priceList = db.coinPriceInfoDao().getPriceList()

    init {
        loadData()
    }

    fun getDetailInfo(fSym: String): LiveData<CoinPriceInfo> {
        return db.coinPriceInfoDao().getPrice(fSym)
    }

    private fun loadData() {
        val disposable = ApiFactory.apiServer.getTopCoinsInfo(limit = 50)
            .map { it.data?.map { it.coinInfo?.name }?.joinToString(", ") }
            .flatMap { ApiFactory.apiServer.getFullPriceList(fSyms = it) }
            .map { getPriceListFromRowData(it) }
            .delaySubscription(10, TimeUnit.SECONDS)
            .repeat()
            .retry()
            .subscribeOn(Schedulers.io())
            .subscribe({
                db.coinPriceInfoDao().insertPriceList(it)
                Log.d("TEST_MAIN_ACTIVITY", it.toString())
            }, {
                Log.d("TEST_MAIN_ACTIVITY", it.message!!)
            })
    }

    private fun getPriceListFromRowData(
        coinPriceInfoRawData: CoinPriceInfoRawData
    ): List<CoinPriceInfo> {
        val result = ArrayList<CoinPriceInfo>()
        val jsonObject = coinPriceInfoRawData.coinPriceInfoJsonObject ?: return result
        val coinKeySet = jsonObject.keySet()
        for (coinKey in coinKeySet) {
            val currencyJson = jsonObject.getAsJsonObject(coinKey)
            val currencyKeySet = currencyJson.keySet()
            for (currency in currencyKeySet) {
                val coinInfo = Gson().fromJson(
                    currencyJson.getAsJsonObject(currency),
                    CoinPriceInfo::class.java
                )
                result.add(coinInfo)
            }
        }
        return result
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}