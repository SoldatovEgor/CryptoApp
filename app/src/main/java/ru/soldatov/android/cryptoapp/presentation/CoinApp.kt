package ru.soldatov.android.cryptoapp.presentation

import android.app.Application
import ru.soldatov.android.cryptoapp.di.DaggerApplicationComponents

class CoinApp : Application() {

    val component by lazy {
        DaggerApplicationComponents.factory().create(this)
    }
}