package ru.soldatov.android.cryptoapp.di

import android.app.Application
import dagger.Binds
import dagger.Module
import dagger.Provides
import ru.soldatov.android.cryptoapp.data.database.AppDatabase
import ru.soldatov.android.cryptoapp.data.database.CoinPriceInfoDao
import ru.soldatov.android.cryptoapp.data.network.ApiFactory
import ru.soldatov.android.cryptoapp.data.network.ApiService
import ru.soldatov.android.cryptoapp.data.repository.CoinRepositoryImpl
import ru.soldatov.android.cryptoapp.domain.CoinInfoRepository

@Module
interface DataModule {

    @Binds
    @ApplicationScope
    fun bindCoinInfoRepository(impl: CoinRepositoryImpl): CoinInfoRepository

    companion object {

        @Provides
        @ApplicationScope
        fun provideCoinInfoDao(
            application: Application
        ): CoinPriceInfoDao {
            return AppDatabase.newInstance(application).coinPriceInfoDao()
        }

        @Provides
        @ApplicationScope
        fun provideApiService(): ApiService {
            return ApiFactory.apiServer
        }
    }
}