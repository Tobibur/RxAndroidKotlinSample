package com.tobibur.rxandroidkotlinsample.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tobibur.rxandroidkotlinsample.R
import com.tobibur.rxandroidkotlinsample.util.FragmentTools.replaceFragment
import kotlinx.android.synthetic.main.fragment_navigation.*

class NavigationFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_navigation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_launch_scheduler_rxjava.setOnClickListener {
            replaceFragment(SchedulerFragment.newInstance(), this.activity!!.supportFragmentManager, R.id.fragment_container)
        }

        btn_launch_retrofit_rxjava.setOnClickListener {
            replaceFragment(RetrofitRxJavaFragment.newInstance(), this.activity!!.supportFragmentManager, R.id.fragment_container)
        }

        btn_launch_timer_rxjava.setOnClickListener {
            replaceFragment(TimerFragment.newInstance(), this.activity!!.supportFragmentManager, R.id.fragment_container)
        }
    }

    companion object {
        fun newInstance() = NavigationFragment()
    }
}
