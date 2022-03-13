package ru.soldatov.android.cryptoapp.presentation

import android.app.Application
import androidx.work.Configuration
import ru.soldatov.android.cryptoapp.data.workers.CoinWorkerFactory
import ru.soldatov.android.cryptoapp.di.DaggerApplicationComponents
import javax.inject.Inject

class CoinApp : Application(), Configuration.Provider {

    @Inject
    lateinit var refreshWorkersFactory: CoinWorkerFactory

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