package com.example.hawkerpals

import android.app.PendingIntent.getActivity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.hawkerpals.bottomnavbartutorial.fragments.HomeFragment
import com.google.firebase.auth.AdditionalUserInfo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase

//class RecyclerAdapter(val context: Context,val hawkerList: ArrayList<Hawkers>) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.ViewHolder {
//        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_layout, parent, false)
//        return ViewHolder(v)
//    }
//
//
////class RecyclerAdapter(val context: Context) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>(){
////
////    private var titles = arrayOf("Telok Ayer Market", "Tiong Bahru Market", "Maxwell Food Centre", "Chinatown Complex",
////        "Golden Mile Centre", "ABC Brickworks", "Bedok Food Centre", "Tekka Food Centre")
////
////    private var details = arrayOf("18 Raffles Quay, 048582", "30 Seng Poh Rd, 168898","1 Kadayanallur St, 069184","335 Smith St, 050335",
////        "505 Beach Rd, 199583","6 Jalan Bukit Merah, 150006","1 Bedok Rd, 469572","665 Buffalo Rd, 210665")
////
////    private val images = intArrayOf(R.drawable.telok_ayer_market_ic, R.drawable.tiong_bahru_ic, R.drawable.maxwell_ic,R.drawable.chinatown_complex_ic,
////        R.drawable.goldenmile_ic,R.drawable.abc_brickworks_ic,R.drawable.bedok_food_ic,R.drawable.tekka_food_centre)
////
////    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.ViewHolder {
////        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_layout, parent, false)
////        return ViewHolder(v)
////    }
//    override fun onBindViewHolder(holder: RecyclerAdapter.ViewHolder, position: Int) {
//        val currentHawkers = hawkerList[position]
//        holder.itemDetail.text = currentHawkers.hawker_address
//        holder.itemTitle.text = currentHawkers.hawker_name
//        holder.itemView.setOnClickListener {
//                val intent = Intent(context,TrendingChat::class.java)
//                context.startActivity(intent)
//            }
//    }
//
//    override fun getItemCount(): Int {
//    return hawkerList.size
//    }
////    override fun onBindViewHolder(holder: RecyclerAdapter.ViewHolder, position: Int) {
////        holder.itemTitle.text = titles[position]
////        holder.itemDetail.text = details[position]
////        holder.itemImage.setImageResource(images[position])
////        holder.itemView.setOnClickListener {
////            val intent = Intent(context,TrendingChat::class.java)
////            intent.putExtra("GroupName",titles[position])
////            intent.putExtra("uid",FirebaseAuth.getInstance().currentUser?.uid)
////            context.startActivity(intent)
////        }
////
////    }
////
////    override fun getItemCount(): Int {
////        return titles.size
////    }
////
////    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
////        var itemImage: ImageView
////        var itemTitle: TextView
////        var itemDetail: TextView
////
////        init{
////            itemImage = itemView.findViewById(R.id.item_image)
////            itemTitle = itemView.findViewById(R.id.item_title)
////            itemDetail = itemView.findViewById(R.id.item_detail)
////
////            itemView.setOnClickListener{
////                val position: Int = absoluteAdapterPosition
////
////                Toast.makeText(itemView.context, "You clicked on ${titles[position]}", Toast.LENGTH_SHORT).show()
////            }
////        }
////
////    }
//
//    class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
//        var itemImage: ImageView
//        var itemTitle: TextView
//        var itemDetail: TextView
//
//        init {
//            itemImage = itemView.findViewById(R.id.item_image)
//            itemTitle = itemView.findViewById(R.id.item_title)
//            itemDetail = itemView.findViewById(R.id.item_detail)
//        }
//    }
//}


class RecyclerAdapter(val hawkerList:ArrayList<Hawkers>) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>(){

