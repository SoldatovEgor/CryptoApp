package ru.soldatov.android.cryptoapp.domain

import androidx.lifecycle.LiveData

interface CoinInfoRepository {

    fun getCoinInfoList(): LiveData<List<CoinInfo>>

    fun getCoinInfo(fromSymbol: String): LiveData<CoinInfo>

    fun loadData()

}