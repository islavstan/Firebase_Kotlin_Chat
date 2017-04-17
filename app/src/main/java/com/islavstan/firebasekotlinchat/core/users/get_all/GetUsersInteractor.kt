package com.islavstan.firebasekotlinchat.core.users.get_all

import android.text.TextUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.islavstan.firebasekotlinchat.models.User
import com.islavstan.firebasekotlinchat.utils.ARG_USERS
import java.util.*


class GetUsersInteractor (val listener:GetUsersContract.OnGetAllUsersListener) :GetUsersContract.Interactor, ValueEventListener {


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
                if (user.uid != FirebaseAuth.getInstance().currentUser?.uid) {
                    users.add(user)
                }


            }
            listener.onGetAllUsersSuccess(users)

        }
    }


    override fun getAllUsersFromFirebase() {
        FirebaseDatabase.getInstance().reference.child(ARG_USERS).addListenerForSingleValueEvent(this)
    }


    override fun getChatUsersFromFirebase() {

    }
}