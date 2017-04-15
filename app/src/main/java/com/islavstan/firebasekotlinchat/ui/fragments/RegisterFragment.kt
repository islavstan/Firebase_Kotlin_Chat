package com.islavstan.firebasekotlinchat.ui.fragments

import android.app.ProgressDialog
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.arellomobile.mvp.MvpFacade.init
import com.google.firebase.auth.FirebaseUser
import com.islavstan.firebasekotlinchat.R
import com.islavstan.firebasekotlinchat.core.registration.RegisterContract
import com.islavstan.firebasekotlinchat.core.registration.RegisterPresenter
import kotlinx.android.synthetic.main.fragment_register.*

class RegisterFragment: Fragment(), View.OnClickListener, RegisterContract.View {

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
        registerBtn.setOnClickListener(this)


    }


    override fun onClick(v: View?) {
        val viewId = v?.id
        when (viewId) {
            R.id.registerBtn -> onRegister(v)
        }

    }

    private fun  onRegister(v: View?) {

    }

    override fun onRegistrationSuccess(firebaseUser: FirebaseUser) {

    }

    override fun onRegistrationFailure(message: String) {

    }


}