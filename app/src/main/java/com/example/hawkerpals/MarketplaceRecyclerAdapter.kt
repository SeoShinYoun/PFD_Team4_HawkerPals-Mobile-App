package com.example.hawkerpals

import android.content.Context
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hawkerpals.models.Post
import kotlinx.android.synthetic.main.card_layout.view.item_detail
import kotlinx.android.synthetic.main.card_layout.view.item_image
import kotlinx.android.synthetic.main.card_layout.view.item_title
import kotlinx.android.synthetic.main.marketcard_layout.view.*

class MarketplaceRecyclerAdapter(val context: Context, val posts:List<Post>) :
    RecyclerView.Adapter<MarketplaceRecyclerAdapter.ViewHolder>() {

    inner class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(post:Post){
            itemView.item_detail.text = post.user?.username
            itemView.item_title.text = post.description
            Glide.with(context).load(post.imageurl).into(itemView.item_image)
            itemView.timestamp.text = DateUtils.getRelativeTimeSpanString(post.creationtime)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.marketcard_layout, parent, false)
        return ViewHolder(v)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       holder.bind(posts[position])

    }

    override fun getItemCount(): Int {
        return posts.size
    }
}

