package com.tobibur.rxandroidkotlinsample.view

import android.os.Bundle
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
import kotlinx.android.synthetic.main.fragment_timer.*
import java.util.concurrent.TimeUnit

class TimerFragment : Fragment() {

    private var compositeDisposable = CompositeDisposable()
    private var lastTimerValue: Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_timer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        startTimer(30)

        controlTimer()
    }

    private fun startTimer(time: Long) {
        compositeDisposable.add(Observable.interval(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .take(time+1)
                .map { num -> num.toInt() }
                .map { num -> time.toInt() - num }
                .subscribeWith(object : DisposableObserver<Int>() {
                    override fun onComplete() {
                        tv_timer.text = getString(R.string.done)
                        Log.d("Timer", "Completed!")
                    }

                    override fun onNext(t: Int) {
                        pb_timer.progress = t
                        tv_timer.text = t.toString()
                        Log.d("Timer", t.toString())
                        lastTimerValue = t
                    }

                    override fun onError(e: Throwable) {

                    }

                }))
    }

    private fun controlTimer() {
        timerControlBtn.setOnClickListener {
            if(timerControlBtn.text == resources.getString(R.string.pause)) {
                disposeTimer()
                timerControlBtn.text = resources.getString(R.string.play)
            }else{
                compositeDisposable = CompositeDisposable()
                startTimer(lastTimerValue.toLong())
                timerControlBtn.text = resources.getString(R.string.pause)
            }
        }
    }

    private fun disposeTimer() {
        compositeDisposable.dispose()
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

    companion object {
        fun newInstance() = TimerFragment()
    }
}
