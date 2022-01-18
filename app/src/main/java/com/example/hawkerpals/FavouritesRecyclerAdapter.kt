package com.example.hawkerpals

import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.snapshot.Index
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.card_layout.view.*
import org.w3c.dom.Text
import java.lang.IndexOutOfBoundsException

class FavouritesRecyclerAdapter(val hawkerList:ArrayList<Hawkers>) : RecyclerView.Adapter<FavouritesRecyclerAdapter.ViewHolder>() {

    private lateinit var user: User
    private lateinit var username:String
    private lateinit var mAuth: FirebaseAuth
    var db = FirebaseDatabase.getInstance("https://hawkerpals-de16f-default-rtdb.asia-southeast1.firebasedatabase.app/")
    val dbRef = db.getReference()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouritesRecyclerAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.favourites_card_layout, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentList = hawkerList[position]
        holder.itemTitle.text = currentList.hawker_name
        holder.itemDetail.text = currentList.hawker_address

        val thumbnailImageView = holder?.itemImage?.item_image
        Picasso.get().load(currentList.hawker_image).into(thumbnailImageView)

        dbRef.child("Users").addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                user = snapshot.child(FirebaseAuth.getInstance().currentUser!!.uid).getValue(User::class.java)!!
                holder.Chatbtn.setOnClickListener {
                    val intent = Intent(holder.itemView.context,TrendingChat::class.java)
                    intent.putExtra("GroupName",currentList.hawker_name)
                    intent.putExtra("username",user.user_name)
                    intent.putExtra("uid", FirebaseAuth.getInstance().currentUser?.uid)
                    holder.itemView.context.startActivity(intent)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        holder.Marketbtn.setOnClickListener{
            val intent = Intent(holder.itemView.context,MarketplaceActivity::class.java)
            intent.putExtra("GroupName", currentList.hawker_name)
            holder.itemView.context.startActivity(intent)
        }

        //removes hawker center from favourites if button clicked from favourites fragment
        holder.unFavbtn.setOnClickListener{
            holder.unFavbtn.isEnabled = false
            holder.unFavbtn.isClickable = false
            holder.unFavbtn.setImageResource(R.drawable.ic_favorite)

            dbRef.child("Users").get().addOnSuccessListener {
                user = it.child(FirebaseAuth.getInstance().currentUser!!.uid).getValue(User::class.java)!!
                //Log.e(TAG, "Want to remove: ${currentList.hawker_name}, values in list are ${user.hawker_favourites.values}")
                if(!user.hawker_favourites.isNullOrEmpty() && user.hawker_favourites.values.contains(currentList.hawker_name)) {
                    for(i in 1..user.hawker_favourites.size){
                        try {
                            var fav = user.hawker_favourites.keys.elementAt(i-1)
                            if(user.hawker_favourites.getValue(fav)==currentList.hawker_name){
                                user.hawker_favourites.remove(fav)
                                //Log.e(TAG, "Removing ${currentList.hawker_name}, ${fav}")
                                dbRef.child("Users").child(FirebaseAuth.getInstance().currentUser!!.uid).child("hawker_favourites").setValue(user.hawker_favourites)
                            }
                        }catch (e: IndexOutOfBoundsException){
                            //Log.e(TAG,"IndexOutOfBounds, Iteration:${i-1},List is: ${user.hawker_favourites}")
                        }
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return hawkerList.size
    }

    fun update(hList: ArrayList<Hawkers>){
        FavouritesRecyclerAdapter(hList).notifyDataSetChanged()
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var itemImage: ImageView
        var itemTitle: TextView
        var itemDetail: TextView
        var Chatbtn: Button
        var Marketbtn: Button
        var unFavbtn: ImageButton

        init{
            itemImage = itemView.findViewById(R.id.item_image)
            itemTitle = itemView.findViewById(R.id.item_title)
            itemDetail = itemView.findViewById(R.id.item_detail)
            Chatbtn = itemView.findViewById(R.id.chat)
            Marketbtn = itemView.findViewById(R.id.marketplace)
            unFavbtn = itemView.findViewById(R.id.unfavButton)
        }

    }


}
