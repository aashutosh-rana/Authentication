package com.bcebhagalpur.CheAshu.useractivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.bcebhagalpur.CheAshu.R
import com.bcebhagalpur.CheAshu.pgfragment.*
import com.google.android.material.tabs.TabLayout

class PgActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager
    private lateinit var tabs: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pg)

        viewPager=findViewById(R.id.viewPager)
        tabs=findViewById(R.id.tabs)

        val adapter=
            MyViewPagerAdapter(
                supportFragmentManager
            )
        adapter.addFragment(Fragment1(),"BOYS SINGLE BED")
        adapter.addFragment(Fragment2(),"BOYS DOUBLE BED")
        adapter.addFragment(Fragment3(),"BOYS TRIPLE BED")
        adapter.addFragment(Fragment4(),"GIRLS SINGLE BED")
        adapter.addFragment(Fragment5(),"GIRLS DOUBLE BED")
        adapter.addFragment(Fragment6(),"GIRLS TRIPLE BED")
        adapter.addFragment(Fragment7(),"BOYS AND GIRLS SINGLE BED")
        adapter.addFragment(Fragment8(),"BOYS AND GIRLS DOUBLE BED")
        adapter.addFragment(Fragment9(),"BOYS AND GIRLS TRIPLE BED")
        viewPager.adapter=adapter
        tabs.setupWithViewPager(viewPager)

    }

    @Suppress("DEPRECATION")
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
