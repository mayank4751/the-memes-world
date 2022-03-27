package com.example.memesworld

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class MainActivity : AppCompatActivity() {
    private lateinit var memeimage : ImageView
    private lateinit var sharebutton : Button
    private lateinit var nextButton : Button
    private lateinit var progressBar: ProgressBar
    var currentimgurl: String? = null




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        memeimage =  findViewById(R.id.memeimage)
        sharebutton = findViewById(R.id.sharebutton)
        nextButton = findViewById(R.id.nextbutton)
        progressBar = findViewById(R.id.progressbar)
          LoadMeme()

    }
    private fun LoadMeme()
    {
        progressBar.visibility = View.VISIBLE
       
// ...

// Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)
        val url = "https://meme-api.herokuapp.com/gimme"

// Request a string response from the provided URL.
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url,null,
            Response.Listener { response ->
             currentimgurl = response.getString("url")
                Glide.with(this).load(currentimgurl).listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.visibility = View.GONE
                        return false
                    }
                }).into(memeimage)
            },
            Response.ErrorListener {
                Toast.makeText(this, "CHECK INTERNET", Toast.LENGTH_SHORT).show()
            })

// Add the request to the RequestQueue.
        queue.add(jsonObjectRequest)

    }

    fun nextMeme(view: View) {
        LoadMeme()
    }
    fun shareMeme(view: View) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT,"HEY CHECK THIS AMAZING MEME $currentimgurl")
        val chooser = Intent.createChooser(intent,"SHARE USING.......")
        startActivity(chooser)
    }
}