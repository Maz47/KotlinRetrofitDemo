package com.maz.org.kotlinretrofitdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.maz.org.kotlinretrofitdemo.api.ApiServiceBuilder
import com.maz.org.kotlinretrofitdemo.api.services.ApiService
import com.maz.org.kotlinretrofitdemo.data.Post
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * This project includes a demo of the Retrofit Library.
 * This Library is used to fetch data from an remote API.
 * For this project https://jsonplaceholder.typicode.com/
 * is used for the remote DataSource.
 *
 * @author Maz47
 *
 * created on 02 April 2020
 */
class MainActivity : AppCompatActivity() {

    /**
     * This variable represents a single instance
     * of the ApiService. This instance is NOT
     * lifecycle aware. So it gets reinitialized
     * at every lifecycle event (rotation, ...).
     */
    private lateinit var apiService: ApiService

    /**
     * This method is the main entry point. It
     * initializes the apiService and sets an
     * OnClickListener to the fetchPostButton.
     *
     * @param savedInstanceState
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        apiService = ApiServiceBuilder.buildService(ApiService::class.java)
        fetchPostButton.setOnClickListener { fetchPost() }
    }

    /**
     * This method gets called when the user
     * clicks the fetchPostButton. It reads
     * the value of the postNumberEditText and
     * validates it. If the value is not empty
     * getPost(value.toInt()) gets called.
     * Otherwise a toast with an error is shown.
     */
    private fun fetchPost() {
        val postID = postNumber.text.toString()
        if (postID.isNotEmpty()) getPost(postID.toInt())
        if (postID.isEmpty()) Toast.makeText(this, getString(R.string.no_post_id), Toast.LENGTH_SHORT).show()
    }

    /**
     * This method gets the post for the
     * given post id from the remote API.
     *
     * @param id
     */
    private fun getPost(id: Int) {
        apiService.getPost(id).enqueue(object : Callback<Post> {
            override fun onFailure(call: Call<Post>, t: Throwable) = handleError(t)
            override fun onResponse(call: Call<Post>, response: Response<Post>) = handleSuccess(response.body())
        })
    }

    /**
     * This method gets called if the API-Call
     * is successful. If the post from the
     * response is not null, updatePostView()
     * gets called. Otherwise a Toast shows
     * and resetPostView() gets called.
     *
     * @param post
     */
    private fun handleSuccess(post: Post?) {
        if (post != null) updatePostView(post)
        if (post == null) {
            Toast.makeText(this, getString(R.string.invalid_post_id), Toast.LENGTH_LONG).show()
            resetPostView()
        }
    }

    /**
     * This method gets called if an Error occurs
     * while making the API-Call. It shows the
     * Error with a Toast and calls resetPostView()
     *
     * @param throwable
     */
    private fun handleError(throwable: Throwable) {
        Toast.makeText(this, getString(R.string.request_error) + throwable.message, Toast.LENGTH_LONG).show()
        resetPostView()
    }

    /**
     * This method updates the post view with
     * the current post values.
     */
    private fun updatePostView(post: Post) {
        postTitle.text = post.title
        postDescription.text = post.body
    }

    /**
     * This method resets the post view to the
     * default values.
     */
    private fun resetPostView() {
        postTitle.text = ""
        postDescription.text = ""
    }

}
