package com.tobibur.rxandroidkotlinsample.service

import com.tobibur.rxandroidkotlinsample.model.QuoteModel
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query



interface ApiInterface {

    @GET("api/1.0/")
    fun getQuotes(@Query(
            "method")method: String
                  ,@Query("format")format: String
                  ,@Query("lang") lang:String )
            : Single<QuoteModel>
}
