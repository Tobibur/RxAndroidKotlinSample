package com.tobibur.rxandroidkotlinsample.model

import com.google.gson.annotations.SerializedName

data class QuoteModel(

    @SerializedName("quoteText")
    val quoteText: String? = null,

    val quoteAuthor: String? = null
)
