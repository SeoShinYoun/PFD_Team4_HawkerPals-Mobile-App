package com.example.hawkerpals

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_marketplace.*
import java.util.ArrayList

class MarketplaceActivity : AppCompatActivity() {

    private var layoutManager : RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<MarketplaceRecyclerAdapter.ViewHolder>? = null
    private lateinit var listingList: ArrayList<Listing>
    private lateinit var marketRecyclerView: RecyclerView
    var db = FirebaseDatabase.getInstance("https://hawkerpals-de16f-default-rtdb.asia-southeast1.firebasedatabase.app/")
    val dbRef = db.getReference()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_marketplace)

        layoutManager = LinearLayoutManager(this)
        marketrecycler_view.layoutManager = layoutManager

        val groupname = intent.getStringExtra("GroupName")
        listingList = arrayListOf<Listing>()
        marketRecyclerView = findViewById(R.id.marketrecycler_view)

        dbRef.child("listing_info").addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                listingList.clear()
                for(listingSnapshot in snapshot.children){
                    val listing = listingSnapshot.getValue(Listing::class.java)
                    if(listing?.listing_group.contentEquals(groupname)){
                        listingList.add(listing!!)
                    }
                }
                if(listingList.isEmpty()){
                    Toast.makeText(this@MarketplaceActivity,"There are no listings right now, check back later!",Toast.LENGTH_LONG).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        dbRef.child("Users").addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                var user = snapshot.child(FirebaseAuth.getInstance().currentUser!!.uid)
                    .getValue(User::class.java)!!
                if (user.user_type=="hawker"){
                    getListingData(listingList)
                }
                else if (user.user_type=="customer"){
                    Toast.makeText(this@MarketplaceActivity,"This feature is only accessible for hawker accounts.",Toast.LENGTH_SHORT).show()

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })


    }

    private fun getListingData(arrayList: ArrayList<Listing>){
        marketrecycler_view.adapter = MarketplaceRecyclerAdapter(arrayList)
//        dbRef.child("listing_info").addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                if(snapshot.exists()){
//                    for (listingSnapshot in snapshot.children){
//                        val listings = listingSnapshot.getValue(Listing::class.java)
//                        arrayList.add(listings!!)
//                    }
//                    marketrecycler_view.adapter = MarketplaceRecyclerAdapter(arrayList)
//                }
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                TODO("Not yet implemented")
//            }
//        })
    }
}