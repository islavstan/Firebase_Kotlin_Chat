package com.islavstan.firebasekotlinchat.core.chat

import android.content.Context
import android.util.Log
import com.google.firebase.database.*
import com.islavstan.firebasekotlinchat.models.Chat
import com.islavstan.firebasekotlinchat.utils.ARG_CHAT_ROOMS
import com.islavstan.firebasekotlinchat.utils.TAG





class ChatInteractor : ChatContract.Interactor {

    lateinit var mOnSendMessageListener: ChatContract.OnSendMessageListener
    lateinit var mOnGetMessagesListener: ChatContract.OnGetMessagesListener

    constructor(mOnSendMessageListener: ChatContract.OnSendMessageListener) {
        this.mOnSendMessageListener = mOnSendMessageListener
    }

    constructor(mOnGetMessagesListener: ChatContract.OnGetMessagesListener) {
        this.mOnGetMessagesListener = mOnGetMessagesListener
    }

    constructor(mOnSendMessageListener: ChatContract.OnSendMessageListener,
                mOnGetMessagesListener: ChatContract.OnGetMessagesListener) {
        this.mOnSendMessageListener = mOnSendMessageListener
        this.mOnGetMessagesListener = mOnGetMessagesListener
    }


    override fun sendMessageToFirebaseUser(context: Context, chat: Chat, receiverFirebaseToken: String) {
        val roomType1 = chat.senderUid + "_" + chat.receiverUid
        val roomType2 = chat.receiverUid + "_" + chat.senderUid
        val databaseReference = FirebaseDatabase.getInstance().reference

        databaseReference.child(ARG_CHAT_ROOMS).ref.addListenerForSingleValueEvent(object : ValueEventListener {

            override fun onCancelled(databaseError: DatabaseError) {
                mOnSendMessageListener.onSendMessageFailure("Unable to send message: " + databaseError.message)
            }


            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.hasChild(roomType1)) {
                    Log.d(TAG, "sendMessageToFirebaseUser:$roomType1 exists")
                    databaseReference.child(ARG_CHAT_ROOMS).child(roomType1).child(chat.timestamp.toString()).setValue(chat)
                } else if (dataSnapshot.hasChild(roomType2)) {
                    Log.d(TAG, "sendMessageToFirebaseUser:$roomType2 exists")
                    databaseReference.child(ARG_CHAT_ROOMS).child(roomType2).child(chat.timestamp.toString()).setValue(chat)

                } else {
                    Log.d(TAG, "sendMessageToFirebaseUser: success")
                    databaseReference.child(ARG_CHAT_ROOMS).child(roomType1).child(chat.timestamp.toString()).setValue(chat)
                    getMessageFromFirebaseUser(chat.senderUid, chat.receiverUid)
                }
                mOnSendMessageListener.onSendMessageSuccess()
            }
        }


        )
    }


    override fun getMessageFromFirebaseUser(senderUid: String, receiverUid: String) {
        val roomType1 = senderUid + "_" + receiverUid
        val roomType2 = receiverUid + "_" + senderUid
        val databaseReference = FirebaseDatabase.getInstance().reference
        databaseReference.child(ARG_CHAT_ROOMS).ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                mOnGetMessagesListener.onGetMessagesFailure("Unable to get message: " + p0.message)


            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.hasChild(roomType1)) {
                    Log.d(TAG, "getMessageFromFirebaseUser: $roomType1 exists")
                    FirebaseDatabase.getInstance().getReference().child(ARG_CHAT_ROOMS).child(roomType1)
                            .addChildEventListener(object : ChildEventListener {
                                override fun onCancelled(p0: DatabaseError?) {
                                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                                }

                                override fun onChildMoved(p0: DataSnapshot?, p1: String?) {
                                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                                }

                                override fun onChildChanged(p0: DataSnapshot?, p1: String?) {
                                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                                }

                                override fun onChildAdded(dataSnapshot: DataSnapshot, p1: String?) {
                                    val chat = dataSnapshot.getValue(Chat::class.java)
                                    mOnGetMessagesListener.onGetMessagesSuccess(chat)
                                }

                                override fun onChildRemoved(p0: DataSnapshot?) {
                                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                                }

                            })
                } else if (dataSnapshot.hasChild(roomType2)) {
                    Log.d(TAG, "getMessageFromFirebaseUser: $roomType2 exists")
                    FirebaseDatabase.getInstance().getReference().child(ARG_CHAT_ROOMS).child(roomType2)
                            .addChildEventListener(object : ChildEventListener {
                                override fun onCancelled(p0: DatabaseError?) {
                                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                                }

                                override fun onChildMoved(p0: DataSnapshot?, p1: String?) {
                                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                                }

                                override fun onChildChanged(p0: DataSnapshot?, p1: String?) {
                                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                                }

                                override fun onChildAdded(dataSnapshot: DataSnapshot, p1: String?) {
                                    val chat = dataSnapshot.getValue(Chat::class.java)
                                    mOnGetMessagesListener.onGetMessagesSuccess(chat)
                                }

                                override fun onChildRemoved(p0: DataSnapshot?) {
                                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                                }

                            })


                }
                else{
                    Log.d(TAG, "getMessageFromFirebaseUser: no such room available");
                }


            }

        })
    }

}