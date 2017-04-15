package com.islavstan.firebasekotlinchat.core.registration

import android.app.Activity
import com.google.firebase.auth.FirebaseUser


class RegisterPresenter(registerView:RegisterContract.View) :RegisterContract.Presenter, RegisterContract.onRegistrationListener {

     var registerInteractor: RegisterInteractor = RegisterInteractor(this)
    var mRegisterView: RegisterContract.View = registerView


    override fun register(activity: Activity, email: String, password: String) {
           registerInteractor.performFirebaseRegistration(activity, email, password)

    }

    override fun onSuccess(firebaseUser: FirebaseUser) {
        mRegisterView.onRegistrationSuccess(firebaseUser)

    }

    override fun onFailure(message: String) {
        mRegisterView.onRegistrationFailure(message)
    }
}