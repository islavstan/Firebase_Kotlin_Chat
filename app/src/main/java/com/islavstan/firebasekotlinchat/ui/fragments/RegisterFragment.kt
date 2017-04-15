package com.islavstan.firebasekotlinchat.ui.fragments

import android.app.ProgressDialog
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseUser
import com.islavstan.firebasekotlinchat.R
import com.islavstan.firebasekotlinchat.core.registration.RegisterContract
import com.islavstan.firebasekotlinchat.core.registration.RegisterPresenter
import com.pawegio.kandroid.toast
import kotlinx.android.synthetic.main.fragment_register.*

class RegisterFragment: Fragment(), RegisterContract.View {

    var regPresenter: RegisterPresenter? = null
    var progressDialog: ProgressDialog? = null


    companion object {
        fun newInstance(): RegisterFragment {
            val args = Bundle()
            val fragment = RegisterFragment()
            fragment.arguments = args
            return fragment

        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val fragmentView = inflater?.inflate(R.layout.fragment_register, container, false)
        return fragmentView

    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initItems()
    }

    private fun initItems() {
        regPresenter = RegisterPresenter(this)
        progressDialog = ProgressDialog(activity)
        progressDialog?.setTitle("loading...")
        progressDialog?.setMessage("Please wait")
        progressDialog?.isIndeterminate = true
        registerBtn.setOnClickListener({ onRegister() })


    }


    private fun onRegister() {
        val email = emailET.text.trim().toString()
        val password = passwordET.text.trim().toString()
        if (email == "" || password == "") {
            this.toast("Fields can not be empty")
        } else {
            regPresenter?.register(activity, email, password)
            progressDialog?.show()

        }
    }

    override fun onRegistrationSuccess(firebaseUser: FirebaseUser) {
        this.toast("Registration Successful!")
    }

    override fun onRegistrationFailure(message: String) {
        progressDialog?.dismiss()
        this.toast("Registration failed!+\n" + message)
    }


}