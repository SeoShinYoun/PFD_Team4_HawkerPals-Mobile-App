package com.example.hawkerpals

import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_trending_chat.*
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.ArrayList

class TrendingChat : AppCompatActivity() {

    private lateinit var messageRecyclerView: RecyclerView
    private lateinit var messageBox:EditText
    private lateinit var sendButton:ImageView
    private lateinit var messageAdapter: TrendingChatAdapter
    private lateinit var messageList: ArrayList<ThreadMessage>
    private lateinit var mDbRef:DatabaseReference


    var receiverRoom:String? = null
    var senderRoom:String? = null


    val db = Firebase.database("https://hawkerpals-de16f-default-rtdb.asia-southeast1.firebasedatabase.app/")
    val dbRef = db.getReference()
//
    override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_trending_chat)


    val intent = Intent()
    val groupname = intent.getStringExtra("GroupName")
    val receiverUid = intent.getStringExtra("uid")
    val senderUid = FirebaseAuth.getInstance().currentUser?.uid
    val dbRef = db.getReference()



    senderRoom = receiverUid + senderUid
    receiverRoom = senderUid + receiverUid

    messageRecyclerView = findViewById(R.id.messageRv)
    messageBox = findViewById(R.id.sendThreadMessage)
    sendButton = findViewById(R.id.sendThreadMessageBtn)
    messageList = ArrayList()
    messageAdapter = TrendingChatAdapter(this,messageList)

    messageRecyclerView.layoutManager = LinearLayoutManager(this)
    messageRecyclerView.adapter = messageAdapter

    GroupName.setText(groupname)

//    GroupName.setText(groupname)
    //logic for adding data to recycler view
    dbRef.child("chats").child(senderRoom!!).child("messages")
        .addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                messageList.clear()
                for(postSnapshot in snapshot.children){
                    val message = postSnapshot.getValue(ThreadMessage::class.java)
                    messageList.add((message!!))
                }
                messageAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    sendButton.setOnClickListener {
        val message = messageBox.text.toString()
        val messageObject = ThreadMessage(message,senderUid)
        dbRef.child("chats").child(senderRoom!!).child("messages").push()
            .setValue(messageObject).addOnSuccessListener {
                dbRef.child("chats").child(receiverRoom!!).child("messages").push()
                    .setValue(messageObject)
            }
        messageBox.setText("")

    }
    }

}