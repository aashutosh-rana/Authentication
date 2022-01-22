package com.bcebhagalpur.CheAshu.useractivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.bcebhagalpur.CheAshu.R
import com.bcebhagalpur.CheAshu.familyfragment.*
import com.google.android.material.tabs.TabLayout

@Suppress("DEPRECATION")
class FamilyActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager
    private lateinit var tabs: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_family)

        viewPager=findViewById(R.id.viewPager)
        tabs=findViewById(R.id.tabs)

        val adapter=
            MyViewPagerAdapter(
                supportFragmentManager
            )
        adapter.addFragment(OneBhkFlat(),"ONE BHK FLAT")
        adapter.addFragment(TwoBhkFlat(),"TWO BHK FLAT")
        adapter.addFragment(ThreeBhkFlat(),"THREE BHK FLAT")
        adapter.addFragment(OneBhkFlatApartment(),"ONE BHK FLAT IN APARTMENT")
        adapter.addFragment(TwoBhkFlatApartment(),"TWO BHK FLAT IN APARTMENT")
        adapter.addFragment(ThreeBhkFlatApartment(),"THREE BHK FLAT IN APARTMENT")
        adapter.addFragment(OnlySingleRoom(),"ONLY SINGLE ROOM")
        adapter.addFragment(OnlyDoubleRoom(),"ONLY DOUBLE ROOM")
        viewPager.adapter=adapter
        tabs.setupWithViewPager(viewPager)
    }

    class MyViewPagerAdapter(manager: FragmentManager): FragmentPagerAdapter(manager){

        private val fragmentList:MutableList<Fragment> = ArrayList()
        private val titleList:MutableList<String> = ArrayList()
        override fun getItem(position: Int): Fragment {

            return fragmentList[position]
        }

        override fun getCount(): Int {

            return fragmentList.size
        }
        fun addFragment(fragment: Fragment, title:String){
            fragmentList.add(fragment)
            titleList.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return titleList[position]
        }

    }
}
