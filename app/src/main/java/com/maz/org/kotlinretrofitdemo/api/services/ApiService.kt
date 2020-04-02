package com.maz.org.kotlinretrofitdemo.api.services

import com.maz.org.kotlinretrofitdemo.data.Post
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * This interface includes all api calls.
 *
 * @author Maz47
 *
 * created on 02 April 2020
 */
interface ApiService {

    /**
     * This method returns the post
     * for the given id from the remote
     * data source.
     */
    @GET("posts/{id}")
    fun getPost(@Path("id") id: Int): Call<Post>

}
