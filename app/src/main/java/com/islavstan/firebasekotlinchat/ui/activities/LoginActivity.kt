package com.islavstan.firebasekotlinchat.ui.activities

import android.app.Activity

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.FragmentTransaction
import android.support.v7.widget.Toolbar
import com.islavstan.firebasekotlinchat.R
import com.islavstan.firebasekotlinchat.ui.fragments.LoginFragment
import com.islavstan.firebasekotlinchat.utils.addFragment
import com.pawegio.kandroid.startActivity

class LoginActivity : AppCompatActivity() {
    private var toolbar: Toolbar ?= null

    companion object {
        fun startActivity(activity: Activity) {
            var intent = Intent(activity, LoginActivity::class.java)
            activity.startActivity(intent)
        }

        fun startActivity(activity: Activity, flags: Int) {
            var intent = Intent(activity, LoginActivity::class.java)
            intent.setFlags(flags)
            activity.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        bindViews()
        initItems()



    }

    private fun initItems() {
        setSupportActionBar(toolbar)
        addFragment(supportFragmentManager, R.id.frame_layout_content_login, LoginFragment.newInstance(),
                LoginFragment::class.java.simpleName)

    }

    private fun bindViews() {
        toolbar = findViewById(R.id.toolbar)as Toolbar

    }
}
