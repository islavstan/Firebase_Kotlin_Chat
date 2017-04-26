package com.islavstan.firebasekotlinchat.core.users.get_all

import android.text.TextUtils
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.islavstan.firebasekotlinchat.models.Chat
import com.islavstan.firebasekotlinchat.models.User
import com.islavstan.firebasekotlinchat.utils.ARG_CHAT_ROOMS
import com.islavstan.firebasekotlinchat.utils.ARG_USERS
import com.islavstan.firebasekotlinchat.utils.TAG
import java.util.*


class GetUsersInteractor (val listener:GetUsersContract.OnGetAllUsersListener) :GetUsersContract.Interactor{





    override fun getAllUsersFromFirebase(senderUid: String) {
        FirebaseDatabase.getInstance().reference.child(ARG_USERS).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(dataSnapshot: DatabaseError?) {
                listener.onGetAllUsersFailure(dataSnapshot!!.message)
            }

            override fun onDataChange(dataSnapshot: DataSnapshot?) {
                val dataSnapshots = dataSnapshot?.children?.iterator()
                val users: ArrayList<User> = ArrayList()
                if (dataSnapshots != null) {
                    while (dataSnapshots.hasNext()) {
                        val dataSnapshotChild = dataSnapshots.next()
                        val user = dataSnapshotChild.getValue(User::class.java)
                        if (user.uid != senderUid) {
                              users.add(user)

                        }


                    }
                    Log.d(TAG, "size = "+ users.size)
                   listener.onGetAllUsersSuccess(users)

                }
            }
        })
    }


    fun getLastMessageFromFirebase( user: User, users: ArrayList<User>,senderUid: String) {

    }


    override fun getChatUsersFromFirebase() {

    }
}