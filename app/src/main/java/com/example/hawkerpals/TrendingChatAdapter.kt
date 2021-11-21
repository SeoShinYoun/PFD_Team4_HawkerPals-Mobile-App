package com.example.hawkerpals

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

class TrendingChatAdapter(val context: Context, val messageList:ArrayList<ThreadMessage>): RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    var mAuth= FirebaseAuth.getInstance()

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val viewHolder = holder as ChatViewHolder
        val currentMessage = messageList[position]
        holder.messageText.text = currentMessage.messageContent
//        holder.messagerName.text = currentMessage.sentUserName



    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {

        if (viewType == 0){
            val view:View = LayoutInflater.from(context).inflate(R.layout.threadmessageto_layout, parent, false)
            return ChatViewHolder(view)
        }else{
            val view:View = LayoutInflater.from(context).inflate(R.layout.threadmessagefrom_layout, parent, false)
            return ChatViewHolder(view)
        }
    }

    override fun getItemViewType(position: Int): Int {
        val currentMessage = messageList[position]

        if(currentMessage.sentUserID.contentEquals(mAuth.uid)){
            return 0
        }
        return 1
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    class ChatViewHolder(itemView:View) :RecyclerView.ViewHolder(itemView){
        val messageText = itemView.findViewById<TextView>(R.id.threadMessageText)
        val messagerName = itemView.findViewById<TextView>(R.id.messagerName)
    }



}