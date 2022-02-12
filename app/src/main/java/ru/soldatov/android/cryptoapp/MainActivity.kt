package ru.soldatov.android.cryptoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import ru.soldatov.android.cryptoapp.api.ApiFactory

class MainActivity : AppCompatActivity() {

    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        compositeDisposable.add(disposable())
    }

    private fun disposable(): Disposable {
        return ApiFactory.apiServer.getFullPriceList(fSyms = "BTC")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d("TEST_MAIN_ACTIVITY", it.toString())
            }, {
                Log.d("TEST_MAIN_ACTIVITY", it.message!!)
            })
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}