package org.sherman.tony.nexttrain.activities

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import org.sherman.tony.nexttrain.R
import org.sherman.tony.nexttrain.adapters.dBHandler

class TestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
    }

    fun returnHome(view: View){
        var intent = Intent(this, MainActivity::class.java)
        var returnIntent = this.intent

        returnIntent.putExtra("return", "return from Test")
        setResult(Activity.RESULT_OK, returnIntent)
        finish()
        // startActivity(intent) Used for simple intent calls
    }

    fun getRecord(view: View){
        val station = "Framingham"
        var db = dBHandler(applicationContext)

        var returnCode = db.getRecord(station)
        Toast.makeText(this, "read Record got ${returnCode.station}",Toast.LENGTH_LONG).show()
    }

    fun updateRecord(view: View){
        var station = "Framingham"
        var db = dBHandler(applicationContext)

        var returnCode = db.touchRecord(station)
        station = "Southborough"
        returnCode = db.touchRecord(station)
        Toast.makeText(this, "read Record got ${returnCode.recent}",Toast.LENGTH_LONG).show()
    }
    fun getRecent(view: View){
        var db = dBHandler(applicationContext)
        var stationList = db.getRecent()
        for (s in stationList!!.iterator()){
            println("TOUCHED STATION + ${s.station}")
        }

    }
}
