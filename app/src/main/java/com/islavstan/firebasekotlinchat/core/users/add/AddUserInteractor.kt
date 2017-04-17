package com.islavstan.firebasekotlinchat.core.users.add

import android.content.Context
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.islavstan.firebasekotlinchat.models.User
import com.islavstan.firebasekotlinchat.utils.ARG_FIREBASE_TOKEN
import com.islavstan.firebasekotlinchat.utils.ARG_USERS
import com.islavstan.firebasekotlinchat.utils.SharedPrefUtil


class AddUserInteractor(val listener: AddUserContract.OnUserDatabaseListener):AddUserContract.Interactor {


    override fun addUserToDatabase(context: Context, firebaseUser: FirebaseUser) {
        val databaseRef: DatabaseReference = FirebaseDatabase.getInstance().getReference()
        val user: User = User(firebaseUser.uid, firebaseUser.email!!, SharedPrefUtil(context).getString(ARG_FIREBASE_TOKEN))
        databaseRef.child(ARG_USERS)
                .child(firebaseUser.uid)
                .setValue(user)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful)
                        listener.onSuccess("user successfully added")
                    else listener.onFailure("user unable to add")
                }
    }
}