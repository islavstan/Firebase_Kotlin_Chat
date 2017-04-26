package com.islavstan.firebasekotlinchat.core.users.get_all

import com.islavstan.firebasekotlinchat.models.User


interface GetUsersContract {
    interface View {
        fun onGetAllUsersSuccess(users: List<User>)
        fun onGetAllUsersFailure(message: String)
        fun onGetChatUsersSuccess(users: List<User>)
        fun onGetChatUsersFailure(message: String)
    }

    interface Presenter {
        fun getAllUsersFromFirebase(senderUid: String)
        fun getChatUsersFromFirebase()
    }

     interface Interactor {
        fun getAllUsersFromFirebase(senderUid: String)

        fun getChatUsersFromFirebase()
    }

    interface OnGetAllUsersListener {
        fun onGetAllUsersSuccess(users: List<User>)
        fun onGetAllUsersFailure(message: String)
    }

    interface OnGetChatUsersListener {
        fun onGetChatUsersSuccess(users: List<User>)
        fun onGetChatUsersFailure(message: String)
    }
}