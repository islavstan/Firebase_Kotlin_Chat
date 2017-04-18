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
import com.islavstan.firebasekotlinchat.core.users.add.AddUserContract
import com.islavstan.firebasekotlinchat.core.users.add.AddUserPresenter
import com.pawegio.kandroid.toast
import kotlinx.android.synthetic.main.fragment_register.*
import android.content.Intent
import com.islavstan.firebasekotlinchat.ui.activities.UserListingActivity



class RegisterFragment: Fragment(), RegisterContract.View, AddUserContract.View {


    var regPresenter: RegisterPresenter? = null
    var addUserPresenter: AddUserPresenter? = null
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
        addUserPresenter = AddUserPresenter(this)
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
        progressDialog?.setMessage("Add user to db")
        this.toast("Registration Successful!")
        addUserPresenter?.addUser(activity, firebaseUser)
    }

    override fun onRegistrationFailure(message: String) {
        progressDialog?.dismiss()
        this.toast("Registration failed!+\n$message")
    }

    override fun onAddUserSuccess(message: String) {
        progressDialog?.dismiss()
        this.toast(message)
        UserListingActivity.startActivity(activity,
                Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)


    }

    override fun onAddUserFailure(message: String) {
        progressDialog?.dismiss()
        this.toast(message)
    }


}