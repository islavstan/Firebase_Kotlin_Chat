package com.islavstan.firebasekotlinchat.core.users.add

import android.content.Context
import com.google.firebase.auth.FirebaseUser


interface AddUserContract {

    interface View {
        fun onAddUserSuccess(message: String)
        fun onAddUserFailure(message: String)

    }

    interface Presenter {
        fun addUser(context: Context, firebaseUser: FirebaseUser)
    }

    interface Interactor {
        fun addUserToDatabase(context: Context, firebaseUser: FirebaseUser)
    }

    interface OnUserDatabaseListener {
        fun onSuccess(message: String)
        fun onFailure(message: String)
    }

}