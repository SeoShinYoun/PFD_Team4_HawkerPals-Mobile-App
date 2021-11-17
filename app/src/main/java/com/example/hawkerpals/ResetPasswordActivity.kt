package com.example.hawkerpals

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_forget_password.*
import android.util.Patterns




class ResetPasswordActivity : AppCompatActivity() {

    lateinit var mAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)
        if(!isValidEmail(forgetEmail.text.toString())){
            forgetEmail.setError("Invalid email")
        }
        mAuth = FirebaseAuth.getInstance();

        resetBtn.setOnClickListener {
            mAuth.sendPasswordResetEmail(forgetEmail.text.toString())
            Toast.makeText(
                this@ResetPasswordActivity,
                "Please check your email to reset the password",
                Toast.LENGTH_SHORT
            ).show()
            onBackPressed()
        }
    }
    private fun isValidEmail(email: String): Boolean {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}