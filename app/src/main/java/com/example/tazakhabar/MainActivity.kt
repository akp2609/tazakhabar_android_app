package com.example.tazakhabar

import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), on_clicking_news_tab {

    private lateinit var mAdapter: Newsadapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerviewtag.layoutManager = LinearLayoutManager(this)
        fetchdata()
        mAdapter = Newsadapter( this)
        recyclerviewtag.adapter = mAdapter


    }

   /* private fun fetchdata(){
        val url = "https://newsapi.org/v2/everything?q=tesla&from=2022-01-07&sortBy=publishedAt&apiKey=2922ba98e8644afeb0632a601b6e6e56"
        val JsonObjectRequest = JsonObjectRequest( Request.Method.GET,
            url,
            null,
    Response.Listener {
                      val newsjsonarr = it.getJSONArray("articles")
                      val newsArray = ArrayList<Newsdata>()

        for(i in 0 until newsjsonarr.length()){
            val newsobj = newsjsonarr.getJSONObject(i)
            val news = Newsdata(
                newsobj.getString("title"),
                newsobj.getString("author:"),
                newsobj.getString("url:"),
                newsobj.getString("urlToImage")
            )
            newsArray.add(news)

        }
        mAdapter.refreshnews(newsArray)
        //Log.i("TAG", "response is $it");

    }, {
                Toast.makeText(this, "something went wrong ", Toast.LENGTH_SHORT).show()
            })



        Singleton.getInstance(this).addToRequestQueue(JsonObjectRequest)
    }*/


    private fun fetchdata() {
        val url =
            "https://newsapi.org/v2/top-headlines?sources=techcrunch"
//            "https://newsapi.org/v2/top-headlines?sources=techcrunch&apiKey=1ebd6aa3884245d292e3e4b3dc7ba411"
        val objectRequest = object : JsonObjectRequest(
            Method.GET, url, null,
            Response.Listener { response ->
                val articlesJsonArray = response.getJSONArray("articles")
                val newsArray = ArrayList<Newsdata>()

                for (i in 0 until articlesJsonArray.length()) {
                    val newsobj = articlesJsonArray.getJSONObject(i)
                    val news = Newsdata(
                        newsobj.getString("title"),
                        newsobj.getString("author"),
                        newsobj.getString("url"),
                        newsobj.getString("urlToImage")
                    )
                    newsArray.add(news)

                }
                mAdapter.refreshnews(newsArray)

            }, Response.ErrorListener {
                Toast.makeText(this@MainActivity, "something went wrong ", Toast.LENGTH_SHORT)
                    .show()
            }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["User-Agent"] = "Mozilla/5.0"
                headers["Authorization"] = "1ebd6aa3884245d292e3e4b3dc7ba411"
                return headers
            }
        }


        Singleton.getInstance(this).addToRequestQueue(objectRequest)
    }


    override fun onclicking_news(item: Newsdata) {

        val builder =  CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(this, Uri.parse(item.url))

    }
}