package com.tobibur.rxandroidkotlinsample.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tobibur.rxandroidkotlinsample.R
import kotlinx.android.synthetic.main.activity_navigation.*
import org.jetbrains.anko.startActivity

class NavigationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)

        main_activity_button.setOnClickListener { startActivity<MainActivity>() }
        retrofit_button.setOnClickListener { startActivity<RetrofitRxJavaActivity>() }
        rx_timer_btn.setOnClickListener{ startActivity<TimerActivity>()}
    }
}
