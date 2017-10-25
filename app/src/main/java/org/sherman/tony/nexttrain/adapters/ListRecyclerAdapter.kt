package org.sherman.tony.nexttrain.adapters

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityCompat.startActivityForResult
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import org.sherman.tony.nexttrain.R
import org.sherman.tony.nexttrain.activities.NextTrainActivity
import org.sherman.tony.nexttrain.data.CODE_TEST
import org.sherman.tony.nexttrain.data.Globals
import org.sherman.tony.nexttrain.models.StationList


class ListRecyclerAdapter(context: Context, stations:ArrayList<StationList>): RecyclerView.Adapter<ListRecyclerAdapter.ViewHolder>() {

    val context = context
    val stations = stations

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindViews(stations[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        // Create a View from the XML
        val view = LayoutInflater.from(context).inflate(R.layout.station_names,parent,false)

        return ViewHolder(view,context,stations)

    }

    override fun getItemCount(): Int {
        return stations.count()
    }

    inner class ViewHolder(itemView: View, context: Context, list:ArrayList<StationList>): RecyclerView.ViewHolder(itemView), View.OnClickListener {

        var mContext = context
        var mList = list
        var stationName: TextView = itemView.findViewById(R.id.stationNameTextViewID)

        fun bindViews(stationList: StationList) {


            stationName.text = stationList.station.toString()
            stationName.setOnClickListener(this)
        }

        override fun onClick(view: View?) {
            var mPosition: Int = adapterPosition
            var status = mList[mPosition]
            var bundle = Bundle()

            when(view!!.id){
                stationName.id -> {
                    var db = dBHandler(mContext)
                    db.touchRecord(stationName.text.toString())
                    var returnCode = db.touchRecord(stationName.text.toString())
                    Globals.stationName = stationName.text.toString()
                    val intent = Intent(context, NextTrainActivity::class.java)
                    intent.putExtra("station", stationName.text.toString())
                    startActivity(context, intent, bundle)
                }
            }
        }
    }
}
