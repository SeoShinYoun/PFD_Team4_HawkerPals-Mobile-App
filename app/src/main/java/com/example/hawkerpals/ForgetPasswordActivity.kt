package com.example.hawkerpals

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_forget_password.*
import android.util.Patterns




class ForgetPasswordActivity : AppCompatActivity() {

    lateinit var mAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_password)
        if(!isValidEmail(enterpw1.text.toString())){
            enterpw1.setError("Invalid email")
        }
        mAuth = FirebaseAuth.getInstance();

        resetBtn.setOnClickListener {
            mAuth.sendPasswordResetEmail(enterpw1.text.toString())
            Toast.makeText(
                this@ForgetPasswordActivity,
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