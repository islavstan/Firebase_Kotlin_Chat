package com.islavstan.firebasekotlinchat.core.users.get_all

import com.islavstan.firebasekotlinchat.models.User


class GetUsersPresenter (val view:GetUsersContract.View) :GetUsersContract.Presenter, GetUsersContract.OnGetAllUsersListener {
    val interactor: GetUsersInteractor = GetUsersInteractor(this)

    override fun getAllUsersFromFirebase() {
        interactor.getAllUsersFromFirebase()
    }

    override fun getChatUsersFromFirebase() {

    }

    override fun onGetAllUsersSuccess(users: List<User>) {
        view.onGetAllUsersSuccess(users)
    }

    override fun onGetAllUsersFailure(message: String) {
        view.onGetAllUsersFailure(message)
    }
}