package com.islavstan.firebasekotlinchat.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.islavstan.firebasekotlinchat.R
import com.islavstan.firebasekotlinchat.bus.SearchUserAction
import com.islavstan.firebasekotlinchat.core.users.get_all.GetUsersContract
import com.islavstan.firebasekotlinchat.core.users.get_all.GetUsersPresenter
import com.islavstan.firebasekotlinchat.models.Chat
import com.islavstan.firebasekotlinchat.models.User
import com.islavstan.firebasekotlinchat.ui.activities.ChatActivity
import com.islavstan.firebasekotlinchat.ui.adapters.UsersRecyclerAdapter
import com.islavstan.firebasekotlinchat.utils.ItemClick
import com.islavstan.firebasekotlinchat.utils.TAG
import com.pawegio.kandroid.toast
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.util.*
import org.greenrobot.eventbus.ThreadMode




class UsersFragment : Fragment(), GetUsersContract.View, SwipeRefreshLayout.OnRefreshListener, ItemClick {


    lateinit var presenter: GetUsersPresenter
    var swipeRefreshLayout: SwipeRefreshLayout? = null
    var recycler: RecyclerView? = null
    val users: ArrayList<User> = ArrayList()


    companion object {
        fun newInstance(): UsersFragment {
            val args = Bundle()
            val fragment = UsersFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EventBus.getDefault().register(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val fragmentView = inflater?.inflate(R.layout.fragment_users, container, false) as View
        bindViews(fragmentView)
        return fragmentView

    }


    fun bindViews(fragmentView: View) {
        swipeRefreshLayout = fragmentView.findViewById(R.id.swipeRefreshLayout) as SwipeRefreshLayout
        recycler = fragmentView.findViewById(R.id.recycler) as RecyclerView

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initItems()
    }

    private fun initItems() {
        presenter = GetUsersPresenter(this)
        presenter.getAllUsersFromFirebase()
        swipeRefreshLayout?.setOnRefreshListener(this)
        swipeRefreshLayout?.post { swipeRefreshLayout?.isRefreshing = true }
    }


    override fun openChat(email: String, uid: String, token: String) {
        ChatActivity.startActivity(activity, email, uid, token)
    }


    override fun onRefresh() {
        presenter.getAllUsersFromFirebase()
    }


    override fun onGetAllUsersSuccess(users: List<User>) {
        swipeRefreshLayout?.post { swipeRefreshLayout?.isRefreshing = false }
        val recAdapter = UsersRecyclerAdapter(users, this)
        recycler?.adapter = recAdapter
        recAdapter.notifyDataSetChanged()
        this.users.addAll(users)


    }

    override fun onGetAllUsersFailure(message: String) {
        swipeRefreshLayout?.post { swipeRefreshLayout?.isRefreshing = false }
        toast("Error " + message)
    }

    override fun onGetChatUsersSuccess(users: List<User>) {

    }

    override fun onGetChatUsersFailure(message: String) {

    }


    @Subscribe
    fun onSearchEvent(event: SearchUserAction) {
        val query = event.query
        search(query)
    }


    fun search(query: String) {
        if (query == "") {
            recycler?.adapter = UsersRecyclerAdapter(users, this)
        } else {
            val searchResults = users.filter { it.email.startsWith(query, ignoreCase = true) }
            recycler?.adapter = UsersRecyclerAdapter(searchResults, this)
        }


    }


}