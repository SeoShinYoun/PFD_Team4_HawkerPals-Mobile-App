package com.example.hawkerpals.bottomnavbartutorial.fragments

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.hawkerpals.*
import com.example.hawkerpals.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.imageview.ShapeableImageView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.card_layout.view.*
import kotlinx.android.synthetic.main.fragment_profile.*



class ProfileFragment : Fragment() {

    private lateinit var mAuth: FirebaseAuth
    val db = Firebase.database("https://hawkerpals-de16f-default-rtdb.asia-southeast1.firebasedatabase.app/")
    val dbRef = db.getReference()
    var user:User? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment

        mAuth = FirebaseAuth.getInstance()
        mAuth.currentUser!!.uid
        val v = inflater.inflate(R.layout.fragment_profile, container, false)

        dbRef.child("Users").addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                user = snapshot.child(FirebaseAuth.getInstance().currentUser!!.uid).getValue(User::class.java)
                val txtname = v.findViewById<TextView>(R.id.nameOfUser)
                txtname.setText(user?.user_name)
                val txtemail = v.findViewById<TextView>(R.id.emailOfUser)
                txtemail.setText(user?.user_email)
                val txttype = v.findViewById<TextView>(R.id.typeOfUser)
                txttype.setText((user?.user_type).toString().capitalize())
                val txtstatus = v.findViewById<TextView>(R.id.statusOfUser)
                txtstatus.setText((user?.vacinated).toString().capitalize())

                if (user?.user_profilepic != null){
                    val profilepic = v.findViewById<ShapeableImageView>(R.id.profileImage)
                    Picasso.get().load(user?.user_profilepic).fit().into(profilepic)
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        val btn = v.findViewById<Button>(R.id.logout)
        btn.setOnClickListener{
            FirebaseAuth.getInstance().signOut()
            val intent = Intent (getActivity(), LoginActivity::class.java)
            getActivity()?.startActivity(intent)
//            FirebaseAuth.getInstance().signOut()
        }

        val resetPWbtn = v.findViewById<Button>(R.id.resetPw)
        resetPWbtn.setOnClickListener {
            val intent = Intent (getActivity(), ResetPasswordActivity::class.java)
            getActivity()?.startActivity(intent)
        }

        val editPFbtn = v.findViewById<Button>(R.id.Editprofile)
        editPFbtn.setOnClickListener {
            val intent = Intent (getActivity(), ProfileEdit::class.java)
            getActivity()?.startActivity(intent)
        }

        return v

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


}