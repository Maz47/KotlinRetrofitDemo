package com.maz.org.kotlinretrofitdemo.data

data class Post(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String
)