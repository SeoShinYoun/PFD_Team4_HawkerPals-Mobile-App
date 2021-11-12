package com.example.hawkerpals

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    lateinit var databaseReference :  DatabaseReference
    var database: FirebaseDatabase? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        toLogin.setOnClickListener{
            onBackPressed()
//            startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
        }
        registerButton.setOnClickListener {
            when {
                TextUtils.isEmpty(emailInput2.text.toString().trim() { it <= ' '}) ->{
                    Toast.makeText(
                        this@RegisterActivity,
                        "Please enter your email.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                TextUtils.isEmpty(passwordInput2.text.toString().trim() { it <= ' '}) ->{
                    Toast.makeText(
                        this@RegisterActivity,
                        "Please enter your password.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else ->{
                    val email: String = emailInput2.text.toString().trim() { it <= ' '}
                    val password: String = passwordInput2.text.toString().trim() { it <= ' '}

                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password)
                        .addOnCompleteListener(
                            OnCompleteListener { task ->
                                if (task.isSuccessful){
                                    val firebaseUser: FirebaseUser = task.result!!.user!!

                                    //[START of writing to Firebase Realtime Database]
                                    val db = Firebase.database("https://hawkerpals-de16f-default-rtdb.asia-southeast1.firebasedatabase.app/")
                                    val dbRef = db.getReference("user_info")
                                    val user = User(user_email = null, user_id = null, user_name = null, user_type = null)
                                    val availableIDs: MutableList<Int> = mutableListOf()
                                    var userKey = "user_"
                                    for(x in (1..9999)){
                                        availableIDs.add(x)
                                    }

                                    dbRef.get().addOnSuccessListener {
                                        for (users in it.children){
                                            for (data in users.children){
                                                if(data.key == "user_id"){
                                                    availableIDs.remove(data.value.toString().toInt())
                                                }
                                            }
                                        }
                                        user.user_email = email //After adding more input boxes(eg full name, userType, etc) to the registration screen, data can be assigned right after this line
                                        user.user_id = turnIntTo4Char(availableIDs.elementAt(0))
                                        userKey += user.user_id
                                        dbRef.child(userKey).setValue(user)
                                    }.addOnFailureListener{
                                        Log.e("firebase", "Error getting data", it)
                                    }
                                    //[END of writing to Firebase Realtime Database]

                                    Toast.makeText(
                                        this@RegisterActivity,
                                        "You have registered successfully",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                    val intent =
                                        Intent(this@RegisterActivity,HomeActivity::class.java)
                                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                    intent.putExtra("user_id",firebaseUser.uid)
                                    intent.putExtra("email_id",email)
                                    startActivity(intent)
                                    finish()

                                }else{
                                    Toast.makeText(
                                        this@RegisterActivity,
                                        task.exception!!.message.toString(),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        )
                }
            }
        }
    }
    private fun turnIntTo4Char(id: Int): String {
        if (id>999){
            return id.toString()
        }
        if (id>99){
            return ("0"+id)
        }
        if (id>9){
            return ("00"+id)
        }
        if (id>0){
            return ("000"+id)
        }
        return "NO ID"
    }
}