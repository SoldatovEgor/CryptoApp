package ru.soldatov.android.cryptoapp.presentation

import android.app.Application
import androidx.work.Configuration
import ru.soldatov.android.cryptoapp.data.database.AppDatabase
import ru.soldatov.android.cryptoapp.data.mapper.CoinMapper
import ru.soldatov.android.cryptoapp.data.network.ApiFactory
import ru.soldatov.android.cryptoapp.data.workers.RefreshWorkersFactory
import ru.soldatov.android.cryptoapp.di.DaggerApplicationComponents
import javax.inject.Inject

class CoinApp : Application(), Configuration.Provider {

    @Inject
    lateinit var refreshWorkersFactory: RefreshWorkersFactory

    val component by lazy {
        DaggerApplicationComponents.factory().create(this)
    }

    override fun onCreate() {
        component.inject(this)
        super.onCreate()
    }

    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder()
            .setWorkerFactory(refreshWorkersFactory)
            .build()
    }
}