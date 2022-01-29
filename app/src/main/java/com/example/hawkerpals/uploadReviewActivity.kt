package com.example.hawkerpals

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.hawkerpals.models.ReviewMarket
import com.example.hawkerpals.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_upload_review.*

class uploadReviewActivity : AppCompatActivity() {
    private var signedInUser: User? = null
    private lateinit var firestoreDb: FirebaseFirestore
    private lateinit var storageReference: StorageReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_review)

        storageReference = FirebaseStorage.getInstance().reference

        firestoreDb = FirebaseFirestore.getInstance()

        firestoreDb.collection("users")
            .document(FirebaseAuth.getInstance().currentUser?.uid as String)
            .get()
            .addOnSuccessListener { userSnapshot ->
                signedInUser = userSnapshot.toObject(User::class.java)
                Log.i(ContentValues.TAG,"signed in user: ${signedInUser}")
            }
        submit_review.setOnClickListener {
            submitReview()
        }
    }

    private fun submitReview() {
        val intent = getIntent()
        val groupname = intent.getStringExtra("GroupName")


        if(review_msg.text.isBlank()){
            Toast.makeText(this,"Review cannot be empty", Toast.LENGTH_SHORT).show()
            return
        }
        if(signedInUser == null){
            Toast.makeText(this,"No user signed in", Toast.LENGTH_SHORT).show()
            return
        }


        var review = ReviewMarket(
            review_msg.text.toString(),
            rating1.rating,
            System.currentTimeMillis(),
            groupname!!,
            signedInUser
            )
        firestoreDb.collection("reviews").add(review)
            .addOnCompleteListener{
                review_msg.text.clear()
                Toast.makeText(this,"Success", Toast.LENGTH_SHORT).show()
                val profileIntent = Intent(this,HomeActivity::class.java)
                intent.putExtra("GroupName",groupname)
                startActivity(profileIntent)
                finish()
            }

    }
}