package com.example.hawkerpals

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import com.example.hawkerpals.databinding.ActivityProfileEditBinding
import com.example.hawkerpals.models.Post
import com.example.hawkerpals.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_profile_edit.*
import kotlinx.android.synthetic.main.activity_upload_market_product.*
import kotlinx.android.synthetic.main.activity_upload_market_product.btnPickImage
import kotlinx.android.synthetic.main.activity_upload_market_product.imageView
import kotlinx.android.synthetic.main.activity_upload_market_product.uploadBtn

class ProfileEdit : AppCompatActivity() {

    private lateinit var binding: ActivityProfileEditBinding

    private var signedInUser: User? = null
    private var filepath: Uri? = null
    private lateinit var firestoreDb: FirebaseFirestore
    private lateinit var storageReference: StorageReference
    private var dbRef = FirebaseDatabase.getInstance("https://hawkerpals-de16f-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference()
    private var updateRealtimeDb = hashMapOf<String, Any>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        storageReference = FirebaseStorage.getInstance().reference

        firestoreDb = FirebaseFirestore.getInstance()

        firestoreDb.collection("users")
            .document(FirebaseAuth.getInstance().currentUser?.uid as String)
            .get()
            .addOnSuccessListener { userSnapshot ->
                signedInUser = userSnapshot.toObject(User::class.java)
                Log.i(ContentValues.TAG,"signed in user: ${signedInUser}")
            }
        btnPickImage.setOnClickListener {
            startFileChooser()
        }

        uploadBtn.setOnClickListener {
            uploadFile()
        }
    }

    private fun uploadFile() {


        if (filepath == null && editName.text.isBlank()) {
            Toast.makeText(this,"Update not successful", Toast.LENGTH_SHORT).show()
            return
        }

        if(signedInUser == null){
            Toast.makeText(this,"No user signed in", Toast.LENGTH_SHORT).show()
            return
        }
        uploadBtn.isEnabled = false

        if (editName.text.isNotBlank() && filepath == null){
            firestoreDb
                .collection("users")
                .document(FirebaseAuth.getInstance().currentUser?.uid as String)
                .update("username",editName.text.toString())
            updateRealtimeDb = hashMapOf<String, Any>(
                "/Users/${FirebaseAuth.getInstance().currentUser?.uid.toString()}/user_name" to editName.text.toString(),
            )
            dbRef.updateChildren(updateRealtimeDb)
            uploadBtn.isEnabled = true
            editName.text.clear()
            Toast.makeText(this,"Success", Toast.LENGTH_SHORT).show()
            val profileIntent = Intent(this,HomeActivity::class.java)
            startActivity(profileIntent)
            finish()
        }

        else if (filepath != null && editName.text.isBlank()){
            var photoReference =storageReference.child("profile/${System.currentTimeMillis()}-photo.jpg")
            photoReference.putFile(filepath!!)
                .continueWithTask { photoUploadTask ->
                    photoReference.downloadUrl
                }.continueWithTask { downloadUrlTask ->
                    //Update username in firestore

                    if (filepath != null){
                        updateRealtimeDb = hashMapOf<String, Any>(
                            "/Users/${FirebaseAuth.getInstance().currentUser?.uid.toString()}/user_profilepic" to downloadUrlTask.result.toString()
                        )
                    }

                    dbRef.updateChildren(updateRealtimeDb)
                }.addOnCompleteListener{profileEditTask ->
                    uploadBtn.isEnabled = true
                    if(!profileEditTask.isSuccessful){
                        Toast.makeText(this,"Unsuccessful", Toast.LENGTH_SHORT).show()
                    }
                    editName.text.clear()
                    imageView.setImageResource(0)
                    Toast.makeText(this,"Success", Toast.LENGTH_SHORT).show()
                    val profileIntent = Intent(this,HomeActivity::class.java)
                    startActivity(profileIntent)
                    finish()
                }
        }
        else{
            var photoReference =storageReference.child("profile/${System.currentTimeMillis()}-photo.jpg")
            photoReference.putFile(filepath!!)
                .continueWithTask { photoUploadTask ->
                    photoReference.downloadUrl
                }.continueWithTask { downloadUrlTask ->
                    firestoreDb
                        .collection("users")
                        .document(FirebaseAuth.getInstance().currentUser?.uid as String)
                        .update("username",editName.text.toString())
                    //Update username and profile pic in realtime database

                    val updateRealtimeDb = hashMapOf<String, Any>(
                        "/Users/${FirebaseAuth.getInstance().currentUser?.uid.toString()}/user_name" to editName.text.toString(),
                        "/Users/${FirebaseAuth.getInstance().currentUser?.uid.toString()}/user_profilepic" to downloadUrlTask.result.toString()
                    )
                    dbRef.updateChildren(updateRealtimeDb)
                }.addOnCompleteListener{profileEditTask ->
                    uploadBtn.isEnabled = true
                    if(!profileEditTask.isSuccessful){
                        Toast.makeText(this,"Unsuccessful", Toast.LENGTH_SHORT).show()
                    }
                    editName.text.clear()
                    imageView.setImageResource(0)
                    Toast.makeText(this,"Success", Toast.LENGTH_SHORT).show()
                    val profileIntent = Intent(this,HomeActivity::class.java)
                    startActivity(profileIntent)
                    finish()
                }
        }
    }

    private fun startFileChooser() {
        var i = Intent()
        i.setType("image/*")
        i.setAction(Intent.ACTION_GET_CONTENT)
        startActivityForResult(Intent.createChooser(i, "Choose Picture"), 111)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 111 && resultCode == Activity.RESULT_OK && data!= null){
            filepath = data.data!!
            var bitmap = MediaStore.Images.Media.getBitmap(contentResolver,filepath)
            imageView.setImageBitmap(bitmap)

        }
    }
}