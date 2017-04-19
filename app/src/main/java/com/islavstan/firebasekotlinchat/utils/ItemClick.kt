package com.islavstan.firebasekotlinchat.utils

import android.support.v7.widget.RecyclerView


interface ItemClick {
    fun openChat(email: String, uid: String, token: String)
}