package com.example.hawkerpals.bottomnavbartutorial.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.example.hawkerpals.HomeActivity
import com.example.hawkerpals.LoginActivity
import com.example.hawkerpals.R
import com.example.hawkerpals.ResetPasswordActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_profile.*



class ProfileFragment : Fragment() {



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment

        val v = inflater.inflate(R.layout.fragment_profile, container, false)

        val btn = v.findViewById<Button>(R.id.logout)
        btn.setOnClickListener{
            val intent = Intent (getActivity(), LoginActivity::class.java)
            getActivity()?.startActivity(intent)
            FirebaseAuth.getInstance().signOut()
        }

        val resetPWbtn = v.findViewById<Button>(R.id.resetPw)
        resetPWbtn.setOnClickListener {
            val intent = Intent (getActivity(), ResetPasswordActivity::class.java)
            getActivity()?.startActivity(intent)

        }

        return v

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onStart() {
        super.onStart()

    }

    override fun onResume() {
        super.onResume()

    }

    override fun onPause() {
        super.onPause()

    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onDetach() {
        super.onDetach()
    }



    companion object {


    }
}