package com.tobibur.rxandroidkotlinsample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import android.os.SystemClock
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private val disposables : CompositeDisposable = CompositeDisposable()

    private val mTAG : String = "MainActivity"
    var result : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {
            onRunSchedulerExample()
        }
    }

    private fun onRunSchedulerExample() {
        disposables.add(sampleObservable()
                // Run on a background thread
                .subscribeOn(Schedulers.io())
                // Be notified on the main thread
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<String>() {
                    override fun onComplete() {
                        textView.text = result
                        Log.d(mTAG, "OnCompleted()")
                    }

                    override fun onError(e: Throwable) {
                        Log.d(mTAG, "OnError()")
                    }

                    override fun onNext(string: String) {
                        result += "onNext() -> $string\n"
                        Log.d(mTAG, "onNext() -> $string")
                    }
                }))
    }

    private fun sampleObservable(): Observable<String> {
        return Observable.defer {
            // Do some long running operation
            SystemClock.sleep(5000)
            Observable.just("one", "two", "three", "four", "five", "five")
                    .filter(io.reactivex.functions.Predicate {
                        return@Predicate it.length>3
                    })
                    .distinct()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }
}
