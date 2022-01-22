package com.bcebhagalpur.CheAshu.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.bcebhagalpur.CheAshu.R
import com.bcebhagalpur.CheAshu.useractivity.*

class HomeFragment : Fragment() {

    private lateinit var btnShop: Button
    private lateinit var btnFamily: Button
    private lateinit var btnPg: Button
    private lateinit var btnBachelorBoys: Button
    private lateinit var btnBachelorGirls: Button
    private lateinit var btnBachelorGirlsBoys: Button
    private lateinit var btnBachelorFamily: Button
    private lateinit var btnMarriageHall: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       val view= inflater.inflate(R.layout.fragment_home, container, false)

        btnShop=view.findViewById(R.id.btnShop)
        btnFamily=view.findViewById(R.id.btnFamily)
        btnPg=view.findViewById(R.id.btnPg)
        btnBachelorBoys=view.findViewById(R.id.btnBachelorBoys)
        btnBachelorGirls=view.findViewById(R.id.btnBachelorGirls)
        btnBachelorGirlsBoys=view.findViewById(R.id.btnBachelorBoysGirls)
        btnBachelorFamily=view.findViewById(R.id.btnBachelorFamily)
        btnMarriageHall=view.findViewById(R.id.btnMarriageHall)

        conditionalNavigation()

        return view
    }

    private fun conditionalNavigation() {
        val idList= arrayOf(btnShop,btnFamily,btnPg,btnBachelorBoys,btnBachelorGirls,btnBachelorGirlsBoys,btnBachelorFamily,btnMarriageHall)
        val activityList= arrayOf(
            ShopActivity(),
            FamilyActivity(),
            PgActivity(),
            BachelorBoysActivity(),
            BachelorGirlsActivity(),
            BachelorGirlsBoysActivity(),
            BachelorFamilyActivity(),
            MarriageHallActivity()
        )

        for(i in 0..7){
            idList[i].setOnClickListener {
                val intent= Intent(activity as Context ,activityList[i]::class.java)
                startActivity(intent)
            }
        }
    }


}
