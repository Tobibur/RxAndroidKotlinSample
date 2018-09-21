package com.tobibur.rxandroidkotlinsample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.d
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import kotlinx.android.synthetic.main.activity_timer.*
import java.util.concurrent.TimeUnit

class TimerActivity : AppCompatActivity() {

    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer)

//        progress.max = 30
//        progress.isIndeterminate = false

        compositeDisposable.add(Observable.interval(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .take(31)
                .subscribeWith(object : DisposableObserver<Long>(){
                    override fun onComplete() {
                        timerText.text = getString(R.string.done)
                        d("Timer", "Completed!")
                    }

                    override fun onNext(t: Long) {
                        progress.progress = t.toInt()
                        timerText.text = t.toString()
                        d("Timer", t.toString())
                    }

                    override fun onError(e: Throwable) {

                    }

                }))
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}
