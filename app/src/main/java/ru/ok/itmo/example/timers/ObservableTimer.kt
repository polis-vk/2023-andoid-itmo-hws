package ru.ok.itmo.example.timers

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import java.util.concurrent.TimeUnit

class ObservableTimer(override var sleepTime: Long) : TimerProtocol {
    private var current = 0

    @Volatile
    private var isReset = true
        set(value) = synchronized(this) { field = value }

    private var disposable: Disposable? = null

    override fun run(completion: (Int) -> Unit) {
        if (disposable == null || disposable?.isDisposed == true) {
            isReset = false
            observeOnMain(completion)

        }
    }

    override fun reset() {
        isReset = true
        current = 0
        disposable?.dispose()
    }

    private fun observeOnMain(completion: (Int) -> Unit) {
        disposable = Observable.interval(sleepTime, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .takeWhile { !isReset && current < 100 }
            .subscribe {
                current++
                completion(current)
            }
    }
}
