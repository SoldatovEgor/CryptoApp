package ru.soldatov.android.cryptoapp.presentation

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.google.gson.Gson
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import ru.soldatov.android.cryptoapp.data.network.ApiFactory
import ru.soldatov.android.cryptoapp.data.database.AppDatabase
import ru.soldatov.android.cryptoapp.data.database.CoinInfoDBModel
import ru.soldatov.android.cryptoapp.data.network.models.CoinInfoJsonObjectDto
import java.util.*
import java.util.concurrent.TimeUnit

class CoinViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.newInstance(application)
    private val compositeDisposable = CompositeDisposable()

    val priceList = db.coinPriceInfoDao().getPriceList()

    init {
        loadData()
    }

    fun getDetailInfo(fSym: String): LiveData<CoinInfoDBModel> {
        return db.coinPriceInfoDao().getPrice(fSym)
    }

    private fun loadData() {
        val disposable = ApiFactory.apiServer.getTopCoinsInfo()
            .map { it.names?.map { it.coinName?.name }?.joinToString(",") }
            .flatMap { ApiFactory.apiServer.getFullPriceList(fSyms = it) }
            .map { getPriceListFromRowData(it) }
            .delaySubscription(10, TimeUnit.SECONDS)
            .repeat()
            .retry()
            .subscribeOn(Schedulers.io())
            .subscribe({
                db.coinPriceInfoDao().insertPriceList(it)
                Log.d("TEST_MAIN_ACTIVITY", it.size.toString())
            }, {
                Log.d("TEST_MAIN_ACTIVITY", it.message!!)
            })
        compositeDisposable.add(disposable)
    }

    private fun getPriceListFromRowData(
        coinInfoJsonObjectDto: CoinInfoJsonObjectDto
    ): List<CoinInfoDBModel> {
        val result = ArrayList<CoinInfoDBModel>()
        val jsonObject = coinInfoJsonObjectDto.json ?: return result
        val coinKeySet = jsonObject.keySet()
        for (coinKey in coinKeySet) {
            val currencyJson = jsonObject.getAsJsonObject(coinKey)
            val currencyKeySet = currencyJson.keySet()
            for (currencyKey in currencyKeySet) {
                val priceInfo = Gson().fromJson(
                    currencyJson.getAsJsonObject(currencyKey),
                    CoinInfoDBModel::class.java
                )
                result.add(priceInfo)
            }
        }
        return result
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}