package ru.soldatov.android.cryptoapp.di

import androidx.work.ListenableWorker
import dagger.MapKey
import kotlin.reflect.KClass

@MapKey
@Retention(AnnotationRetention.RUNTIME)
annotation class WorkerModuleKey(val value: KClass<out ListenableWorker>)
