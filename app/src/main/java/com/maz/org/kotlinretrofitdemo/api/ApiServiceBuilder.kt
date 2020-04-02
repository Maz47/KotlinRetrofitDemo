package com.maz.org.kotlinretrofitdemo.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * This object is a singleton and
 * initializes a Retrofit instance.
 *
 * @author Maz47
 *
 * created on 02 April 2020
 */
object ApiServiceBuilder {

    private const val BASE_URL = "https://jsonplaceholder.typicode.com/"

    /**
     * This object represents the retrofit
     * instance. It gets initialized when
     * the object gets called for the first
     * time (by lazy).
     */
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    /**
     * This method represents a generic way
     * of initializing any Service (e.g. ApiService).
     */
    fun <T> buildService(clazz: Class<T>): T = retrofit.create(clazz)

}