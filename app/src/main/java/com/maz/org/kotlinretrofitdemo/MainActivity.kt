package com.maz.org.kotlinretrofitdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.maz.org.kotlinretrofitdemo.api.ApiServiceBuilder
import com.maz.org.kotlinretrofitdemo.api.services.ApiService
import com.maz.org.kotlinretrofitdemo.data.Post
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initUI()
    }

    private fun initUI() {
        val apiService: ApiService = ApiServiceBuilder.buildService(ApiService::class.java)
        fetchPost.setOnClickListener {
            val postID = postNumber.text.toString()
            if (postID.isEmpty()) Toast.makeText(this, getString(R.string.no_post_id), Toast.LENGTH_SHORT).show()
            if (postID.isNotEmpty()) makeApiCall(apiService, postID)
        }
    }

    private fun makeApiCall(apiService: ApiService, postID: String) {
        apiService.getPost(postID.toInt()).enqueue(object : Callback<Post> {
            override fun onFailure(call: Call<Post>, t: Throwable) = handleError(t)
            override fun onResponse(call: Call<Post>, response: Response<Post>) = handleSuccess(response.body())
        })
    }

    private fun handleSuccess(post: Post?) {
        if (post != null) {
            postTitle.text = post.title
            postDescription.text = post.body
        }
        if (post == null) {
            Toast.makeText(this, getString(R.string.invalid_post_id), Toast.LENGTH_LONG).show()
            resetPostView()
        }
    }

    private fun handleError(t: Throwable) {
        Toast.makeText(this, getString(R.string.request_error) + t.message, Toast.LENGTH_LONG).show()
        resetPostView()
    }

    private fun resetPostView() {
        postTitle.text = ""
        postDescription.text = ""
    }

}
