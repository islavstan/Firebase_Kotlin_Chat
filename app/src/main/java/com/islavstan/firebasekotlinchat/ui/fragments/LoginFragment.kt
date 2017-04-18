package com.islavstan.firebasekotlinchat.ui.fragments

import android.app.ProgressDialog
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.islavstan.firebasekotlinchat.R
import com.islavstan.firebasekotlinchat.core.login.LoginContract
import com.islavstan.firebasekotlinchat.core.login.LoginPresenter
import com.islavstan.firebasekotlinchat.ui.activities.RegisterActivity
import com.islavstan.firebasekotlinchat.ui.activities.UserListingActivity
import com.islavstan.firebasekotlinchat.utils.TAG
import com.pawegio.kandroid.toast
import kotlinx.android.synthetic.main.fragment_login.*
import android.content.Intent



class LoginFragment : Fragment(), LoginContract.View {
    lateinit var presenter: LoginPresenter
    var progressDialog: ProgressDialog? = null

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
        initItems()
        setOnClick()
    }

    private fun initItems() {
        presenter = LoginPresenter(this)
        progressDialog = ProgressDialog(activity)
        progressDialog?.setTitle("Loading")
        progressDialog?.setMessage("Please wait")
        progressDialog?.isIndeterminate = true


    }

    fun setOnClick() {
        registerBtn.setOnClickListener({ RegisterActivity.startActivity(activity) })
        loginBtn.setOnClickListener({ onLogin() })

    }


    private fun onLogin() {
        val email = emailET.text.trim().toString()
        val password = passwordET.text.trim().toString()
        if (email != "" && password != "") {
            presenter.login(activity, email, password)
            progressDialog?.show()
        } else toast("Fields can not be empty")

    }

    override fun onLoginSuccess(message: String) {
        progressDialog?.dismiss()
        toast("Logged in successfully")
        UserListingActivity.startActivity(activity, Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
    }

    override fun onLoginFailure(message: String) {
        progressDialog?.dismiss()
        toast("Error: $message")

    }


}