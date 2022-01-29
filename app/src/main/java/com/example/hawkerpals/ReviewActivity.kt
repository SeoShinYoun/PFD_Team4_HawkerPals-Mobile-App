package com.example.hawkerpals

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hawkerpals.models.Post
import com.example.hawkerpals.models.ReviewMarket
import com.example.hawkerpals.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_review.*

import java.util.ArrayList


class ReviewActivity : AppCompatActivity() {

    private var signedInUser:User? = null
    private lateinit var firestoreDb: FirebaseFirestore
    private lateinit var storageReference: StorageReference
    private lateinit var reviews:MutableList<ReviewMarket>
    private lateinit var adapter:ReviewRecyclerAdapter
    private lateinit var firebaseDb: FirebaseFirestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review)
        val intent = getIntent()
        val groupname = intent.getStringExtra("GroupName")
        mkpName.setText(groupname)

        reviews = mutableListOf()

        adapter = ReviewRecyclerAdapter(this, reviews)
        review_recycler.adapter = adapter
        review_recycler.layoutManager = LinearLayoutManager(this)
        //new code
        firebaseDb = FirebaseFirestore.getInstance()


        firebaseDb.collection("users")
            .document(FirebaseAuth.getInstance().currentUser?.uid as String)
            .get()
            .addOnSuccessListener { userSnapshot ->
                signedInUser = userSnapshot.toObject(User::class.java)
                Log.i(TAG,"signed in user: ${signedInUser}")
            }

        val reviewReference = firebaseDb
            .collection("reviews")
            .limit(20)
            .orderBy("creationtime", Query.Direction.DESCENDING)
            .whereEqualTo("marketname", groupname)
        reviewReference.addSnapshotListener { snapshot, exception ->
            if (exception != null || snapshot == null) {
                Log.e(TAG, "Got some error", exception)
                return@addSnapshotListener
            }

            val reviewList = snapshot.toObjects(ReviewMarket::class.java)
            reviews.clear()
            reviews.addAll(reviewList)
            adapter.notifyDataSetChanged()
            for (review in reviewList) {
                Log.i(TAG, "Review ${review}")
            }


        }
        reviewCreate.setOnClickListener{
            val intent = Intent(this,uploadReviewActivity::class.java)
            intent.putExtra("GroupName",groupname)
            startActivity(intent)
        }

    }
}