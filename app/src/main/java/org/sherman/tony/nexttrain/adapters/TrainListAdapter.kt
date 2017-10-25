package org.sherman.tony.nexttrain.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import org.sherman.tony.nexttrain.models.TrainStatus
import org.sherman.tony.nexttrain.R
import java.text.SimpleDateFormat
import java.util.*


class TrainListAdapter(var listOfTrains: ArrayList<TrainStatus>,
                       val context: Context): RecyclerView.Adapter<TrainListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, position: Int): ViewHolder {
        val view = LayoutInflater.from(context)
                .inflate(R.layout.station_lst_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listOfTrains.size
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder!!.bindView(listOfTrains[position])
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        var station = itemView.findViewById<TextView>(R.id.stationListTextViewID)
        var arrivalTime = itemView.findViewById<TextView>(R.id.timeScheduleID)

        fun bindView(train: TrainStatus){
            var humanTime = readableTime(train.sch_arr_time!!)
            station.text = train.route.toString()
            arrivalTime.text = humanTime
            //arrivalTime.text = train.sch_arr_time.toString()
        }

        fun readableTime(seconds: Long):String {
            val milliSeconds = seconds*1000
            var date = Date()
            date.setTime(milliSeconds)
            val dateFormat = SimpleDateFormat("hh:mm")
            return dateFormat.format(date)

            return ""
        }
    }
}
