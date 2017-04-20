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
        }
        return 0
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val chat = messages[position]
        val mesType = chat.mesType
        holder?.message?.text = chat.message



    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        if (viewType == VIEW_TYPE_ME) {
            return ViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.item_chat_mine, parent, false), viewType)
        } else return ViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.item_chat_other, parent, false), viewType)
    }


    class ViewHolder(itemView: View, viewType: Int) : RecyclerView.ViewHolder(itemView) {
        var userPhoto: ImageView? = null
        var message: TextView? = null

        init {
            if(viewType == 1 || viewType == 2) {
                userPhoto = itemView.findViewById(R.id.user_photo) as ImageView
                message = itemView.findViewById(R.id.messageTV) as TextView
            }

        }

    }
}