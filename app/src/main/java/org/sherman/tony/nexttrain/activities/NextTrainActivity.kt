package org.sherman.tony.nexttrain.activities

import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_next_train.*
import org.json.JSONException
import org.json.JSONObject
import org.sherman.tony.nexttrain.R
import org.sherman.tony.nexttrain.adapters.TabbedMenuAdapter
import org.sherman.tony.nexttrain.adapters.TrainListAdapter
import org.sherman.tony.nexttrain.data.MBTA_FORMAT
import org.sherman.tony.nexttrain.data.MBTA_KEY
import org.sherman.tony.nexttrain.data.MBTA_ROOT
import org.sherman.tony.nexttrain.models.TrainStatus

class NextTrainActivity : AppCompatActivity() {

    var volleyRequest: RequestQueue? = null
    //var trainList: TrainList()
    var listOfTrains: ArrayList<TrainStatus>? = null

    val context = this
    val station = "Back Bay"
    var trainAdapter: TrainListAdapter? = null
    var layoutManager: RecyclerView.LayoutManager? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_next_train)
        //        setSupportActionBar(toolbar)
        var sectionAdapter: TabbedMenuAdapter

        sectionAdapter = TabbedMenuAdapter(supportFragmentManager)
        viewPagerID.adapter = sectionAdapter
        tabs.setupWithViewPager(viewPagerID)
        tabs.setTabTextColors(Color.WHITE, Color.GREEN)

        // Get a list of train schedules
        //
        val url = createURL(station)

        listOfTrains = ArrayList<TrainStatus>()

        volleyRequest = Volley.newRequestQueue(this)
        readTrains(url,station)
    }

    fun createURL(station: String): String {

        val stationString = "&stop="+station.replace(" ", "%20")
        return MBTA_ROOT+ MBTA_KEY+stationString+ MBTA_FORMAT
    }

    fun readTrains(url:String, station: String) {

        var trainStatus = TrainStatus()


        println("URL ===> $url")
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET,url,
                Response.Listener {
                    response: JSONObject ->
                    try {
                        val responseMode = response.getJSONArray("mode")
                        val responseModeRouteObj = responseMode.getJSONObject(0)
                        val responseModeRouteObjRoute = responseModeRouteObj.getJSONArray("route")
                        for (i in 0..responseModeRouteObjRoute.length() - 1){
                            val responseModeRouteObjRouteRouteObj = responseModeRouteObjRoute.getJSONObject(i)
                            val responseModeRouteObjRouteRouteObjDirection = responseModeRouteObjRouteRouteObj.getJSONArray("direction")
                            for (j in 0.. responseModeRouteObjRouteRouteObjDirection.length() - 1){
                                val responseModeRouteObjRouteRouteObjDirectionDirectionObj = responseModeRouteObjRouteRouteObjDirection.getJSONObject(j)
                                val direction = responseModeRouteObjRouteRouteObjDirectionDirectionObj.getString("direction_id")
                                val directionValue = direction.toInt()
                                val tripArray = responseModeRouteObjRouteRouteObjDirectionDirectionObj.getJSONArray("trip")
                                for (k in 0..tripArray.length() - 1){
                                    trainStatus = TrainStatus()
                                    trainStatus.direction_id = directionValue
                                    var tripArrayObj = tripArray.getJSONObject(k)
                                    var schArrival = tripArrayObj.getString("sch_arr_dt")
                                    trainStatus.sch_arr_time = schArrival.toLong()
                                    trainStatus.route = tripArrayObj.getString("trip_name")

                                    println("ARRIVAL TIME = ${trainStatus.sch_arr_time}") // DEBUGGING print statement

                                    listOfTrains!!.add(0, trainStatus)
                                    //println("ADDITION RESPONSE WAS $addResponse")
                                    for (i in 0..listOfTrains!!.size - 1) {
                                        println("FINAL LIST ELEMENT ===> ${listOfTrains!![i].sch_arr_time}")
                                    }
                                }
                            }
                        }
                        trainAdapter = TrainListAdapter(listOfTrains!!, this)
                        layoutManager = LinearLayoutManager(this)

                        // Set up recycler Adapter
                        recyclerViewID.layoutManager = layoutManager
                        recyclerViewID.adapter = trainAdapter


                        trainAdapter!!.notifyDataSetChanged()

                    } catch (e: JSONException){e.printStackTrace()}
                },
                Response.ErrorListener {
                    error: VolleyError? ->
                    try{
                    error!!.printStackTrace()
                }catch(e:JSONException) {
                        e.printStackTrace()
                    }
                })
        //val list:ArrayList<TrainList>? = null

        volleyRequest!!.add(jsonObjectRequest)
        return
        //return list
    }

    fun returnMainPage(view: View){
        var intent = Intent(this, MainActivity::class.java)
        var returnIntent = this.intent

        returnIntent.putExtra("return", "return from Tabbed Activity")
        //setResult(Activity.RESULT_OK, returnIntent)
        startActivity(intent)
        finish()
    }


}
