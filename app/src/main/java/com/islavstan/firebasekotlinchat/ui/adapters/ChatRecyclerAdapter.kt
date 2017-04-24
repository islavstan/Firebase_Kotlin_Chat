package com.islavstan.firebasekotlinchat.ui.adapters

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.islavstan.firebasekotlinchat.R
import com.islavstan.firebasekotlinchat.models.Chat
import com.islavstan.firebasekotlinchat.models.User
import com.islavstan.firebasekotlinchat.utils.TAG
import com.squareup.picasso.Picasso
import java.util.*


class ChatRecyclerAdapter( val messages:MutableList<Chat>):RecyclerView.Adapter<ChatRecyclerAdapter.ViewHolder>() {
    private val VIEW_TYPE_ME = 1
    private val VIEW_TYPE_OTHER = 2
    private val VIEW_TYPE_MY_IMAGE = 3
    private val VIEW_TYPE_OTHER_IMAGE = 4

    override fun getItemCount(): Int {
        return messages.size
    }

    fun addChat(chat: Chat) {
        messages.add(chat)
        notifyItemInserted(messages.size - 1)
    }

    override fun getItemViewType(position: Int): Int {
        when (messages[position].mesType) {
            1 -> return VIEW_TYPE_ME
            2 -> return VIEW_TYPE_OTHER
            3 -> return VIEW_TYPE_MY_IMAGE
            4 -> return VIEW_TYPE_OTHER_IMAGE
        }
        return 0
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val chat = messages[position]
        val mesType = chat.mesType
        if (mesType == 1 || mesType == 2)
            holder?.message?.text = chat.message
        if (mesType == 3 || mesType == 4)
            Picasso.with(holder?.image?.context).load(chat.message).placeholder(R.drawable.loading).into(holder?.image)

    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        when (viewType) {
            VIEW_TYPE_ME -> return ViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.item_chat_mine, parent, false), viewType)
            VIEW_TYPE_OTHER -> return ViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.item_chat_other, parent, false), viewType)
            VIEW_TYPE_MY_IMAGE -> return ViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.item_chat_mine_image, parent, false), viewType)
            VIEW_TYPE_OTHER_IMAGE -> return ViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.item_chat_other_image, parent, false), viewType)
        }
        return null!!
    }


    class ViewHolder(itemView: View, viewType: Int) : RecyclerView.ViewHolder(itemView) {
        var userPhoto: ImageView? = null
        var message: TextView? = null
        var image: ImageView? = null

        init {
            if (viewType == 1 || viewType == 2) {
                userPhoto = itemView.findViewById(R.id.user_photo) as ImageView
                message = itemView.findViewById(R.id.messageTV) as TextView
            } else if (viewType == 3 || viewType == 4) {
                userPhoto = itemView.findViewById(R.id.user_photo) as ImageView
                image = itemView.findViewById(R.id.image) as ImageView


            }

        }

    }
}