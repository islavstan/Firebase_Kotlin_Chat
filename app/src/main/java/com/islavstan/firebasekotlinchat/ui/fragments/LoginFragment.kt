package com.islavstan.firebasekotlinchat.ui.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.islavstan.firebasekotlinchat.R
import com.islavstan.firebasekotlinchat.core.login.LoginContract
import com.islavstan.firebasekotlinchat.ui.activities.RegisterActivity
import com.islavstan.firebasekotlinchat.utils.TAG
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment(), LoginContract.View {

    companion object {
        fun newInstance(): LoginFragment {
            val args = Bundle()
            val fragment = LoginFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val fragmentView = inflater?.inflate(R.layout.fragment_login, container, false)

        return fragmentView

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setOnClick()
    }

    fun setOnClick() {
        registerBtn.setOnClickListener({ RegisterActivity.startActivity(activity) })

    }


    override fun onLoginSuccess(message: String) {

    }

    override fun onLoginFailure(message: String) {

    }


}