package com.islavstan.firebasekotlinchat.ui.activities

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class UserListingActivity : AppCompatActivity() {

    companion object {
        fun startActivity(activity: Activity) {
            var intent = Intent(activity, UserListingActivity::class.java)
            activity.startActivity(intent)
        }

        fun startActivity(activity: Activity, flags: Int) {
            var intent = Intent(activity, UserListingActivity::class.java)
            intent.flags = flags
            activity.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}
