package com.example.hawkerpals

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val user_ID = intent.getStringExtra("user_id")
        val email_ID = intent.getStringExtra("email_id")

        userID.text = "User ID ::$user_ID"
        emailid.text = "Email ID::$email_ID"

        logout.setOnClickListener{
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this@HomeActivity, LoginActivity::class.java))
            finish()
        }
    }
}