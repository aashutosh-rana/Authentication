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
import com.bcebhagalpur.CheAshu.adapter.HistoryAdapter
import com.bcebhagalpur.CheAshu.model.BachelorUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class HistoryFragment : Fragment() {
    private lateinit var btnDeleteAll:Button
    private lateinit var etSearch: SearchView
    lateinit var adapter: HistoryAdapter
    private lateinit var recyclerHistory: RecyclerView
    lateinit var progressLayout: RelativeLayout
    private lateinit var progressBar: ProgressBar
    private lateinit var txtItemNotFound: TextView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

       val view= inflater.inflate(R.layout.fragment_history, container, false)

        btnDeleteAll=view.findViewById(R.id.btnDeleteAll)
        recyclerHistory = view.findViewById(R.id.recyclerHistory)
        etSearch=view.findViewById(R.id.etSearch)
        progressLayout=view.findViewById(R.id.progressLayout)
        progressBar=view.findViewById(R.id.progressBar)
        txtItemNotFound=view.findViewById(R.id.txtItemNotFound)
        progressLayout.visibility= View.VISIBLE

        val layoutManager = LinearLayoutManager(activity as Context)
        layoutManager.reverseLayout
        layoutManager.stackFromEnd
       recyclerHistory.layoutManager = layoutManager
        val dividerItemDecoration =
            DividerItemDecoration(recyclerHistory.context, layoutManager.orientation)
        recyclerHistory.addItemDecoration(dividerItemDecoration)
        val bachelorUser = ArrayList<BachelorUser>()
        adapter = HistoryAdapter(activity as Context, bachelorUser)
        recyclerHistory.adapter = adapter
       recyclerHistory.addOnScrollListener(object : RecyclerView.OnScrollListener() {
        })

        getUser()
        searchCity()
        btnDeleteAll.setOnClickListener {
try {
    val child = FirebaseDatabase.getInstance().reference.child("RENTERS")
    val anotherChild = child.child(FirebaseAuth.getInstance().currentUser!!.uid)
    anotherChild.child("HISTORY").removeValue()
    Toast.makeText(activity as Context,"Your all history deleted",Toast.LENGTH_SHORT).show()
     }catch (e:Exception){
    e.printStackTrace()
}

        }

        return view
    }


    private fun searchCity(){

        etSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                adapter.getFilter().filter(newText)
                return false
            }

        })
    }
    private fun getUser() {

        try {

            val child = FirebaseDatabase.getInstance().reference.child("RENTERS")
            val anotherChild = child.child(FirebaseAuth.getInstance().currentUser!!.uid)
            val anotherChild1 = anotherChild.child("HISTORY")
            anotherChild1.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {

                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    val userList = ArrayList<BachelorUser>()
                    progressLayout.visibility = View.GONE

                    if (snapshot.exists()) {
                        for (p0 in snapshot.children) {
                            val p1 = p0.getValue(BachelorUser::class.java)
                            userList.add(0, p1!!)
                        }
                        try {

                        adapter = HistoryAdapter(activity as Context, userList)
                        if (snapshot.exists()) {
                            val maxId = snapshot.childrenCount
                            val maxInt = maxId.toInt()
                            for (i in 0..maxInt) {
                                adapter.notifyItemInserted(i)
                                recyclerHistory.smoothScrollToPosition(i)
                            }

                        }

                        recyclerHistory.adapter = adapter
                            }catch (e:TypeCastException){
                            e.printStackTrace()
                        }

                    }
                    else {
                        try {
                            Toast.makeText(activity as Context, "no history found", Toast.LENGTH_SHORT).show()
                            txtItemNotFound.visibility = View.VISIBLE
                        }catch (e:TypeCastException){
                            e.printStackTrace()
                        }
                    }
                }

            })
        }catch (e:TypeCastException){
            e.printStackTrace()
        }
    }


}
