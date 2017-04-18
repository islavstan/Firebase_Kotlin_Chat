package com.islavstan.firebasekotlinchat.core.logout


class LogoutPresenter(val view: LogoutContract.View ) :LogoutContract.Presenter, LogoutContract.OnLogoutListener {

    val interactor = LogoutInteractor(this)

    override fun logout() {
        interactor.performFirebaseLogout()


    }

    override fun onSuccess(message: String) {
        view.onLogoutSucess(message)
    }

    override fun onFailure(message: String) {
        view.onLogoutFailure(message)
    }
}