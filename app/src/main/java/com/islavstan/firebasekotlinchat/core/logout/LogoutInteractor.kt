package com.islavstan.firebasekotlinchat.core.logout

import com.google.firebase.auth.FirebaseAuth


class LogoutInteractor(val listener: LogoutContract.OnLogoutListener): LogoutContract.Interactor {

    override fun performFirebaseLogout() {
        if (FirebaseAuth.getInstance().currentUser != null) {
            FirebaseAuth.getInstance().signOut()
            listener.onSuccess("Successfully logged out!")
        } else {
            listener.onFailure("No user logged in yet!")
        }
    }
}