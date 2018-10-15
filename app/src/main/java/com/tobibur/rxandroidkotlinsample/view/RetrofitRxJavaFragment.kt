package com.tobibur.rxandroidkotlinsample.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tobibur.rxandroidkotlinsample.R
import com.tobibur.rxandroidkotlinsample.model.QuoteModel
import com.tobibur.rxandroidkotlinsample.service.ApiClient
import com.tobibur.rxandroidkotlinsample.service.ApiInterface
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_retrofit_rxjava.*
import org.jetbrains.anko.toast

class RetrofitRxJavaFragment : Fragment() {

    private var disposables: CompositeDisposable = CompositeDisposable()
    private val apiService = ApiClient().getClient()!!.create(ApiInterface::class.java)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_retrofit_rxjava, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getQuotes()
    }

    private fun getQuotes() {
        disposables.add(
                apiService.getQuotes("getQuote", "json", "en")
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .retry(2)
                        .subscribeWith(object : DisposableSingleObserver<QuoteModel>() {
                            @SuppressLint("SetTextI18n")
                            override fun onSuccess(t: QuoteModel) {
                                tv_quote.text = t.quoteText + "\n\n -" + t.quoteAuthor
                            }

                            override fun onError(e: Throwable) {
                                activity!!.toast(e.message.toString())
                            }

                        })
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.dispose()
    }

    companion object {
        fun newInstance() = RetrofitRxJavaFragment()
    }
}
