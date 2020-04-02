package com.maz.org.kotlinretrofitdemo.data

/**
 * This is the data class for the post.
 *
 * @author Maz47
 *
 * created on 02 April 2020
 *
 * @param userId
 * @param id
 * @param title
 * @param body
 */
data class Post(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String
)