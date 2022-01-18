package com.example.hawkerpals

import android.app.AlertDialog
import android.content.Context
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hawkerpals.models.Post
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.card_layout.view.*
import kotlinx.android.synthetic.main.card_layout.view.item_detail
import kotlinx.android.synthetic.main.card_layout.view.item_image
import kotlinx.android.synthetic.main.card_layout.view.item_title
import kotlinx.android.synthetic.main.marketcard_layout.view.*
import org.w3c.dom.Text

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

//class MarketplaceRecyclerAdapter(val listingList:ArrayList<Listing>) : RecyclerView.Adapter<MarketplaceRecyclerAdapter.ViewHolder>() {
//
//    inner class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){
//        var image : ImageView
//        var textTitle : TextView
//        var textDes : TextView
//        var moreBtn : Button
//
//        init {
//            image = itemView.findViewById(R.id.item_image)
//            textTitle = itemView.findViewById(R.id.item_title)
//            textDes = itemView.findViewById(R.id.item_detail)
//            moreBtn = itemView.findViewById(R.id.details)
//        }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarketplaceRecyclerAdapter.ViewHolder {
//        val v = LayoutInflater.from(parent.context).inflate(R.layout.marketcard_layout, parent, false)
//        return ViewHolder(v)
//
//    }
//
//    override fun onBindViewHolder(holder: MarketplaceRecyclerAdapter.ViewHolder, position: Int) {
//        val currentList = listingList[position]
//        holder.textTitle.text = currentList.listing_title
//        holder.textDes.text = currentList.listing_description
//
//        val thumbnailImageView = holder?.image?.item_image
//        Picasso.get().load(currentList.listing_image).into(thumbnailImageView)
//
//        holder.moreBtn.setOnClickListener{v:View->
//            val alertDialog = AlertDialog.Builder(v.context)
//            alertDialog.setTitle(currentList.listing_title)
//            alertDialog.setMessage("Placeholder text info such as  such as quantity," +
//                    " price, intention(buy/sell), stall no., contact details, etc.")
//            alertDialog.setNeutralButton("Ok") { dialog, which ->            }
//            alertDialog.show()
//            //Toast.makeText(v.context,"Placeholder text for name of seller, store number, etc", Toast.LENGTH_SHORT).show()
//        }
//
//    }
//
//    override fun getItemCount(): Int {
//        return listingList.size
//    }
//}