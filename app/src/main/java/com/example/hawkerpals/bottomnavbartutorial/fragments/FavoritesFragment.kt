package com.example.hawkerpals.bottomnavbartutorial.fragments

import android.os.Bundle
import android.renderscript.Sampler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hawkerpals.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_marketplace.*
import java.util.ArrayList

class FavoritesFragment : Fragment() {

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<FavouritesRecyclerAdapter.ViewHolder>? = null
    private lateinit var user : User
    private lateinit var favouritesRecyclerView: RecyclerView
    private lateinit var hawkerList: ArrayList<Hawkers>

    val db = Firebase.database("https://hawkerpals-de16f-default-rtdb.asia-southeast1.firebasedatabase.app/")
    val dbRef = db.getReference()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_favorites, container, false)
        // Inflate the layout for this fragment
        return v
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?){
        super.onViewCreated(itemView, savedInstanceState)

        favouritesRecyclerView = itemView.findViewById(R.id.favourites_view)
        favouritesRecyclerView.layoutManager = LinearLayoutManager(context)

        favouritesRecyclerView.setHasFixedSize(true)

        hawkerList = arrayListOf<Hawkers>()
        getHawkerData(hawkerList)
    }

    private fun getHawkerData(arrayList: ArrayList<Hawkers>){
        dbRef.child("Users").addValueEventListener(object:ValueEventListener{
            override fun onDataChange(userSnapshot: DataSnapshot) {
                user = userSnapshot.child(FirebaseAuth.getInstance().currentUser!!.uid).getValue(User::class.java)!!
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        dbRef.child("hawkerCenter_info").addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    hawkerList.clear()
                    for (hawkerSnapShot in snapshot.children){
                        val hawker = hawkerSnapShot.getValue(Hawkers::class.java)
                        if (hawker != null) {
                            if(user.hawker_favourites.containsValue(hawker.hawker_name))
                                arrayList.add(hawker!!)
                        }
                    }
                    favouritesRecyclerView.adapter = FavouritesRecyclerAdapter(hawkerList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

}

