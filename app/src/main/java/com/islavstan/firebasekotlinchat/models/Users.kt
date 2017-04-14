package com.islavstan.firebasekotlinchat.models




class Users {
    var emailId: String = ""
    var lastMessage: String = ""
    var notifCount: Int = 0

    constructor()

    constructor(emailId: String, lastMessage: String, notifCount: Int) {
        this.emailId = emailId
        this.lastMessage = lastMessage
        this.notifCount = notifCount
    }

}