package com.example.hawkerpals

class ThreadMessage{
    var sentUserID : String? = null
    var groupName : String? = null
//    var sentUserName : String? = null
    var messageContent : String? = null

    constructor(){}

    constructor(message: String?, sentUserID:String?, userName:String?,grpName:String?){

        this.messageContent = message
//        this.sentUserName = userName
        this.sentUserID = sentUserID
        this.groupName = grpName

    }

}