package com.example.tazakhabar

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class Newsadapter(private val listener: on_clicking_news_tab):
    RecyclerView.Adapter<newsviewholder>() {

    private val items: ArrayList<Newsdata> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): newsviewholder {
        val inflaterobj =
            LayoutInflater.from(parent.context).inflate(R.layout.news_recycler_view, parent, false)

        val newsholder = newsviewholder(inflaterobj)

        inflaterobj.setOnClickListener {
        listener.onclicking_news(items[newsholder.absoluteAdapterPosition])
    }
        return newsholder
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: newsviewholder, position: Int) {
    val curr_news = items[position]
    holder.texttitle.text = curr_news.title
    holder.authorname.text = curr_news.author
    Glide.with(holder.itemView.context).load(curr_news.imgurl).into(holder.img)
    }



    @SuppressLint("NotifyDataSetChanged")
    fun refreshnews(refreshNews:ArrayList<Newsdata>){
        items.clear()
        items.addAll(refreshNews)

        notifyDataSetChanged()
    }

}

class newsviewholder(itemview: View): RecyclerView.ViewHolder(itemview){
    val texttitle:TextView = itemview.findViewById<TextView>(R.id.newsid)
    val img:ImageView = itemview.findViewById<ImageView>(R.id.newsimg)
    val authorname:TextView = itemview.findViewById<TextView>(R.id.author)
}

interface on_clicking_news_tab{
    fun onclicking_news(item: Newsdata)
}

