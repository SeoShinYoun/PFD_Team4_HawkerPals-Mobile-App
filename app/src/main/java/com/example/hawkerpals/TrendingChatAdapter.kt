package com.example.hawkerpals

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

class TrendingChatAdapter(val context: Context, val messageList:ArrayList<ThreadMessage>): RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    val ITEM_RECEIVE = 1;
    val ITEM_SENT = 2;
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val currentMessage = messageList[position]

        if(holder.javaClass == SentViewHolder::class.java){
            val viewHolder = holder as SentViewHolder
            holder.sentMessage.text = currentMessage.messageContent
        }
        else{
            val viewHolder = holder as ReceiveViewHolder
            holder.receiveMessage.text = currentMessage.messageContent
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {

        if (viewType == 1){
            val view:View = LayoutInflater.from(context).inflate(R.layout.threadmessagefrom_layout, parent, false)
            return ReceiveViewHolder(view)
        }else{
            val view:View = LayoutInflater.from(context).inflate(R.layout.threadmessageto_layout, parent, false)
            return SentViewHolder(view)
        }
    }

    override fun getItemViewType(position: Int): Int {
        val currentMessage = messageList[position]
        if(FirebaseAuth.getInstance().currentUser?.uid.equals(currentMessage.sentUserID)){
            return ITEM_SENT
        }else{
            return ITEM_RECEIVE
        }
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    class SentViewHolder(itemView:View) :RecyclerView.ViewHolder(itemView){
        val sentMessage = itemView.findViewById<TextView>(R.id.threadMessageTextTo)
    }
    class ReceiveViewHolder(itemView:View) :RecyclerView.ViewHolder(itemView){
        val receiveMessage = itemView.findViewById<TextView>(R.id.threadMessageTextFrom)

    }


}