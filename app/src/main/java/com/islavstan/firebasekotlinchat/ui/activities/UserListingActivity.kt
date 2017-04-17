package com.islavstan.firebasekotlinchat.ui.activities

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import com.islavstan.firebasekotlinchat.R
import com.islavstan.firebasekotlinchat.ui.fragments.RegisterFragment
import com.islavstan.firebasekotlinchat.ui.fragments.UsersFragment
import com.islavstan.firebasekotlinchat.utils.addFragment

class UserListingActivity : AppCompatActivity() {
    private var toolbar: Toolbar? = null

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
        setContentView(R.layout.activity_user_listing)
        bindViews()
        initItems()


    }

    private fun initItems() {
        setSupportActionBar(toolbar)
        title = "Users"
        addFragment(supportFragmentManager, R.id.frame_layout_content_users, UsersFragment.newInstance(),
                UsersFragment::class.java.simpleName)

    }

    private fun bindViews() {
        toolbar = findViewById(R.id.toolbar) as Toolbar

    }
}

