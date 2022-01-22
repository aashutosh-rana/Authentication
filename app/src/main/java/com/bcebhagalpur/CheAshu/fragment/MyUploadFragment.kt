package com.bcebhagalpur.CheAshu.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bcebhagalpur.CheAshu.R
import com.bcebhagalpur.CheAshu.adapter.DeleteAdapter
import com.bcebhagalpur.CheAshu.model.BachelorUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MyUploadFragment : Fragment() {
    private lateinit var recyclerProfile: RecyclerView
    lateinit var adapter: DeleteAdapter
    private lateinit var spinnerRoomType1: Spinner
    private lateinit var spinnerRoomType2: Spinner
    private lateinit var btnGetResult:Button
    private lateinit var txtUploading:TextView
    val cat1= arrayOf("select one","SHOWROOM","SHOP","SHOWROOM OR SHOP BOTH")
    val cat2= arrayOf("select one","1 BHK FLAT","2 BHK FLAT","3 BHK FLAT","1 BHK FLAT IN APARTMENT","2 BHK FLAT IN APARTMENT","3 BHK FLAT IN APARTMENT",
        "ONLY DOUBLE ROOM","ONLY SINGLE ROOM")
    val cat3= arrayOf("select one","BOYS SINGLE BED","BOYS DOUBLE BED","BOYS TRIPLE BED","GIRLS SINGLE BED","GIRLS DOUBLE BED","GIRLS TRIPLE BED",
        "FOR BOTH SINGLE BED","FOR BOTH DOUBLE BED","FOR BOTH TRIPLE BED")
    val room= arrayOf("select one","SHOP OR SHOWROOM","FAMILY","PG","BACHELOR ONLY FOR BOYS","BACHELOR ONLY FOR GIRLS","BACHELOR FOR BOTH GIRLS AND BOYS",
        "BACHELOR OR FAMILY BOTH")
    private val cat4= arrayOf("first select category")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view= inflater.inflate(R.layout.fragment_my_upload, container, false)
        btnGetResult=view.findViewById(R.id.btnGetResult)
        txtUploading=view.findViewById(R.id.txtUploading)
        spinnerRoomType1=view.findViewById(R.id.spinnerRoomType1)
        spinnerRoomType2=view.findViewById(R.id.spinnerRoomType2)
        recyclerProfile = view.findViewById(R.id.recyclerProfile)
        val arrayAdp= ArrayAdapter(activity as Context,android.R.layout.simple_spinner_dropdown_item,room)
        spinnerRoomType1.adapter=arrayAdp

        val arrayAdp2= ArrayAdapter(activity as Context,android.R.layout.simple_spinner_dropdown_item,cat4)
        spinnerRoomType2.adapter=arrayAdp2
        spinnerSet()

           btnGetResult.setOnClickListener{
               txtUploading.visibility=View.VISIBLE
               getUser()
           }

        return view
    }

    private fun getUser() {

        val spinnerString1=spinnerRoomType1.selectedItem.toString()

        val spinnerString2 = spinnerRoomType2.selectedItem.toString()

        val layoutManager = LinearLayoutManager(activity as Context)
        layoutManager.reverseLayout
        layoutManager.stackFromEnd
        recyclerProfile.layoutManager = layoutManager
        val dividerItemDecoration =
            DividerItemDecoration(recyclerProfile.context, layoutManager.orientation)
        recyclerProfile.addItemDecoration(dividerItemDecoration)
        val bachelorUser = ArrayList<BachelorUser>()
        adapter =
            DeleteAdapter(activity as Context, bachelorUser, spinnerString1,spinnerString2)
        recyclerProfile.adapter = adapter
        recyclerProfile.addOnScrollListener(object : RecyclerView.OnScrollListener() {
        })

        val child = FirebaseDatabase.getInstance().reference.child("OWNERS")
        val anotherChild = child.child(spinnerString1)
        val anotherChild1 = anotherChild.child(spinnerString2)
        try {
            val anotherChild2 = anotherChild1.child(FirebaseAuth.getInstance().currentUser!!.uid)
            anotherChild2.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {

                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    val userList = ArrayList<BachelorUser>()
                    txtUploading.visibility=View.GONE
                    if (snapshot.exists()) {
                        for (p0 in snapshot.children) {
                            val p1 = p0.getValue(BachelorUser::class.java)
                            userList.add(0, p1!!)
                        }
                        try {
                            adapter = DeleteAdapter(activity as Context, userList,spinnerString1,spinnerString2)
                        }catch (E: TypeCastException){
                            E.printStackTrace()
                        }

                        if (snapshot.exists()) {
                            val maxId = snapshot.childrenCount
                            val maxInt = maxId.toInt()
                            for (i in 0..maxInt) {
                                adapter.notifyItemInserted(i)
                                recyclerProfile.smoothScrollToPosition(i)
                            }

                        }

                        recyclerProfile.adapter = adapter

                    } else {
                        try{
                            Toast.makeText(
                                activity,
                                " sorry item not found",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }catch (e:TypeCastException){
                            e.printStackTrace()
                        }

                    }
                }

            })
        }catch (e:java.lang.NullPointerException){
            Toast.makeText(activity,"login with those method by which you previously loggedin",
                Toast.LENGTH_LONG).show()
        }

    }

    private fun spinnerSet() {
        spinnerRoomType1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when (position) {
                    1 -> {
                        val arrayAdp1= ArrayAdapter(activity as Context,android.R.layout.simple_spinner_dropdown_item,cat1)
                        spinnerRoomType2.adapter=arrayAdp1
                    }
                    2 -> {
                        val arrayAdp1= ArrayAdapter(activity as Context,android.R.layout.simple_spinner_dropdown_item,cat2)
                        spinnerRoomType2.adapter=arrayAdp1
                    }
                    3 -> {
                        val arrayAdp1= ArrayAdapter(activity as Context,android.R.layout.simple_spinner_dropdown_item,cat3)
                        spinnerRoomType2.adapter=arrayAdp1
                    }
                    4 -> {
                        val arrayAdp1= ArrayAdapter(activity as Context,android.R.layout.simple_spinner_dropdown_item,cat2)
                        spinnerRoomType2.adapter=arrayAdp1
                    }
                    5 -> {
                        val arrayAdp1= ArrayAdapter(activity as Context,android.R.layout.simple_spinner_dropdown_item,cat2)
                        spinnerRoomType2.adapter=arrayAdp1
                    }
                    6 -> {
                        val arrayAdp1= ArrayAdapter(activity as Context,android.R.layout.simple_spinner_dropdown_item,cat2)
                        spinnerRoomType2.adapter=arrayAdp1
                    }
                    7->{
                        val arrayAdp1= ArrayAdapter(activity as Context,android.R.layout.simple_spinner_dropdown_item,cat2)
                        spinnerRoomType2.adapter=arrayAdp1
                    }
                    else->{
                        val cat4= arrayOf("first select category")
                        val arrayAdp1= ArrayAdapter(activity as Context,android.R.layout.simple_spinner_dropdown_item,cat4)
                        spinnerRoomType2.adapter=arrayAdp1
                    }

                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

    }



}

