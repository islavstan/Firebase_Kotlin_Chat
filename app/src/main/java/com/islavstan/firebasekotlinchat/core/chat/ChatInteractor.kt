package com.islavstan.firebasekotlinchat.core.chat

import android.content.Context
import android.net.Uri
import android.util.Log
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.islavstan.firebasekotlinchat.models.Chat
import com.islavstan.firebasekotlinchat.models.TypingInfo
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.google.android.gms.tasks.OnSuccessListener
import android.support.annotation.NonNull
import com.google.android.gms.tasks.OnFailureListener
import com.islavstan.firebasekotlinchat.fcm.FcmNotificationBuilder
import com.islavstan.firebasekotlinchat.utils.*


class ChatInteractor : ChatContract.Interactor {


    lateinit var mOnSendMessageListener: ChatContract.OnSendMessageListener
    lateinit var mOnGetMessagesListener: ChatContract.OnGetMessagesListener
    lateinit var mOnGeTypingListener: ChatContract.OnGetTypingListener
    lateinit var mRemoveMessageListener: ChatContract.RemoveMessageListener

    constructor(mOnSendMessageListener: ChatContract.OnSendMessageListener) {
        this.mOnSendMessageListener = mOnSendMessageListener
    }

    constructor(mOnGetMessagesListener: ChatContract.OnGetMessagesListener) {
        this.mOnGetMessagesListener = mOnGetMessagesListener
    }

    constructor(mOnSendMessageListener: ChatContract.OnSendMessageListener, mOnGetMessagesListener: ChatContract.OnGetMessagesListener, mOnGeTypingListener: ChatContract.OnGetTypingListener, mRemoveMessageListener: ChatContract.RemoveMessageListener) {
        this.mOnSendMessageListener = mOnSendMessageListener
        this.mOnGetMessagesListener = mOnGetMessagesListener
        this.mOnGeTypingListener = mOnGeTypingListener
        this.mRemoveMessageListener = mRemoveMessageListener
    }


