package org.sherman.tony.nexttrain.activities

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_load_stations.*
import org.sherman.tony.nexttrain.R
import org.sherman.tony.nexttrain.adapters.ListRecyclerAdapter
//import org.sherman.tony.nexttrain.adapters.ListRecyclerAdapter
import org.sherman.tony.nexttrain.adapters.dBHandler
import org.sherman.tony.nexttrain.data.Globals
import org.sherman.tony.nexttrain.models.StationList

class LoadStationsActivity : AppCompatActivity() {
    var dbHandle: dBHandler? = null
    var recyclerAdapter: ListRecyclerAdapter? = null
    var stationList: ArrayList<StationList>? = null
    var stationListItems: ArrayList<StationList>? = null
    var layoutManager: RecyclerView.LayoutManager? = null
    var requestQueue: RequestQueue? = null // Set up a Volley Request Queue


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_load_stations)

        dbHandle = dBHandler(this)
        stationList = ArrayList<StationList>()
        stationListItems = ArrayList()
        layoutManager = LinearLayoutManager(this)
        recyclerAdapter = ListRecyclerAdapter(this, stationListItems!!)
        requestQueue = Volley.newRequestQueue(this) // Instantiate the Volley Queue

        // Set up the Recycler View
        recyclerViewID2.layoutManager = layoutManager
        recyclerViewID2.adapter = recyclerAdapter

        // Load the data depending on what the intent value stations was set to

        if(Globals.GlobalVariable.stationButton == 1) {
            stationList = dbHandle!!.readAllStations()
        } else {
            stationList = dbHandle!!.getRecent()
        }

        for (s in stationList!!.iterator()) {
            val station = StationList()

            station.recent = s.recent
            station.favorite = s.favorite
            station.station = s.station
            station.id = s.id

            stationListItems!!.add(station)
        }
        recyclerAdapter!!.notifyDataSetChanged()
    }

    fun returnHome(view: View) {
        var intent = Intent(this, MainActivity::class.java)
        var returnIntent = this.intent

        returnIntent.putExtra("return", "return from All Stations")
        setResult(Activity.RESULT_OK, returnIntent)
        finish()
        // startActivity(intent) Used for simple intent calls
    }
}
