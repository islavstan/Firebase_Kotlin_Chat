package com.islavstan.firebasekotlinchat.core.login

import android.app.Activity
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.islavstan.firebasekotlinchat.utils.ARG_FIREBASE_TOKEN
import com.islavstan.firebasekotlinchat.utils.ARG_USERS
import com.islavstan.firebasekotlinchat.utils.SharedPrefUtil
import com.islavstan.firebasekotlinchat.utils.TAG


class LoginInteractor(val listener: LoginContract.onLoginListener): LoginContract.Interactor {


    override fun performFirebaseLogin(activity: Activity, email: String, password: String) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity) { task ->
                    Log.d(TAG, "performFirebaseLogin:onComplete:" + task.isSuccessful)
                    if (task.isSuccessful) {
                        listener.onSuccess(task.result.toString())
                        updateFirebaseToken(task.result.user.uid,
                                SharedPrefUtil(activity.applicationContext).getString(ARG_FIREBASE_TOKEN))
                    } else {
                        listener.onFailure(task.exception?.message.toString())
                    }
                }

    }

    private fun updateFirebaseToken(uid: String, token: String) {
        FirebaseDatabase.getInstance().reference.child(ARG_USERS).child(uid).child(ARG_FIREBASE_TOKEN).setValue(token)
    }
}