package com.islavstan.firebasekotlinchat.core.chat

import android.content.Context
import android.net.Uri
import com.islavstan.firebasekotlinchat.models.Chat


class ChatPresenter (val view:ChatContract.View) :ChatContract.Presenter, ChatContract.OnSendMessageListener, ChatContract.OnGetMessagesListener, ChatContract.OnGetTypingListener, ChatContract.RemoveMessageListener{



    val interactor = ChatInteractor(this, this, this, this)


    override fun changeTypingStatus(senderUid: String, receiverUid: String, status: Boolean) {
    interactor.changeTypingStatus(senderUid, receiverUid, status)
    }

    override fun setTypingStatus(senderUid: String, receiverUid: String) {
    interactor.setTypingStatus(senderUid, receiverUid)
    }
    override fun sendMessage(context: Context, chat: Chat, receiverFirebaseToken: String) {
     interactor.sendMessageToFirebaseUser(context, chat, receiverFirebaseToken)
    }

    override fun getMessage(senderUid: String, receiverUid: String) {
        interactor.getMessageFromFirebaseUser(senderUid, receiverUid)
    }

    override fun onSendMessageSuccess() {
        view.onSendMessageSuccess()
    }

    override fun onSendMessageFailure(message: String) {
      view.onSendMessageFailure(message)
    }

    override fun onGetMessagesSuccess(chat: Chat) {
       view.onGetMessageSuccess(chat)
    }

    override fun onGetMessagesFailure(message: String) {
       view.onGetMessageFailure(message)
    }

    override fun onGetTyping(typing: Boolean) {
        view.getTypingStatus(typing)
    }

    override fun loadImageToServer(uri: Uri, context: Context, chat: Chat, receiverFirebaseToken: String) {
       interactor.loadImageToServer(uri, context, chat, receiverFirebaseToken)
    }
    override fun removeMessage(name: String, senderUid: String, receiverUid: String) {
         interactor.removeMessage(name, senderUid, receiverUid)
    }

    override fun messageRemoved(timestamp: String) {
             view.removeMessageSuccess(timestamp)
    }

}