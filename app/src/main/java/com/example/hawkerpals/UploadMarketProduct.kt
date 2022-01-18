package com.example.hawkerpals

import android.app.Activity
import android.app.ProgressDialog
import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import com.example.hawkerpals.models.Post
import com.example.hawkerpals.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_upload_market_product.*


class uploadMarketProduct : AppCompatActivity() {

    private var signedInUser:User? = null
    lateinit var filepath: Uri
    private lateinit var firestoreDb: FirebaseFirestore
    private lateinit var storageReference: StorageReference



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_market_product)

        storageReference = FirebaseStorage.getInstance().reference

        firestoreDb = FirebaseFirestore.getInstance()

        firestoreDb.collection("users")
            .document(FirebaseAuth.getInstance().currentUser?.uid as String)
            .get()
            .addOnSuccessListener { userSnapshot ->
                signedInUser = userSnapshot.toObject(User::class.java)
                Log.i(TAG,"signed in user: ${signedInUser}")
            }
        btnPickImage.setOnClickListener {
            startFileChooser()
        }

        uploadBtn.setOnClickListener {
            uploadFile()
        }
    }

    private fun uploadFile() {
        val intent = getIntent()
        val groupname = intent.getStringExtra("GroupName")
        if (filepath == null){
            Toast.makeText(this,"No photo",Toast.LENGTH_SHORT).show()
            return
        }
        if(edDescription.text.isBlank()){
            Toast.makeText(this,"Description cannot be empty",Toast.LENGTH_SHORT).show()
            return
        }
        if(signedInUser == null){
            Toast.makeText(this,"No user signed in",Toast.LENGTH_SHORT).show()
            return
        }
        uploadBtn.isEnabled = false

        var photoReference =storageReference.child("photos/${System.currentTimeMillis()}-photo.jpg")
        photoReference.putFile(filepath)
            .continueWithTask { photoUploadTask ->
                photoReference.downloadUrl
            }.continueWithTask { downloadUrlTask ->
                val post = Post(
                    edDescription.text.toString(),
                    downloadUrlTask.result.toString(),
                    System.currentTimeMillis(),
                    groupname!!,
                    signedInUser)
                firestoreDb.collection("posts").add(post)
            }.addOnCompleteListener{postCreationTask ->
                uploadBtn.isEnabled = true
                if(!postCreationTask.isSuccessful){
                    Toast.makeText(this,"Unsuccessful",Toast.LENGTH_SHORT).show()
                }
                edDescription.text.clear()
                imageView.setImageResource(0)
                Toast.makeText(this,"Success",Toast.LENGTH_SHORT).show()
                val profileIntent = Intent(this,HomeActivity::class.java)
//                intent.putExtra("GroupName",groupname)
                startActivity(profileIntent)
                finish()
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
