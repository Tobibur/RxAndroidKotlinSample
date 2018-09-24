package com.tobibur.rxandroidkotlinsample.view

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.tobibur.rxandroidkotlinsample.R
import com.tobibur.rxandroidkotlinsample.model.QuoteModel
import com.tobibur.rxandroidkotlinsample.service.ApiClient
import com.tobibur.rxandroidkotlinsample.service.ApiInterface
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_retrofit_rx_java.*
import org.jetbrains.anko.toast

class RetrofitRxJavaActivity : AppCompatActivity() {

    private var disposables: CompositeDisposable = CompositeDisposable()
    private val apiService = ApiClient().getClient()!!.create(ApiInterface::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_retrofit_rx_java)

        getQuotes()
    }

    private fun getQuotes() {
        disposables.add(
                apiService.getQuotes("getQuote","json","en")
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .retry(2)
                        .subscribeWith(object : DisposableSingleObserver<QuoteModel>(){
                            @SuppressLint("SetTextI18n")
                            override fun onSuccess(t: QuoteModel) {
                                quoteText.text = t.quoteText + "\n\n -"+ t.quoteAuthor
                            }

                            override fun onError(e: Throwable) {
                                toast(e.message.toString())
                            }

                        })
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.dispose()
    }
}
