package com.islavstan.firebasekotlinchat.models


class Chat {
    var sender: String = ""
    var receiver: String = ""
    var senderUid: String = ""
    var receiverUid: String = ""
    var message: String = ""
    var timestamp: Long = 0
    var mesType: Int = 0

    constructor()

    constructor(sender: String, receiver: String, senderUid: String, receiverUid: String, message: String, timestamp: Long) {
        this.sender = sender
        this.receiver = receiver
        this.senderUid = senderUid
        this.receiverUid = receiverUid
        this.message = message
        this.timestamp = timestamp
    }

    constructor(sender: String, receiver: String, senderUid: String, receiverUid: String, message: String, timestamp: Long, mesType: Int) {
        this.sender = sender
        this.receiver = receiver
        this.senderUid = senderUid
        this.receiverUid = receiverUid
        this.message = message
        this.timestamp = timestamp
        this.mesType = mesType
    }


}