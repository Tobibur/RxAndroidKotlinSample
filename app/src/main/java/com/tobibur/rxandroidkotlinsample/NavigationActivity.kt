package com.tobibur.rxandroidkotlinsample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_navigation.*
import org.jetbrains.anko.startActivity

class NavigationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)

        main_activity_button.setOnClickListener { startActivity<MainActivity>() }
        retrofit_button.setOnClickListener { startActivity<RetrofitRxJavaActivity>() }
    }
}
