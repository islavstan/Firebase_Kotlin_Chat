package com.islavstan.firebasekotlinchat.core.chat

import android.content.Context
import android.net.Uri
import com.islavstan.firebasekotlinchat.models.Chat





interface ChatContract {
    interface View {
        fun onSendMessageSuccess()
        fun onSendMessageFailure(message: String)
        fun onGetMessageSuccess(chat: Chat)
        fun onGetMessageFailure(message: String)
        fun getTypingStatus(status: Boolean)
    }

    interface Interactor {
        fun sendMessageToFirebaseUser(context: Context, chat: Chat, receiverFirebaseToken: String)
        fun getMessageFromFirebaseUser(senderUid: String, receiverUid: String)
        fun setTypingStatus(senderUid: String, receiverUid: String)
        fun changeTypingStatus(senderUid: String, receiverUid: String, status: Boolean)
        fun getTypingStatus(senderUid: String, receiverUid: String)
        fun loadImageToServer(uri:Uri, context: Context, chat: Chat, receiverFirebaseToken: String)

    }

    interface Presenter {
        fun sendMessage(context: Context, chat: Chat, receiverFirebaseToken: String)
        fun getMessage(senderUid: String, receiverUid: String)
        fun setTypingStatus(senderUid: String, receiverUid: String)
        fun changeTypingStatus(senderUid: String, receiverUid: String, status: Boolean)
        fun loadImageToServer(uri:Uri, context: Context, chat: Chat, receiverFirebaseToken: String)
    }

    interface OnSendMessageListener {
        fun onSendMessageSuccess()
        fun onSendMessageFailure(message: String)
    }

    interface OnGetMessagesListener {
        fun onGetMessagesSuccess(chat: Chat)
        fun onGetMessagesFailure(message: String)
    }

    interface OnGetTypingListener {
        fun onGetTyping(typing: Boolean)
    }

}