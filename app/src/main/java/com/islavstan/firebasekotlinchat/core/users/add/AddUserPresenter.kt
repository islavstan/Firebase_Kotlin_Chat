package com.islavstan.firebasekotlinchat.core.users.add

import android.content.Context
import com.google.firebase.auth.FirebaseUser


class AddUserPresenter(private val view:AddUserContract.View) : AddUserContract.Presenter, AddUserContract.OnUserDatabaseListener {
    private val interactor: AddUserInteractor = AddUserInteractor(this)


    override fun onSuccess(message: String) {
        view.onAddUserSuccess(message)
    }

    override fun onFailure(message: String) {
        view.onAddUserFailure(message)
    }

    override fun addUser(context: Context, firebaseUser: FirebaseUser) {
        interactor.addUserToDatabase(context, firebaseUser)
    }
}