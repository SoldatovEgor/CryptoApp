package ru.soldatov.android.cryptoapp.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import ru.soldatov.android.cryptoapp.presentation.CoinApp
import ru.soldatov.android.cryptoapp.presentation.CoinDetailFragment
import ru.soldatov.android.cryptoapp.presentation.CoinPriceListActivity

@ApplicationScope
@Component(
    modules = [
        DataModule::class,
        ViewModelModule::class
    ]
)
interface ApplicationComponents {

    fun inject(activity: CoinPriceListActivity)

    fun inject(fragment: CoinDetailFragment)

    fun inject(application: CoinApp)

    @Component.Factory
    interface Factory {

        fun create(
            @BindsInstance application: Application
        ): ApplicationComponents

    }

}