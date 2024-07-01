package com.example.pojecttest

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class list_adapter(val context: Activity) : RecyclerView.Adapter<list_adapter.viewHolder>(){
    val data = deCityListXml_fun(context)
    inner class viewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        var witem = itemView.findViewById<TextView>(R.id.witem)
        var item = itemView.findViewById<CardView>(R.id.item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        return viewHolder(LayoutInflater.from(parent.context).inflate(R.layout.wlist_item_view,parent,false))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.apply {
            witem.text = "${data[position].name}\n${data[position].name_en} ${data[position].type}"
            item.setOnClickListener {
                val intent = Intent(context,item_page::class.java)
                intent.putExtra("fileName",data[position].file_name)
                context.startActivity(intent)
            }
        }
    }
}

class weatherList_adapter(val context: Context , val fileName : String) : RecyclerView.Adapter<weatherList_adapter.viewHolder>(){
    var data = deWeather_fun(context,fileName)
    inner class viewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        var witem = itemView.findViewById<TextView>(R.id.witem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        return viewHolder(LayoutInflater.from(parent.context).inflate(R.layout.wlist_item_view,parent,false))
    }

    override fun getItemCount(): Int {
        return data.hourly_forecast.size
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.apply {
            witem.text = "${data.hourly_forecast[position].time} \n ${data.hourly_forecast[position].weather_condition} \n ${data.hourly_forecast[position].temperature}"
        }
    }
}