    private lateinit var user: User
    private lateinit var username:String
    private lateinit var mAuth: FirebaseAuth
    var db = FirebaseDatabase.getInstance("https://hawkerpals-de16f-default-rtdb.asia-southeast1.firebasedatabase.app/")
    val dbRef = db.getReference()
//    private var titles = arrayOf("Telok Ayer Market", "Tiong Bahru Market", "Maxwell Food Centre", "Chinatown Complex",
//        "Golden Mile Centre", "ABC Brickworks", "Bedok Food Centre", "Tekka Food Centre")
//
//    private var details = arrayOf("18 Raffles Quay, 048582", "30 Seng Poh Rd, 168898","1 Kadayanallur St, 069184","335 Smith St, 050335",
//        "505 Beach Rd, 199583","6 Jalan Bukit Merah, 150006","1 Bedok Rd, 469572","665 Buffalo Rd, 210665")

//    private val images = intArrayOf(R.drawable.telok_ayer_market_ic, R.drawable.tiong_bahru_ic, R.drawable.maxwell_ic,R.drawable.chinatown_complex_ic,
//        R.drawable.goldenmile_ic,R.drawable.abc_brickworks_ic,R.drawable.bedok_food_ic,R.drawable.tekka_food_centre)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_layout, parent, false)
//        val btn = v.findViewById<Button>(R.id.chat)
//        btn.setOnClickListener{
//            val intent = Intent (v.context, TrendingChat::class.java)
//            intent.putExtra("GroupName",hawkerList[])
//            intent.putExtra("uid",FirebaseAuth.getInstance().currentUser?.uid)
//            v.context?.startActivity(intent)
//
//        }
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerAdapter.ViewHolder, position: Int) {
        val currentList = hawkerList[position]
        holder.itemTitle.text = currentList.hawker_name
        holder.itemDetail.text = currentList.hawker_address
        dbRef.child("Users").addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                user = snapshot.child(FirebaseAuth.getInstance().currentUser!!.uid).getValue(User::class.java)!!
                holder.Chatbtn.setOnClickListener {
                    val intent = Intent(holder.itemView.context,TrendingChat::class.java)
                    intent.putExtra("GroupName",currentList.hawker_name)
                    intent.putExtra("username",user.user_name)
                    intent.putExtra("uid",FirebaseAuth.getInstance().currentUser?.uid)
                    holder.itemView.context.startActivity(intent)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
//        holder.Chatbtn.setOnClickListener {
//            val intent = Intent(holder.itemView.context,TrendingChat::class.java)
//            intent.putExtra("GroupName",currentList.hawker_name)
//            intent.putExtra("username",user.user_name)
//            intent.putExtra("uid",FirebaseAuth.getInstance().currentUser?.uid)
//            holder.itemView.context.startActivity(intent)
//        }
//        holder.itemImage.setImageResource(images[position])

    }

    override fun getItemCount(): Int {
        return hawkerList.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
//        var itemImage: ImageView
        var itemTitle: TextView
        var itemDetail: TextView
        var Chatbtn: Button

        init{
//            itemImage = itemView.findViewById(R.id.item_image)
            itemTitle = itemView.findViewById(R.id.item_title)
            itemDetail = itemView.findViewById(R.id.item_detail)
            Chatbtn = itemView.findViewById(R.id.chat)

        }

    }
//    private var titles = arrayOf("Telok Ayer Market", "Tiong Bahru Market", "Maxwell Food Centre", "Chinatown Complex",
//        "Golden Mile Centre", "ABC Brickworks", "Bedok Food Centre", "Tekka Food Centre")
//
//    private var details = arrayOf("18 Raffles Quay, 048582", "30 Seng Poh Rd, 168898","1 Kadayanallur St, 069184","335 Smith St, 050335",
//        "505 Beach Rd, 199583","6 Jalan Bukit Merah, 150006","1 Bedok Rd, 469572","665 Buffalo Rd, 210665")
//
//    private val images = intArrayOf(R.drawable.telok_ayer_market_ic, R.drawable.tiong_bahru_ic, R.drawable.maxwell_ic,R.drawable.chinatown_complex_ic,
//        R.drawable.goldenmile_ic,R.drawable.abc_brickworks_ic,R.drawable.bedok_food_ic,R.drawable.tekka_food_centre)
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.ViewHolder {
//        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_layout, parent, false)
//        return ViewHolder(v)
//    }
//
//    override fun onBindViewHolder(holder: RecyclerAdapter.ViewHolder, position: Int) {
//        holder.itemTitle.text = titles[position]
//        holder.itemDetail.text = details[position]
//        holder.itemImage.setImageResource(images[position])
//        holder.itemView.setOnClickListener {
//            val intent = Intent(context,TrendingChat::class.java)
//            intent.putExtra("GroupName",titles[position])
//            intent.putExtra("uid",FirebaseAuth.getInstance().currentUser?.uid)
//            context.startActivity(intent)
//        }
//
//    }
//
//    override fun getItemCount(): Int {
//        return titles.size
//    }
//
//    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
//        var itemImage: ImageView
//        var itemTitle: TextView
//        var itemDetail: TextView
//
//        init{
//            itemImage = itemView.findViewById(R.id.item_image)
//            itemTitle = itemView.findViewById(R.id.item_title)
//            itemDetail = itemView.findViewById(R.id.item_detail)
//
//            itemView.setOnClickListener{
//                val position: Int = absoluteAdapterPosition
//
//                Toast.makeText(itemView.context, "You clicked on ${titles[position]}", Toast.LENGTH_SHORT).show()
//            }
//        }
//
//    }


}