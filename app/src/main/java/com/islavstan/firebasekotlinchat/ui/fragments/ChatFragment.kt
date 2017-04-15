package com.islavstan.firebasekotlinchat.ui.fragments

import android.support.v4.app.Fragment



class ChatFragment : Fragment() {
    companion object {
        fun newInstance(receiver: String, receiverUid: String, token: String): ChatFragment {
          return ChatFragment()
        }
    }
}