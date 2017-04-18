package com.islavstan.firebasekotlinchat.models


class User {
    var uid: String = ""
    var email: String = ""
    var firebaseToken: String = ""

    constructor()
    constructor(uid: String, email: String, firebaseToken: String) {
        this.uid = uid
        this.email = email
        this.firebaseToken = firebaseToken
    }


}