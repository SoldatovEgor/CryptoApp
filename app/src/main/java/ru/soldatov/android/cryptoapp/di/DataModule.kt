package ru.soldatov.android.cryptoapp.di

import android.app.Application
import dagger.Binds
import dagger.Module
import dagger.Provides
import ru.soldatov.android.cryptoapp.data.database.AppDatabase
import ru.soldatov.android.cryptoapp.data.database.CoinPriceInfoDao
import ru.soldatov.android.cryptoapp.data.repository.CoinRepositoryImpl
import ru.soldatov.android.cryptoapp.domain.CoinInfoRepository

@Module
interface DataModule {

    @Binds
    fun bindCoinInfoRepository(impl: CoinRepositoryImpl): CoinInfoRepository

    companion object {

        @Provides
        fun provideCoinInfoDao(
            application: Application
        ): CoinPriceInfoDao {
            return AppDatabase.newInstance(application).coinPriceInfoDao()
        }
    }
}