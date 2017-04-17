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




class UsersRecyclerAdapter(val users:List<User>):RecyclerView.Adapter<UsersRecyclerAdapter.ViewHolder>() {


    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        var user = users[position]
        holder?.userEmail?.text = user.email
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

    }
}