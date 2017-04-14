package com.islavstan.firebasekotlinchat.ui.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.islavstan.firebasekotlinchat.R
import com.islavstan.firebasekotlinchat.utils.TAG

class SplashActivity : AppCompatActivity() {


    private val SPLASH_TIME: Int = 2000;
    private var mHandler: Handler? = null
    private var mRunnable: Runnable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        mHandler = Handler()
        mRunnable = Runnable {
            Log.d(TAG, "Runnable")
            if (FirebaseAuth.getInstance().currentUser != null) {
                UserListingActivity.startActivity(this)

            } else {
                LoginActivity.startActivity(this)

            }
            finish()
        }
    }
}
