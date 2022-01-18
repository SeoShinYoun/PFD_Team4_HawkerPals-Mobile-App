package com.example.hawkerpals

data class User(
    var user_email: String? = null,
    var user_id: String? = null,
    var user_name: String? = null,
    var user_type: String? = null,
    var vacinated: Boolean? = null,
    var hawker_favourites: MutableMap<String, String> = HashMap()
)



//class User {
//    var user_email :String? = null
//    var user_id : String? = null
//    var user_name : String? = null
//    var user_type : String? = null
//    var vacinated: Boolean? = null
//    var hawker_favourites : List<String> = arrayListOf()
//
//    constructor(email: String?, id:String?, name:String?,type:String?, vax:Boolean?, fav:List<String>){
//        this.user_email = email
//        this.user_id = id
//        this.user_name = name
//        this.user_type = type
//        this.vacinated = vax
//        this.hawker_favourites = fav
//    }
//}
