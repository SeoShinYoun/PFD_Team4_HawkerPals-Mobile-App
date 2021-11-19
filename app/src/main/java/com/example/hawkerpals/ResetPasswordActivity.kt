package com.example.hawkerpals

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_reset_password.*
import android.util.Patterns




class ResetPasswordActivity : AppCompatActivity() {

    lateinit var mAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)
        mAuth = FirebaseAuth.getInstance();

        resetBtn.setOnClickListener {
//            mAuth.sendPasswordResetEmail(enterpw1.text.toString())
//            Toast.makeText(
//                this@ResetPasswordActivity,
//                "Please check your email to reset the password",
//                Toast.LENGTH_SHORT
//            ).show()
//            onBackPressed()
            if(!enterpw2.text.toString().contentEquals(enterpw1.text.toString())){
                enterpw2.setError("Password does not match")
            }
            else{
                mAuth.currentUser?.updatePassword(enterpw2.text.toString())
                Toast.makeText(
                    this@ResetPasswordActivity,
                    "You have successfully changed password.",
                    Toast.LENGTH_SHORT
                ).show()
                val i = Intent(this, LoginActivity::class.java)
                startActivity(i)
                finish()
                mAuth.signOut()
            }
        }
    }

}