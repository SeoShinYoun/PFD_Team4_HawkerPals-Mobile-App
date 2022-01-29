package com.example.hawkerpals

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.hawkerpals.bottomnavbartutorial.fragments.ActivityFragment
import com.example.hawkerpals.bottomnavbartutorial.fragments.FavoritesFragment
import com.example.hawkerpals.bottomnavbartutorial.fragments.HomeFragment
import com.example.hawkerpals.bottomnavbartutorial.fragments.ProfileFragment
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.fragment_profile.*

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val homeFragment = HomeFragment()
        val favoritesFragment = FavoritesFragment()

        val profileFragment = ProfileFragment()

        makeCurrentFragment(homeFragment)

        bottom_navigation.setOnItemSelectedListener{
            when (it.itemId){
                R.id.ic_home -> makeCurrentFragment(homeFragment)
                R.id.ic_favorite -> makeCurrentFragment(favoritesFragment)
                R.id.ic_person -> makeCurrentFragment(profileFragment)
            }
            true
        }
    }

    private fun makeCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_wrapper, fragment)
            commit()
        }


}


//        val user_ID = intent.getStringExtra("user_id")
//        val email_ID = intent.getStringExtra("email_id")
//
//        userID.text = "User ID ::$user_ID"
//        emailid.text = "Email ID::$email_ID"
//
