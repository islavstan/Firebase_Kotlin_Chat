package com.islavstan.firebasekotlinchat.core.registration

import android.app.Activity
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.islavstan.firebasekotlinchat.utils.TAG


class RegisterInteractor(registrationListener: RegisterContract.onRegistrationListener): RegisterContract.Interactor {

    var mOnRegistrationListener: RegisterContract.onRegistrationListener = registrationListener

    override fun performFirebaseRegistration(activity: Activity, email: String, password: String) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity) { task ->
                    Log.d(TAG, "performFirebaseRegistration complete " + task.isSuccessful)

                    if (!task.isSuccessful) {
                        mOnRegistrationListener.onFailure(task.exception?.message.toString())
                    } else {
                        mOnRegistrationListener.onSuccess(task.result.user)
                    }

                }
    }
}