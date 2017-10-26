package org.sherman.tony.nexttrain.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.fragment_inbound.*
import org.json.JSONException
import org.json.JSONObject

import org.sherman.tony.nexttrain.R
import org.sherman.tony.nexttrain.R.id.inboundFragmentRecyclerID
import org.sherman.tony.nexttrain.adapters.TrainListAdapter
import org.sherman.tony.nexttrain.data.Globals
import org.sherman.tony.nexttrain.data.MBTA_FORMAT
import org.sherman.tony.nexttrain.data.MBTA_KEY
import org.sherman.tony.nexttrain.data.MBTA_ROOT
import org.sherman.tony.nexttrain.models.TrainStatus


/**
 * A simple [Fragment] subclass.
 */
class InboundFragment : Fragment() {

    var trainAdapter: TrainListAdapter? = null
    var volleyRequest: RequestQueue? = null
    var listOfTrains:ArrayList<TrainStatus>? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_inbound, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        var linearLayoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        inboundFragmentRecyclerID.setHasFixedSize(true)
        //inboundFragmentRecyclerID

        listOfTrains = ArrayList<TrainStatus>()
        volleyRequest = Volley.newRequestQueue(context)

        val station = Globals.stationName.toString() // Will use globals later TODO
        val url = createURL(station)
        readTrains(url,1, linearLayoutManager)
    }

    fun createURL(station: String): String {

        val stationString = "&stop="+station.replace(" ", "%20").replace("/", "%2F")
        return MBTA_ROOT + MBTA_KEY +stationString+ MBTA_FORMAT
    }

    fun readTrains(url:String,chosenDirection: Int, layoutManager: LinearLayoutManager) {

        var trainStatus = TrainStatus()
        val routeDefault = "MBTA Error"
        val defaultTime: Long = System.currentTimeMillis()

        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET,url,
                Response.Listener {
                    response: JSONObject ->
                    try {
                        val responseMode = response.getJSONArray("mode")
                        if (responseMode.length() == 0){ // Catch an empty Response
                            setErrorList(routeDefault, defaultTime)
                        }
                        else {
                            val responseModeRouteObj = responseMode.getJSONObject(0)
                            val responseModeRouteObjRoute = responseModeRouteObj.getJSONArray("route")
                            if (responseModeRouteObjRoute.length() == 0){ // catch empty Routes
                                setErrorList(routeDefault, defaultTime)
                            }
                            else {
                                for (i in 0..responseModeRouteObjRoute.length() - 1) {
                                    val responseModeRouteObjRouteRouteObj = responseModeRouteObjRoute.getJSONObject(i)
                                    val responseModeRouteObjRouteRouteObjDirection = responseModeRouteObjRouteRouteObj.getJSONArray("direction")
                                    for (j in 0..responseModeRouteObjRouteRouteObjDirection.length() - 1) {
                                        val responseModeRouteObjRouteRouteObjDirectionDirectionObj = responseModeRouteObjRouteRouteObjDirection.getJSONObject(j)
                                        val direction = responseModeRouteObjRouteRouteObjDirectionDirectionObj.getString("direction_id")
                                        val directionValue = direction.toInt()
                                        val tripArray = responseModeRouteObjRouteRouteObjDirectionDirectionObj.getJSONArray("trip")
                                        if (tripArray.length() == 0){
                                            setErrorList(routeDefault,defaultTime)
                                        } else {
                                            for (k in 0..tripArray.length() - 1) {
                                                trainStatus = TrainStatus()
                                                trainStatus.direction_id = directionValue
                                                var tripArrayObj = tripArray.getJSONObject(k)
                                                var schArrival = tripArrayObj.getString("sch_arr_dt")
                                                trainStatus.sch_arr_time = schArrival.toLong()
                                                trainStatus.route = tripArrayObj.getString("trip_name")

                                                if (directionValue == chosenDirection) {
                                                    listOfTrains!!.add(0, trainStatus)
                                                }
                                            }
                                            if (tripArray.length() == 0){
                                                setErrorList(routeDefault,defaultTime)
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        if(listOfTrains!!.size == 0){
                            setErrorList(routeDefault, defaultTime)
                        }
                        listOfTrains!!.sortBy { it.sch_arr_time }
                        trainAdapter = TrainListAdapter(listOfTrains!!, context)

                        // Set up recycler Adapter
                        inboundFragmentRecyclerID.layoutManager = layoutManager
                        inboundFragmentRecyclerID.adapter = trainAdapter


                        trainAdapter!!.notifyDataSetChanged()

                    } catch (e: JSONException){e.printStackTrace()}
                },
                Response.ErrorListener {
                    error: VolleyError? ->
                    try{
                        error!!.printStackTrace()
                    }catch(e: JSONException) {
                        e.printStackTrace()
                    }
                })

        volleyRequest!!.add(jsonObjectRequest)
        return
    }

    fun setErrorList(route: String, now: Long){
        val trainStatus = TrainStatus()
        trainStatus.route = route
        trainStatus.direction_id = 1
        trainStatus.sch_arr_time = now

        listOfTrains!!.add(trainStatus)
        return
    }


}// Required empty public constructor
