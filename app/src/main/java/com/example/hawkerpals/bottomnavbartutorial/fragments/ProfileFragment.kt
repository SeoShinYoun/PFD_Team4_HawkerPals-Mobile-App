package com.example.hawkerpals.bottomnavbartutorial.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.hawkerpals.LoginActivity
import com.example.hawkerpals.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_profile.*



class ProfileFragment : Fragment() {
    var TAG = "ActivityLifeCycle"


    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d(TAG,"===>fragment===>onAttach()")
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG,"===>fragment===>onCreate()")
        logout.setOnClickListener{
            FirebaseAuth.getInstance().signOut()
            val intent = Intent (getActivity(), LoginActivity::class.java)
            getActivity()?.startActivity(intent)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        Log.d(TAG, "===>fragment===>onCreateView()")
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "===>fragment===>onStart()")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "===>fragment===>onResume()")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "===>fragment===>onPause()")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "===>fragment===>onStop()")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "===>fragment===>onDestroyView()")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "===>fragment===>onDestroy()")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d(TAG, "===>fragment===>onDetach()")
    }



    companion object {


    }
}