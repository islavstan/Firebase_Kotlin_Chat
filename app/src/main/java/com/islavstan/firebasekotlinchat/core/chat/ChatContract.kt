package com.islavstan.firebasekotlinchat.core.chat

import android.content.Context
import com.islavstan.firebasekotlinchat.models.Chat





interface ChatContract {
    interface View {
        fun onSendMessageSuccess()
        fun onSendMessageFailure(message: String)
        fun onGetMessageSuccess(chat: Chat)
        fun onGetMessageFailure(message: String)
    }

    interface Interactor {
        fun sendMessageToFirebaseUser(context: Context, chat: Chat, receiverFirebaseToken: String)
        fun getMessageFromFirebaseUser(senderUid: String, receiverUid: String)
    }

    interface Presenter {
        fun sendMessage(context: Context, chat: Chat, receiverFirebaseToken: String)
        fun getMessage(senderUid: String, receiverUid: String)
    }

    interface OnSendMessageListener {
        fun onSendMessageSuccess()
        fun onSendMessageFailure(message: String)
    }

    interface OnGetMessagesListener {
        fun onGetMessagesSuccess(chat: Chat)
        fun onGetMessagesFailure(message: String)
    }

}