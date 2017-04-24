package com.islavstan.firebasekotlinchat.ui.fragments

import android.app.Activity.RESULT_OK
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
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
import com.islavstan.firebasekotlinchat.utils.*
import com.pawegio.kandroid.toast
import org.greenrobot.eventbus.EventBus
import id.zelory.compressor.Compressor;

import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import java.io.File
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
    private val SELECT_PICTURE = 100
    var selectedImageUri: Uri? = null
    var photoFile: File? = null
    var actualImage: File? = null


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
        imageBtn?.setOnClickListener({openImageChooser()})


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


    private fun openImageChooser() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE)
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

    private fun sendImage(){
        progressDialog?.show()
        val receiver = arguments.getString(ARG_RECEIVER)
        val receiverUid = arguments.getString(ARG_RECEIVER_UID)
        val receiverToken = arguments.getString(ARG_FIREBASE_TOKEN)
        val sender = FirebaseAuth.getInstance().currentUser?.email
        val senderUid = FirebaseAuth.getInstance().currentUser?.uid
        val chat = Chat(sender as String, receiver, senderUid as String, receiverUid, System.currentTimeMillis(), 3)
        presenter?.loadImageToServer(Uri.fromFile(photoFile),activity.applicationContext, chat, receiverToken)
    }


    override fun onSendMessageSuccess() {
        Log.d(TAG, "onSendMessageSuccess")
        messageET?.setText("")

    }

    override fun onSendMessageFailure(message: String) {
        toast(message)
        progressDialog?.dismiss()
    }

    override fun onGetMessageSuccess(chat: Chat) {
        progressDialog?.dismiss()
        if (chat.senderUid != FirebaseAuth.getInstance().currentUser?.uid) {
            if (chat.mesType == 1) {
                chat.mesType = 2
            } else if (chat.mesType == 3) {
                chat.mesType = 4
            }
        }
        recAdapter.addChat(chat)
        chatRecycler?.smoothScrollToPosition(recAdapter.getItemCount() - 1);
    }

    override fun onGetMessageFailure(message: String) {
        progressDialog?.dismiss()
        toast(message)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == RESULT_OK){
            if(requestCode == SELECT_PICTURE){
                selectedImageUri = data?.data
                actualImage =  File(MarshMallowPermission.getPath(activity, selectedImageUri));
                photoFile = Compressor.getDefault(activity).compressToFile(actualImage)
                sendImage()

            }
        }
    }





}