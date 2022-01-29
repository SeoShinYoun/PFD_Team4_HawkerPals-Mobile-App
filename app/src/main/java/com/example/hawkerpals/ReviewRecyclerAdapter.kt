package com.example.hawkerpals

import android.content.Context
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hawkerpals.models.ReviewMarket
import kotlinx.android.synthetic.main.card_layout.view.item_detail
import kotlinx.android.synthetic.main.card_layout.view.item_title
import kotlinx.android.synthetic.main.reviewcard_layout.view.*

class ReviewRecyclerAdapter (val context: Context, val reviews:List<ReviewMarket>):
    RecyclerView.Adapter<ReviewRecyclerAdapter.ViewHolder>() {
    inner class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(reviews: ReviewMarket){
            itemView.item_detail.text = reviews.user?.username
            itemView.item_title.text = reviews.description
            itemView.timestamp.text = DateUtils.getRelativeTimeSpanString(reviews.creationtime)
            itemView.ratingBar2.rating = reviews.rating!!
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.reviewcard_layout, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(reviews[position])
    }

    override fun getItemCount(): Int {
        return reviews.size    }

}