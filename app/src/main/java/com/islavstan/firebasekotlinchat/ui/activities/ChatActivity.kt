package com.islavstan.firebasekotlinchat.ui.activities

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.FragmentTransaction
import android.support.v7.widget.Toolbar
import com.islavstan.firebasekotlinchat.R
import com.islavstan.firebasekotlinchat.ui.fragments.ChatFragment
import com.islavstan.firebasekotlinchat.ui.fragments.LoginFragment
import com.islavstan.firebasekotlinchat.ui.fragments.RegisterFragment
import com.islavstan.firebasekotlinchat.utils.ARG_FIREBASE_TOKEN
import com.islavstan.firebasekotlinchat.utils.ARG_RECEIVER
import com.islavstan.firebasekotlinchat.utils.ARG_RECEIVER_UID
import com.islavstan.firebasekotlinchat.utils.addFragment

class ChatActivity : AppCompatActivity() {
    private var toolbar: Toolbar? = null

    companion object {
        fun startActivity(activity: Activity, receiver: String, receiverUid: String, firebaseToken: String) {
            var intent = Intent(activity, ChatActivity::class.java)
            intent.putExtra(ARG_RECEIVER, receiver)
            intent.putExtra(ARG_RECEIVER_UID, receiverUid)
            intent.putExtra(ARG_FIREBASE_TOKEN, firebaseToken)
            activity.startActivity(intent)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        bindViews()
        initItems()


    }

    private fun initItems() {
        setSupportActionBar(toolbar)
        toolbar?.title = intent.extras.getString(ARG_RECEIVER)

        addFragment(supportFragmentManager, R.id.frame_layout_content_chat,
                ChatFragment.newInstance(intent.extras.getString(ARG_RECEIVER),
                        intent.extras.getString(ARG_RECEIVER_UID),
                        intent.extras.getString(ARG_FIREBASE_TOKEN)),
                ChatFragment::class.java.simpleName)

    }

    private fun bindViews() {
        toolbar = findViewById(R.id.toolbar) as Toolbar

    }
}
