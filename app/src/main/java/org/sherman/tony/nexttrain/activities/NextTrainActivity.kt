package org.sherman.tony.nexttrain.activities

import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_next_train.*
import org.sherman.tony.nexttrain.R
import org.sherman.tony.nexttrain.adapters.TabbedMenuAdapter



class NextTrainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_next_train)
        //        setSupportActionBar(toolbar)
        var sectionAdapter: TabbedMenuAdapter

        sectionAdapter = TabbedMenuAdapter(supportFragmentManager)
        viewPagerID.adapter = sectionAdapter
        tabs.setupWithViewPager(viewPagerID)
        tabs.setTabTextColors(Color.WHITE, Color.GREEN)

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
