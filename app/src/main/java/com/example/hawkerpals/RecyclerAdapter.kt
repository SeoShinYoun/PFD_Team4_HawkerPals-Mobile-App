package com.example.hawkerpals

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter: RecyclerView.Adapter<RecyclerAdapter.ViewHolder>(){

    private var titles = arrayOf("Telok Ayer Market", "Tiong Bahru Market", "Maxwell Food Centre", "Chinatown Complex",
        "Golden Mile Centre", "ABC Brickworks", "Bedok Food Centre", "Tekka Food Centre")

    private var details = arrayOf("18 Raffles Quay, 048582", "30 Seng Poh Rd, 168898","1 Kadayanallur St, 069184","335 Smith St, 050335",
        "505 Beach Rd, 199583","6 Jalan Bukit Merah, 150006","1 Bedok Rd, 469572","665 Buffalo Rd, 210665")

    private val images = intArrayOf(R.drawable.telok_ayer_market_ic, R.drawable.tiong_bahru_ic, R.drawable.maxwell_ic,R.drawable.chinatown_complex_ic,
        R.drawable.goldenmile_ic,R.drawable.abc_brickworks_ic,R.drawable.bedok_food_ic,R.drawable.tekka_food_centre)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_layout, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerAdapter.ViewHolder, position: Int) {
        holder.itemTitle.text = titles[position]
        holder.itemDetail.text = details[position]
        holder.itemImage.setImageResource(images[position])

    }

    override fun getItemCount(): Int {
        return titles.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var itemImage: ImageView
        var itemTitle: TextView
        var itemDetail: TextView

        init{
            itemImage = itemView.findViewById(R.id.item_image)
            itemTitle = itemView.findViewById(R.id.item_title)
            itemDetail = itemView.findViewById(R.id.item_detail)

            itemView.setOnClickListener{
                val position: Int = absoluteAdapterPosition

                Toast.makeText(itemView.context, "You clicked on ${titles[position]}", Toast.LENGTH_SHORT).show()
            }
        }

    }


}