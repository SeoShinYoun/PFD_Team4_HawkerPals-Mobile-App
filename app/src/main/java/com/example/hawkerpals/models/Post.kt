package com.example.hawkerpals.models


data class Post(
    var description: String = "",
    var imageurl: String = "",
    var creationtime: Long = 0,
    var marketname: String = "",
    var user:User? = null
)