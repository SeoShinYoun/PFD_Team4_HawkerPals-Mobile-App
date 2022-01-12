package com.example.hawkerpals

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hawkerpals.models.Post
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
    private var adapter: RecyclerView.Adapter<MarketplaceRecyclerAdapter.ViewHolder>? = null
    private lateinit var listingList: ArrayList<Listing>
    var db = FirebaseDatabase.getInstance("https://hawkerpals-de16f-default-rtdb.asia-southeast1.firebasedatabase.app/")
    val dbRef = db.getReference()

    //new code
    private lateinit var firebaseDb: FirebaseFirestore
    //

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_marketplace)

        //new code
        firebaseDb = FirebaseFirestore.getInstance()
        val postsReference = firebaseDb.collection("posts")
            .limit(30)
            .orderBy("creation_time", Query.Direction.DESCENDING)
        postsReference.addSnapshotListener{ snapshot, exception ->
            if (exception != null || snapshot == null){
                return@addSnapshotListener
            }
            val postList = snapshot.toObjects(Post::class.java)
            for (post in postList){

            }
        }
        //


        val intent = getIntent()
        val groupname = intent.getStringExtra("GroupName")
        mName = findViewById(R.id.mpName)
        mName.setText(groupname)

        postBtn.setOnClickListener {
            val i = Intent(this, uploadMarketProduct::class.java)
            startActivity(i)
            finish()
        }
        layoutManager = LinearLayoutManager(this)
        marketrecycler_view.layoutManager = layoutManager

        listingList = arrayListOf<Listing>()

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
        dbRef.child("listing_info").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for (listingSnapshot in snapshot.children){
                        val listings = listingSnapshot.getValue(Listing::class.java)
                        arrayList.add(listings!!)
                    }
                    marketrecycler_view.adapter = MarketplaceRecyclerAdapter(arrayList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}