package com.islavstan.firebasekotlinchat.core.login

import android.app.Activity


class LoginPresenter(val view:LoginContract.View):LoginContract.Presenter, LoginContract.onLoginListener {
    val interactor = LoginInteractor(this)


    override fun login(activity: Activity, email: String, password: String) {
        interactor.performFirebaseLogin(activity, email, password)
    }

    override fun onSuccess(message: String) {
        view.onLoginSuccess(message)
    }

    override fun onFailure(message: String) {
        view.onLoginFailure(message)
    }
}