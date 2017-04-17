package com.islavstan.firebasekotlinchat.ui.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.islavstan.firebasekotlinchat.R
import com.islavstan.firebasekotlinchat.core.users.get_all.GetUsersContract
import com.islavstan.firebasekotlinchat.core.users.get_all.GetUsersPresenter
import com.islavstan.firebasekotlinchat.models.User
import com.islavstan.firebasekotlinchat.ui.adapters.UsersRecyclerAdapter
import com.pawegio.kandroid.toast
import kotlinx.android.synthetic.main.fragment_users.*

class UsersFragment : Fragment(), GetUsersContract.View, SwipeRefreshLayout.OnRefreshListener {

    lateinit var presenter: GetUsersPresenter


    companion object {
        fun newInstance(): UsersFragment {
            val args = Bundle()
            val fragment = UsersFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val fragmentView = inflater?.inflate(R.layout.fragment_users, container, false)
        return fragmentView

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initItems()
    }

    private fun initItems() {
        presenter = GetUsersPresenter(this)
        presenter.getAllUsersFromFirebase()
        swipeRefreshLayout.post { swipeRefreshLayout.isRefreshing = true }
        swipeRefreshLayout.setOnRefreshListener(this)
    }


    override fun onRefresh() {
        presenter.getAllUsersFromFirebase()
    }


    override fun onGetAllUsersSuccess(users: List<User>) {
        swipeRefreshLayout.post { swipeRefreshLayout.isRefreshing = false }
        val recAdapter = UsersRecyclerAdapter(users)
        recycler.adapter = recAdapter
        recAdapter.notifyDataSetChanged()


    }

    override fun onGetAllUsersFailure(message: String) {
        swipeRefreshLayout.post { swipeRefreshLayout.isRefreshing = false }
        toast("Error " + message)
    }

    override fun onGetChatUsersSuccess(users: List<User>) {

    }

    override fun onGetChatUsersFailure(message: String) {

    }
}