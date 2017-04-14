package com.islavstan.firebasekotlinchat.ui.activities

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.FragmentTransaction
import android.support.v7.widget.Toolbar
import com.islavstan.firebasekotlinchat.R
import com.islavstan.firebasekotlinchat.ui.fragments.LoginFragment
import com.islavstan.firebasekotlinchat.ui.fragments.RegisterFragment
import com.islavstan.firebasekotlinchat.utils.addFragment

class RegisterActivity : AppCompatActivity() {
    private var toolbar: Toolbar?= null

    companion object {
        fun startActivity(activity: Activity) {
            var intent = Intent(activity, RegisterActivity::class.java)
            activity.startActivity(intent)
        }
    }



        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
            bindViews()
            initItems()



        }

    private fun initItems() {
        setSupportActionBar(toolbar)
        addFragment(supportFragmentManager, R.id.frame_layout_content_register, RegisterFragment.newInstance(),
                RegisterFragment::class.java.simpleName)

    }

    private fun bindViews() {
        toolbar = findViewById(R.id.toolbar)as Toolbar

    }
}
