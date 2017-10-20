package org.sherman.tony.nexttrain.adapters

import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import org.sherman.tony.nexttrain.fragments.InboundFragment
import org.sherman.tony.nexttrain.fragments.OutboundFragment

/**
 * Created by Admin on 10/20/2017.
 */
class TabbedMenuAdapter(fm:FragmentManager): FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): android.support.v4.app.Fragment {
        when(position) {
            0 -> return InboundFragment()
            1 -> return OutboundFragment()
        }
        return null!!
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence {
        when(position){
            0 -> return "INBOUND"
            1 -> return "OUTBOUND"
        }
        return null!!
    }

}