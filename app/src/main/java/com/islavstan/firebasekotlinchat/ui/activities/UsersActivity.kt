package com.islavstan.firebasekotlinchat.ui.activities

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.SearchView
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import com.afollestad.materialdialogs.MaterialDialog
import com.google.firebase.auth.FirebaseAuth
import com.islavstan.firebasekotlinchat.R
import com.islavstan.firebasekotlinchat.bus.SearchUserAction
import com.islavstan.firebasekotlinchat.core.logout.LogoutContract
import com.islavstan.firebasekotlinchat.core.logout.LogoutPresenter
import com.islavstan.firebasekotlinchat.ui.fragments.UsersFragment
import com.islavstan.firebasekotlinchat.utils.addFragment
import com.pawegio.kandroid.onQueryChange
import com.pawegio.kandroid.toast
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder
import org.greenrobot.eventbus.EventBus

class UsersActivity : AppCompatActivity(), LogoutContract.View {

    private var toolbar: Toolbar? = null
    lateinit var presenter: LogoutPresenter
    private var logoutDialog: MaterialDialog? = null

    companion object {
        fun startActivity(activity: Activity) {
            val intent = Intent(activity, UsersActivity::class.java)
            activity.startActivity(intent)
        }

        fun startActivity(activity: Activity, flags: Int) {
            val intent = Intent(activity, UsersActivity::class.java)
            intent.flags = flags
            activity.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_listing)


        bindViews()
        initItems()
        SlidingRootNavBuilder(this)
                .withToolbarMenuToggle(toolbar)
                .withMenuLayout(R.layout.menu_left_drawer)
                .inject()

        val emailTV = findViewById(R.id.email) as TextView
        emailTV.text = FirebaseAuth.getInstance().currentUser?.email
    }

    private fun initItems() {
        setSupportActionBar(toolbar)
        title = "Users"
        addFragment(supportFragmentManager, R.id.frame_layout_content_users, UsersFragment.newInstance(),
                UsersFragment::class.java.simpleName)
        presenter = LogoutPresenter(this)

    }

    private fun bindViews() {
        toolbar = findViewById(R.id.toolbar) as Toolbar

    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_user_listing, menu)
        initSearchView(menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun initSearchView(menu: Menu) {
        var searchViewMenuItem = menu.findItem(R.id.action_search)
        var searchView = searchViewMenuItem.actionView as SearchView
        searchView.onQueryChange { query -> EventBus.getDefault().post(SearchUserAction(query)) }
        searchView.setOnCloseListener { EventBus.getDefault().post(SearchUserAction("")); false }


    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_logout -> logout()
        }
        return super.onOptionsItemSelected(item)
    }


    private fun logout() {
        logoutDialog = MaterialDialog.Builder(this)
                .title("Logout")
                .content("Are you sure you want to logout?")
                .positiveText("Yes")
                .negativeText("No")
                .positiveColorRes(R.color.black)
                .negativeColorRes(R.color.black)
                .onPositive { materialDialog, dialogAction ->
                    presenter.logout()
                    logoutDialog?.dismiss()
                }.onNegative { dialog, which -> logoutDialog?.dismiss() }
                .cancelListener { logoutDialog?.dismiss() }
                .show()

    }


    override fun onLogoutSucess(message: String) {
        toast(message)
        LoginActivity.startActivity(this, Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
    }

    override fun onLogoutFailure(message: String) {
        toast(message)
    }

}

