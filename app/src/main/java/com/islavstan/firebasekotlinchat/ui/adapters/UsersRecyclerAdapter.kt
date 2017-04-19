package com.islavstan.firebasekotlinchat.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.islavstan.firebasekotlinchat.R
import com.islavstan.firebasekotlinchat.models.User
import com.islavstan.firebasekotlinchat.models.Users
import java.util.*
import android.view.LayoutInflater
import android.widget.RelativeLayout
import com.islavstan.firebasekotlinchat.utils.ItemClick


class UsersRecyclerAdapter(val users:List<User>, val itemClick: ItemClick):RecyclerView.Adapter<UsersRecyclerAdapter.ViewHolder>() {


    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val user = users[position]
        holder?.userEmail?.text = user.email
        holder?.item?.setOnClickListener({ itemClick.openChat(user.email, user.uid, user.firebaseToken) })

    }


    override fun getItemCount(): Int {
        return users.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_all_user_listining, parent, false)
        return ViewHolder(view)
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var userPhoto: ImageView = itemView.findViewById(R.id.user_photo) as ImageView
        var userEmail: TextView = itemView.findViewById(R.id.user_email) as TextView
        var item: RelativeLayout = itemView.findViewById(R.id.item) as RelativeLayout

    }
}