package ru.soldatov.android.cryptoapp.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import ru.soldatov.android.cryptoapp.presentation.CoinDetailFragment
import ru.soldatov.android.cryptoapp.presentation.CoinPriceListActivity

@Component(
    modules = [
        DataModule::class,
        ViewModelModule::class
    ]
)
interface ApplicationComponents {

    fun inject(activity: CoinPriceListActivity)

    fun inject(fragment: CoinDetailFragment)

    @Component.Factory
    interface Factory {

        fun create(
            @BindsInstance application: Application
        ): ApplicationComponents

    }

}