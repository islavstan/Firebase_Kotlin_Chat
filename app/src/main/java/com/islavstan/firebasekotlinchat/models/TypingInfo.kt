package com.islavstan.firebasekotlinchat.models


class TypingInfo {
    var senderTyping: Boolean = false
    var receiverTyping: Boolean = false

    constructor(senderTyping: Boolean, receiverTyping: Boolean) {
        this.senderTyping = senderTyping
        this.receiverTyping = receiverTyping
    }

    override fun toString(): String {
        return "TypingInfo(senderTyping=$senderTyping, receiverTyping=$receiverTyping)"
    }


}