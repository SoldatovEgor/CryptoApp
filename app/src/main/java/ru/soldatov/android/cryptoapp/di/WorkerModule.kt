package ru.soldatov.android.cryptoapp.di

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.soldatov.android.cryptoapp.data.workers.ChildWorkerFactory
import ru.soldatov.android.cryptoapp.data.workers.RefreshWorkers

@Module
interface WorkerModule {

    @Binds
    @IntoMap
    @WorkerModuleKey(RefreshWorkers::class)
    fun bindWorkerModule(refreshWorkers: RefreshWorkers.Factory): ChildWorkerFactory
}