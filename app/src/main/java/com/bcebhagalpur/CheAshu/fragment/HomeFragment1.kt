package com.bcebhagalpur.CheAshu.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import com.bcebhagalpur.CheAshu.R
import com.bcebhagalpur.CheAshu.activity.OwnerActivity3
import com.bcebhagalpur.CheAshu.activity.UserActivity

class HomeFragment1 : Fragment() {

    private lateinit var btnRental: Button
    private lateinit var btnOwner: Button
    private lateinit var imgPhoto: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view= inflater.inflate(R.layout.fragment_home1, container, false)

        btnOwner=view.findViewById(R.id.btnOwner)
        btnRental=view.findViewById(R.id.btnRental)
        imgPhoto=view.findViewById(R.id.imgPhoto)

        btnRental()
        btnOwner()
        return view
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun btnRental(){
        btnRental.setOnTouchListener(View.OnTouchListener { _, motionEvent ->
            when (motionEvent.action){
                MotionEvent.ACTION_DOWN
                -> {
                    btnRental.setBackgroundColor(R.drawable.blue_btn_bg)
                }
                MotionEvent.ACTION_UP -> {
                    btnRental.setBackgroundResource(R.color.com_facebook_button_background_color)
                    startActivity(Intent(activity as Context, UserActivity::class.java))
                }

            }
            return@OnTouchListener false
        })
    }
    @SuppressLint("ClickableViewAccessibility")
    private fun btnOwner(){
        btnOwner.setOnTouchListener(View.OnTouchListener { _, motionEvent ->
            when (motionEvent.action){
                MotionEvent.ACTION_DOWN
                -> {
                    btnOwner.setBackgroundColor(R.drawable.blue_btn_bg)
                }
                MotionEvent.ACTION_UP -> {
                    btnOwner.setBackgroundResource(R.color.tw__composer_red)
                        startActivity(Intent(activity as Context,OwnerActivity3::class.java))
                }

            }
            return@OnTouchListener false
        })

    }

}
