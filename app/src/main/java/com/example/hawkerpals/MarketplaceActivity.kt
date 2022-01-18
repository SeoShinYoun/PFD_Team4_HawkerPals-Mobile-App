package com.example.hawkerpals

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hawkerpals.models.Post
import com.example.hawkerpals.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_marketplace.*
import kotlinx.android.synthetic.main.activity_upload_market_product.*
import java.util.ArrayList

class MarketplaceActivity : AppCompatActivity() {

    private var layoutManager : RecyclerView.LayoutManager? = null
    private lateinit var mName: TextView
//    private var adapter: RecyclerView.Adapter<MarketplaceRecyclerAdapter.ViewHolder>? = null
    private lateinit var listingList: ArrayList<Listing>
    private lateinit var marketRecyclerView: RecyclerView
//    var db = FirebaseDatabase.getInstance("https://hawkerpals-de16f-default-rtdb.asia-southeast1.firebasedatabase.app/")
//    val dbRef = db.getReference()

    //new code
    private var signedInUser:User? = null
    private lateinit var firebaseDb: FirebaseFirestore
    private lateinit var posts:MutableList<Post>
    private lateinit var adapter:MarketplaceRecyclerAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_marketplace)

        val intent = getIntent()
        val groupname = intent.getStringExtra("GroupName")
        mName = findViewById(R.id.mpName)
        mName.setText(groupname)

        posts = mutableListOf()

        adapter = MarketplaceRecyclerAdapter(this, posts)
        marketrecycler_view.adapter = adapter
        marketrecycler_view.layoutManager = LinearLayoutManager(this)
        //new code
        firebaseDb = FirebaseFirestore.getInstance()

        firebaseDb.collection("users")
            .document(FirebaseAuth.getInstance().currentUser?.uid as String)
            .get()
            .addOnSuccessListener { userSnapshot ->
                signedInUser = userSnapshot.toObject(User::class.java)
                Log.i(TAG,"signed in user: ${signedInUser}")
            }
        val postsReference = firebaseDb
            .collection("posts")
            .limit(20)
            .orderBy("creationtime", Query.Direction.DESCENDING)
            .whereEqualTo("marketname", groupname)
        postsReference.addSnapshotListener { snapshot, exception ->
            if (exception != null || snapshot == null) {
                Log.e(TAG, "Got some error", exception)
                return@addSnapshotListener
            }

            val postList = snapshot.toObjects(Post::class.java)
            posts.clear()
            posts.addAll(postList)
            adapter.notifyDataSetChanged()
            for (post in postList) {
                Log.i(TAG, "Post ${post}")
            }


        }
        fabCreate.setOnClickListener{
            val intent = Intent(this,uploadMarketProduct::class.java)
            intent.putExtra("GroupName",groupname)
            startActivity(intent)
        }





//        layoutManager = LinearLayoutManager(this)
//        marketrecycler_view.layoutManager = layoutManager
//
//        listingList = arrayListOf<Listing>()
//        marketRecyclerView = findViewById(R.id.marketrecycler_view)
//
//        dbRef.child("listing_info").addValueEventListener(object: ValueEventListener{
//            override fun onDataChange(snapshot: DataSnapshot) {
//                listingList.clear()
//                for(listingSnapshot in snapshot.children){
//                    val listing = listingSnapshot.getValue(Listing::class.java)
//                    if(listing?.listing_group.contentEquals(groupname)){
//                        listingList.add(listing!!)
//                    }
//                }
//                if(listingList.isEmpty()){
//                    Toast.makeText(this@MarketplaceActivity,"There are no listings right now, check back later!",Toast.LENGTH_LONG).show()
//                }
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                TODO("Not yet implemented")
//            }

//        })
//
//        dbRef.child("Users").addValueEventListener(object :ValueEventListener{
//            override fun onDataChange(snapshot: DataSnapshot) {
//                var user = snapshot.child(FirebaseAuth.getInstance().currentUser!!.uid)
//                    .getValue(User::class.java)!!
//                if (user.user_type=="hawker"){
//                    getListingData(listingList)
//                }
//                else if (user.user_type=="customer"){
//                    Toast.makeText(this@MarketplaceActivity,"This feature is only accessible for hawker accounts.",Toast.LENGTH_SHORT).show()
//
//                }
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                TODO("Not yet implemented")
//            }
//
//        })
//
//
//    }
//
//    private fun getListingData(arrayList: ArrayList<Listing>){
//        marketrecycler_view.adapter = MarketplaceRecyclerAdapter(arrayList)
////        dbRef.child("listing_info").addValueEventListener(object : ValueEventListener {
////            override fun onDataChange(snapshot: DataSnapshot) {
////                if(snapshot.exists()){
////                    for (listingSnapshot in snapshot.children){
////                        val listings = listingSnapshot.getValue(Listing::class.java)
////                        arrayList.add(listings!!)
////                    }
////                    marketrecycler_view.adapter = MarketplaceRecyclerAdapter(arrayList)
////                }
////            }
////
////            override fun onCancelled(error: DatabaseError) {
////                TODO("Not yet implemented")
////            }
////        })
    }
}