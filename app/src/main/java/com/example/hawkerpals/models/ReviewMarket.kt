package com.example.hawkerpals.models

data class ReviewMarket(
    var description: String = "",
    var rating: Float? = null,
    var creationtime: Long = 0,
    var marketname: String = "",
    var user:User? = null
)