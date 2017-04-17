package com.islavstan.firebasekotlinchat.fcm

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService
import com.islavstan.firebasekotlinchat.utils.ARG_FIREBASE_TOKEN
import com.islavstan.firebasekotlinchat.utils.ARG_USERS
import com.islavstan.firebasekotlinchat.utils.SharedPrefUtil
import com.islavstan.firebasekotlinchat.utils.TAG


class MyFirebaseInstanceIDService : FirebaseInstanceIdService() {

    override fun onTokenRefresh() {
        var refreshToken: String? = FirebaseInstanceId.getInstance().getToken()
        Log.d(TAG, "refreshToken = " + refreshToken)
        sendRegistrationToServer(refreshToken)
    }

    private fun sendRegistrationToServer(refreshToken: String?) {
        SharedPrefUtil(applicationContext).saveString(ARG_FIREBASE_TOKEN, refreshToken)
        if (FirebaseAuth.getInstance().currentUser != null) {
            FirebaseDatabase.getInstance()
                    .getReference()
                    .child(ARG_USERS)
                    .child(FirebaseAuth.getInstance().getCurrentUser()?.uid)
                    .child(ARG_FIREBASE_TOKEN)
                    .setValue(refreshToken)
        }
    }
}