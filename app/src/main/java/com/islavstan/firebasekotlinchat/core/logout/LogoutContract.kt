package com.islavstan.firebasekotlinchat.core.logout


interface LogoutContract {
    interface View {
        fun onLogoutSucess(message: String)
        fun onLogoutFailure(message: String)
    }

    interface Presenter {
        fun logout()
    }

    interface Interactor {
        fun performFirebaseLogout()
    }

    interface OnLogoutListener {
        fun onSuccess(message: String)
        fun onFailure(message: String)
    }
}