    override fun setTypingStatus(senderUid: String, receiverUid: String) {
        val roomType1 = senderUid + "_" + receiverUid
        val roomType2 = receiverUid + "_" + senderUid
        val databaseReference = FirebaseDatabase.getInstance().reference
        databaseReference.child(ARG_CHAT_ROOMS).ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError?) {
                Log.d(TAG, databaseError?.message)
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.hasChild(roomType1)) {
                    databaseReference.child(ARG_CHAT_ROOMS).child(roomType1).child(TYPING_STATUS).setValue(TypingInfo(false, false))
                } else if (dataSnapshot.hasChild(roomType2)) {
                    databaseReference.child(ARG_CHAT_ROOMS).child(roomType2).child(TYPING_STATUS).setValue(TypingInfo(false, false))
                } else {
                    databaseReference.child(ARG_CHAT_ROOMS).child(roomType1).child(TYPING_STATUS).setValue(TypingInfo(false, false))

                }

            }

        })

        getTypingStatus(senderUid, receiverUid)


    }


    override fun removeMessage(name: String, senderUid: String, receiverUid: String) {
        val roomType1 = senderUid + "_" + receiverUid
        val roomType2 = receiverUid + "_" + senderUid
        val databaseReference = FirebaseDatabase.getInstance().reference
        databaseReference.child(ARG_CHAT_ROOMS).ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError?) {
                Log.d(TAG, databaseError?.message)
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.hasChild(roomType1)) {
                    databaseReference.child(ARG_CHAT_ROOMS).child(roomType1).child("messages").child(name).removeValue()
                } else if (dataSnapshot.hasChild(roomType2)) {
                    databaseReference.child(ARG_CHAT_ROOMS).child(roomType2).child("messages").child(name).removeValue()
                } else {
                    databaseReference.child(ARG_CHAT_ROOMS).child(roomType1).child("messages").child(name).removeValue()

                }

            }

        })


    }

    override fun loadImageToServer(uri: Uri, context: Context, chat: Chat, receiverFirebaseToken: String) {
        val storage = FirebaseStorage.getInstance()
        val storageRef = storage.reference
        val riversRef = storageRef.child("images/" + uri.lastPathSegment)
        val uploadTask = riversRef.putFile(uri)
        uploadTask.addOnFailureListener { exception ->
            Log.d(TAG, "loadImageToServer error = " + exception.message)
        }.addOnSuccessListener { taskSnapshot ->
            // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
            val downloadUrl = taskSnapshot.downloadUrl
            chat.message = downloadUrl.toString()
            sendMessageToFirebaseUser(context, chat, receiverFirebaseToken)
        }

    }


    override fun changeTypingStatus(senderUid: String, receiverUid: String, status: Boolean) {
        val roomType1 = senderUid + "_" + receiverUid
        val roomType2 = receiverUid + "_" + senderUid
        val databaseReference = FirebaseDatabase.getInstance().reference
        databaseReference.child(ARG_CHAT_ROOMS).ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError?) {
                Log.d(TAG, databaseError?.message)
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.hasChild(roomType1)) {
                    databaseReference.child(ARG_CHAT_ROOMS).child(roomType1).child(TYPING_STATUS).child("senderTyping").setValue(status)
                } else if (dataSnapshot.hasChild(roomType2)) {
                    databaseReference.child(ARG_CHAT_ROOMS).child(roomType2).child(TYPING_STATUS).child("receiverTyping").setValue(status)
                } else {
                    databaseReference.child(ARG_CHAT_ROOMS).child(roomType1).child(TYPING_STATUS).child("senderTyping").setValue(status)

                }

            }

        })


    }


    override fun getTypingStatus(senderUid: String, receiverUid: String) {
        val roomType1 = senderUid + "_" + receiverUid
        val roomType2 = receiverUid + "_" + senderUid
        val databaseReference = FirebaseDatabase.getInstance().reference
        databaseReference.child(ARG_CHAT_ROOMS).ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {


            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.hasChild(roomType1)) {

                    FirebaseDatabase.getInstance().getReference().child(ARG_CHAT_ROOMS).child(roomType1).child("typing_status")/*.child("receiverTyping")*/
                            .addChildEventListener(object : ChildEventListener {
                                override fun onCancelled(p0: DatabaseError?) {
                                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                                }

                                override fun onChildMoved(p0: DataSnapshot?, p1: String?) {
                                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                                }

                                override fun onChildChanged(dataSnapshot: DataSnapshot, p1: String?) {
                                    if (dataSnapshot.key == "receiverTyping")
                                        mOnGeTypingListener.onGetTyping(dataSnapshot.getValue(Boolean::class.java))
                                }

                                override fun onChildAdded(dataSnapshot: DataSnapshot, p1: String?) {

                                }

                                override fun onChildRemoved(p0: DataSnapshot?) {
                                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                                }

                            })
                } else if (dataSnapshot.hasChild(roomType2)) {
                    Log.d(TAG, "getMessageFromFirebaseUser: $roomType2 exists")
                    FirebaseDatabase.getInstance().getReference().child(ARG_CHAT_ROOMS).child(roomType2).child("typing_status")/*.child("senderTyping")*/
                            .addChildEventListener(object : ChildEventListener {
                                override fun onCancelled(p0: DatabaseError?) {
                                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                                }

                                override fun onChildMoved(p0: DataSnapshot?, p1: String?) {
                                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                                }

                                override fun onChildChanged(dataSnapshot: DataSnapshot, p1: String?) {
                                    if (dataSnapshot.key == "senderTyping")
                                        mOnGeTypingListener.onGetTyping(dataSnapshot.getValue(Boolean::class.java))

                                }

                                override fun onChildAdded(dataSnapshot: DataSnapshot, p1: String?) {

                                }

                                override fun onChildRemoved(p0: DataSnapshot?) {
                                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                                }

                            })


                } else {

                }


            }

        })
    }


    private fun sendPushNotificationToReceiver(username: String,
                                               message: String,
                                               uid: String,
                                               firebaseToken: String,
                                               receiverFirebaseToken: String) {
        Log.d(TAG, "sendPushNotificationToReceiver")
        FcmNotificationBuilder.initialize()
                .title(username)
                .message(message)
                .username(username)
                .uid(uid)
                .firebaseToken(firebaseToken)
                .receiverFirebaseToken(receiverFirebaseToken)
                .send()
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
                    databaseReference.child(ARG_CHAT_ROOMS).child(roomType1).child("messages").child(chat.timestamp.toString()).setValue(chat)
                    databaseReference.child(ARG_USERS).child(chat.senderUid).child("lastMessage").setValue(chat.message)
                    databaseReference.child(ARG_USERS).child(chat.receiverUid).child("lastMessage").setValue(chat.message)

                } else if (dataSnapshot.hasChild(roomType2)) {
                    Log.d(TAG, "sendMessageToFirebaseUser:$roomType2 exists")
                    databaseReference.child(ARG_CHAT_ROOMS).child(roomType2).child("messages").child(chat.timestamp.toString()).setValue(chat)
                    databaseReference.child(ARG_USERS).child(chat.senderUid).child("lastMessage").setValue(chat.message)
                    databaseReference.child(ARG_USERS).child(chat.receiverUid).child("lastMessage").setValue(chat.message)


                } else {
                    Log.d(TAG, "sendMessageToFirebaseUser: success")
                    databaseReference.child(ARG_CHAT_ROOMS).child(roomType1).child("messages").child(chat.timestamp.toString()).setValue(chat)
                    databaseReference.child(ARG_USERS).child(chat.senderUid).child("lastMessage").setValue(chat.message)
                    databaseReference.child(ARG_USERS).child(chat.receiverUid).child("lastMessage").setValue(chat.message)

                    getMessageFromFirebaseUser(chat.senderUid, chat.receiverUid)
                }
                // send push notification to the receiver
                sendPushNotificationToReceiver(chat.sender,
                        chat.message,
                        chat.senderUid,
                        SharedPrefUtil(context).getString(ARG_FIREBASE_TOKEN),
                        receiverFirebaseToken)

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
                    FirebaseDatabase.getInstance().getReference().child(ARG_CHAT_ROOMS).child(roomType1).child("messages")
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
                                    Log.d(TAG, "onChildRemoved " + p0?.key)
                                    mRemoveMessageListener.messageRemoved(p0?.key as String)

                                }

                            })
                } else if (dataSnapshot.hasChild(roomType2)) {
                    Log.d(TAG, "getMessageFromFirebaseUser: $roomType2 exists")
                    FirebaseDatabase.getInstance().getReference().child(ARG_CHAT_ROOMS).child(roomType2).child("messages")
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
                                    Log.d(TAG, "onChildRemoved")
                                    mRemoveMessageListener.messageRemoved(p0?.key as String)
                                }

                            })


                } else {
                    Log.d(TAG, "getMessageFromFirebaseUser: no such room available");
                }


            }

        })
    }

}