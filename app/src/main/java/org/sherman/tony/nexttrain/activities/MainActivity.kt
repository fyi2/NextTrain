package org.sherman.tony.nexttrain.activities

import android.app.ActionBar
import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import org.sherman.tony.nexttrain.R
import org.sherman.tony.nexttrain.data.CODE_STATION
import org.sherman.tony.nexttrain.data.CODE_TABBED
import org.sherman.tony.nexttrain.data.CODE_TEST
import org.sherman.tony.nexttrain.data.Globals

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //val activity:Activity = this
        //activity.title = "Next Train"
        //var mactionBar:ActionBar = actionBar
        //mactionBar.setDisplayShowTitleEnabled(true)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        // requestCode = Integer we decide on
        //resultCode = Android Code
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CODE_STATION) {
            if (resultCode == Activity.RESULT_OK) {
                var result = data!!.extras.get("return").toString()
                // Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //        .setAction("Action", null).show()
                Toast.makeText(this, result, Toast.LENGTH_LONG).show()
            }
        }
        if (requestCode == CODE_TABBED) {
            if (resultCode == Activity.RESULT_OK) {
                var result = data!!.extras.get("return").toString()
                Toast.makeText(this, result, Toast.LENGTH_LONG).show()
            }
        }
    }

    fun loadStations(view: View) {
        val clickedButton: Button = view as Button
        val intent = Intent(applicationContext,LoadStationsActivity::class.java)
        if (clickedButton.text.toString() == "All Stations"){
            Globals.stationButton = 1
        } else {
            Globals.stationButton = 0
        }


        startActivityForResult(intent, CODE_STATION)

    }
    fun loadTestPage(view: View) {
        val intent = Intent(applicationContext,TestActivity::class.java)
        intent.putExtra("stations","all")

        startActivityForResult(intent, CODE_TEST)

    }

}
