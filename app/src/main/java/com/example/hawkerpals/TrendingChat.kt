package com.example.hawkerpals

import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
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
import kotlin.math.log

class TrendingChat : AppCompatActivity() {

    private lateinit var messageList: ArrayList<ThreadMessage>

    private lateinit var nameOfGroup:TextView
    private lateinit var messageBox:EditText
    private lateinit var sendButton:ImageView

    private lateinit var messageRecyclerView: RecyclerView
    private lateinit var messageAdapter: TrendingChatAdapter

    private lateinit var mAuth: FirebaseAuth
    var db = FirebaseDatabase.getInstance("https://hawkerpals-de16f-default-rtdb.asia-southeast1.firebasedatabase.app/")
    val dbRef = db.getReference()
//    var receiverRoom:String? = null
//    var senderRoom:String? = null
    //    val db = Firebase.database("https://hawkerpals-de16f-default-rtdb.asia-southeast1.firebasedatabase.app/")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trending_chat)

        mAuth = FirebaseAuth.getInstance()
        mAuth.currentUser

        val intent = getIntent()

        val username = intent.getStringExtra("username")
        val groupname = intent.getStringExtra("GroupName")
//        val receiverUid = intent.getStringExtra("uid")

//        val senderUid = mAuth.currentUser?.uid

        nameOfGroup = findViewById(R.id.GroupName)
        nameOfGroup.setText(groupname)
//        senderRoom = receiverUid + senderUid
//        receiverRoom = senderUid + receiverUid

        messageRecyclerView = findViewById(R.id.messageRv)
        messageBox = findViewById(R.id.sendThreadMessage)
        sendButton = findViewById(R.id.sendThreadMessageBtn)
        messageList = ArrayList()
        messageAdapter = TrendingChatAdapter(this,messageList)

        messageRecyclerView.layoutManager = LinearLayoutManager(this)
        messageRecyclerView.adapter = messageAdapter



        dbRef.child("Messages").addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                messageList.clear()
                for(postSnapshot in snapshot.children){
                    val message = postSnapshot.getValue(ThreadMessage::class.java)
                        if(message?.groupName.contentEquals(groupname)){
                            messageList.add(message!!)
                        }
                }
                messageAdapter.notifyDataSetChanged()
                messageRecyclerView.smoothScrollToPosition((messageRecyclerView.adapter as TrendingChatAdapter).itemCount)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })


        sendButton.setOnClickListener {
            val message = messageBox.text.toString()
            if(message.equals("")){
                messageBox.setError("No Message Sent!")
            }
            else{
                var messageObject = ThreadMessage(message,mAuth.uid,username,groupname)
                dbRef.child("Messages").push().setValue(messageObject)
                messageBox.setText("")
            }
//            val messageObject = ThreadMessage(message,senderUid,groupname)
//            dbRef.child("chats").child(senderRoom!!).child("messages").push()
//                .setValue(messageObject).addOnSuccessListener {
//                    dbRef.child("chats").child(receiverRoom!!).child("messages").push()
//                        .setValue(messageObject)
//                }
//            messageBox.setText("")

        }


    }

}