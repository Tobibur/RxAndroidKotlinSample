package com.tobibur.rxandroidkotlinsample.view

import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tobibur.rxandroidkotlinsample.R
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_scheduler.*

class SchedulerFragment : Fragment() {

    private val disposables: CompositeDisposable = CompositeDisposable()
    private val mTAG: String = "Scheduler Fragment"
    private var result: String = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_scheduler, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_start_scheduler.setOnClickListener {
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
                        tv_scheduler_result.text = result
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
                    .filter {
                        it.length > 3
                    }
                    .distinct()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }

    companion object {
        fun newInstance() = SchedulerFragment()
    }
}
