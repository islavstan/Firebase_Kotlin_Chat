package com.islavstan.firebasekotlinchat.ui.fragments

import android.app.ProgressDialog
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.islavstan.firebasekotlinchat.R
import com.islavstan.firebasekotlinchat.core.chat.ChatContract
import com.islavstan.firebasekotlinchat.core.chat.ChatPresenter
import com.islavstan.firebasekotlinchat.models.Chat
import com.islavstan.firebasekotlinchat.ui.adapters.ChatRecyclerAdapter
import com.islavstan.firebasekotlinchat.utils.ARG_FIREBASE_TOKEN
import com.islavstan.firebasekotlinchat.utils.ARG_RECEIVER
import com.islavstan.firebasekotlinchat.utils.ARG_RECEIVER_UID
import com.islavstan.firebasekotlinchat.utils.TAG
import com.pawegio.kandroid.toast
import org.greenrobot.eventbus.EventBus

import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit


class ChatFragment : Fragment(), ChatContract.View {


    var progressDialog: ProgressDialog? = null
    var sendBtn: ImageButton? = null
    var imageBtn: ImageButton? = null
    var messageET: EditText? = null
    var typingStatus: TextView? = null
    var chatRecycler: RecyclerView? = null


    var presenter: ChatPresenter? = null
    var chatList = mutableListOf<Chat>()
    lateinit var recAdapter: ChatRecyclerAdapter
    var send: Boolean = false

    companion object {
        fun newInstance(receiver: String, receiverUid: String, token: String): ChatFragment {
            val args = Bundle()
            args.putString(ARG_RECEIVER, receiver)
            args.putString(ARG_RECEIVER_UID, receiverUid)
            args.putString(ARG_FIREBASE_TOKEN, token)
            val fragment = ChatFragment()
            fragment.arguments = args
            return fragment
        }
    }


    override fun onStart() {
        super.onStart()
        // EventBus.getDefault().register(this)
    }


    override fun onStop() {
        super.onStop()
        //  EventBus.getDefault().unregister(this)
    }


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val fragmentView = inflater?.inflate(R.layout.fragment_chat, container, false) as View
        bindView(fragmentView)
        return fragmentView

    }


    fun bindView(view: View) {
        sendBtn = view.findViewById(R.id.sendBtn) as ImageButton
        imageBtn = view.findViewById(R.id.imageBtn) as ImageButton
        messageET = view.findViewById(R.id.messageET) as EditText
        typingStatus = view.findViewById(R.id.typingStatus) as TextView
        chatRecycler = view.findViewById(R.id.chatRecycler) as RecyclerView
    }




    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initItems()
    }

    private fun initItems() {
        progressDialog = ProgressDialog(activity)
        progressDialog?.setTitle("loading...")
        progressDialog?.setMessage("Please wait")
        progressDialog?.isIndeterminate = true
        presenter = ChatPresenter(this)
        sendBtn?.setOnClickListener({ sendMessage() })
        recAdapter = ChatRecyclerAdapter(chatList)
        chatRecycler?.adapter = recAdapter
        presenter?.getMessage(FirebaseAuth.getInstance().currentUser?.uid!!,
                arguments.getString(ARG_RECEIVER_UID))
        presenter?.setTypingStatus(FirebaseAuth.getInstance().currentUser?.uid!!,
                arguments.getString(ARG_RECEIVER_UID))




        Observable.create(Observable.OnSubscribe<String> { subscriber ->
            messageET?.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) = Unit

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    subscriber.onNext(s.toString())
                    if (!send) {
                        presenter?.changeTypingStatus(FirebaseAuth.getInstance().currentUser?.uid!!,
                                arguments.getString(ARG_RECEIVER_UID), true)
                        send = true

                    }
                }

            })
        }).debounce(1000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    text ->
                    presenter?.changeTypingStatus(FirebaseAuth.getInstance().currentUser?.uid!!,
                            arguments.getString(ARG_RECEIVER_UID), false)
                    send = false


                })

    }


    override fun getTypingStatus(status: Boolean) {
        if (status) {
            typingStatus?.visibility = View.VISIBLE
        } else typingStatus?.visibility = View.GONE
    }

    private fun sendMessage() {
        val message = messageET?.text?.trim().toString()
        val receiver = arguments.getString(ARG_RECEIVER)
        val receiverUid = arguments.getString(ARG_RECEIVER_UID)
        val receiverToken = arguments.getString(ARG_FIREBASE_TOKEN)
        val sender = FirebaseAuth.getInstance().currentUser?.email
        val senderUid = FirebaseAuth.getInstance().currentUser?.uid
        val chat = Chat(sender as String, receiver, senderUid as String, receiverUid, message, System.currentTimeMillis(), 1)
        presenter?.sendMessage(activity.applicationContext, chat, receiverToken)


    }


    override fun onSendMessageSuccess() {
        Log.d(TAG, "onSendMessageSuccess")
        messageET?.setText("")

    }

    override fun onSendMessageFailure(message: String) {
        toast(message)
    }

    override fun onGetMessageSuccess(chat: Chat) {
        if (chat.senderUid != FirebaseAuth.getInstance().currentUser?.uid) {
            chat.mesType = 2
        }
        recAdapter.addChat(chat)
        chatRecycler?.smoothScrollToPosition(recAdapter.getItemCount() - 1);
    }

    override fun onGetMessageFailure(message: String) {
        toast(message)
    }


}