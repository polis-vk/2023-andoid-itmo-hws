package ru.ok.itmo.weatherapp

import android.app.Application
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import ru.ok.itmo.example.BuildConfig
import timber.log.Timber

class App : Application()  {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        RxJavaPlugins.setErrorHandler { throwable ->
            Timber.e("Rx ErrorHandler $throwable")
        }
    }
}