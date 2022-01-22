package com.bcebhagalpur.CheAshu.fragment

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.bcebhagalpur.CheAshu.R

class ProfileFragment : Fragment(){

    private lateinit var btnOpp:Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        btnOpp=view.findViewById(R.id.btnOpp)
        btnOpp.setOnClickListener {
            val mAlertDialogBuilder = AlertDialog.Builder(activity as Context)
            mAlertDialogBuilder.setTitle("Message")
            mAlertDialogBuilder.setIcon(R.drawable.ic_opportunity)
            mAlertDialogBuilder.setMessage("Sorry to say this service currently unavailable.")
            mAlertDialogBuilder.setCancelable(false)
            mAlertDialogBuilder.setPositiveButton("Got it"){_,_->
            }
            mAlertDialogBuilder.create().show()
        }

        return view
    }
}