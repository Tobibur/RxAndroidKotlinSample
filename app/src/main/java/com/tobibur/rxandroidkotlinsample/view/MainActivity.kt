package com.tobibur.rxandroidkotlinsample.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.tobibur.rxandroidkotlinsample.R
import com.tobibur.rxandroidkotlinsample.util.FragmentTools.replaceFragment


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        replaceFragment(NavigationFragment.newInstance(), this.supportFragmentManager, R.id.fragment_container)
    }

    override fun onBackPressed() {
        val backStackEntryCount = supportFragmentManager.backStackEntryCount
        Log.d("test", backStackEntryCount.toString())
        if (backStackEntryCount == 1) {
            finish()
        } else {
            if (backStackEntryCount > 1) {
                supportFragmentManager.popBackStack()
            } else {
                super.onBackPressed()
            }
        }
    }
}
