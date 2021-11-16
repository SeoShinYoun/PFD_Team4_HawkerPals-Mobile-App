package com.example.hawkerpals

class ThreadMessage{
    var sentUserID : String? = null
    var threadName : String? = null
    var sentUserName : String? = null
    var messageContent : String? = null

    constructor(){}

    constructor(message: String?, sentUserID:String?){
        this.messageContent = message
        this.sentUserID = sentUserID

    }

}