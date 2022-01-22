package com.bcebhagalpur.CheAshu.activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.FragmentTransaction
import com.bcebhagalpur.CheAshu.R
import com.bcebhagalpur.CheAshu.fragment.HistoryFragment
import com.bcebhagalpur.CheAshu.fragment.HomeFragment
import com.bcebhagalpur.CheAshu.fragment.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class UserActivity : AppCompatActivity() {

    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var homeFragment: HomeFragment
    private lateinit var profileFragment: ProfileFragment
    private lateinit var historyFragment: HistoryFragment
    private var previousMenuItem: MenuItem? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        bottomNavigationView=findViewById(R.id.bottomNavigationView)
        myHomeFragment()
        bottom()
    }
    private fun bottom(){
        bottomNavigationView.setOnNavigationItemSelectedListener {

            if (previousMenuItem != null){
                previousMenuItem?.isChecked = false
            }

            it.isCheckable = true
            it.isChecked = true
            previousMenuItem = it

            when(it.itemId){
                R.id.home->{
                    myHomeFragment()
                }
                R.id.favourite->{
                    historyFragment= HistoryFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.frameLayout,historyFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit()
                    supportActionBar?.hide()
                }
                R.id.profile->{
                    profileFragment= ProfileFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.frameLayout,profileFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit()
                    supportActionBar?.hide()
                }
            }
            true
        }
    }
    @SuppressLint("ResourceAsColor")
    private fun myHomeFragment(){
        homeFragment = HomeFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayout, homeFragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit()
        supportActionBar?.title ="Play"
        supportActionBar?.show()
    }
    override fun onBackPressed() {
        when(supportFragmentManager.findFragmentById(R.id.frameLayout)){
            !is HomeFragment-> {
                myHomeFragment()
            }
            else->super.onBackPressed()
        }
    }

}
