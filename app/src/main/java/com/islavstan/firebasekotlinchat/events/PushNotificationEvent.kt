package com.islavstan.firebasekotlinchat.events


class PushNotificationEvent {
    var title: String = ""
    var message: String = ""
    var userName: String = ""
    var uid: String = ""
    var fcmToken: String = ""

    constructor()

    constructor(title: String, message: String, userName: String, uid: String, fcmToken: String) {
        this.title = title
        this.message = message
        this.userName = userName
        this.uid = uid
        this.fcmToken = fcmToken
    }

}