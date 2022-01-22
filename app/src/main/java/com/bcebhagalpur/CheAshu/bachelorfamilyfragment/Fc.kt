package com.bcebhagalpur.CheAshu.bachelorfamilyfragment

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bcebhagalpur.CheAshu.R
import com.bcebhagalpur.CheAshu.adapter.BachelorAdapter
import com.bcebhagalpur.CheAshu.model.BachelorUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Fc: Fragment() {

    private lateinit var etSearch:SearchView
    lateinit var adapter: BachelorAdapter
    private lateinit var recyclerBachelor: RecyclerView
    lateinit var progressLayout: RelativeLayout
    private lateinit var progressBar: ProgressBar
    private lateinit var txtItemNotFound:TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view= inflater.inflate(R.layout.fragment_1, container, false)

        recyclerBachelor = view.findViewById(R.id.recyclerBachelor)
        etSearch=view.findViewById(R.id.etSearch)
        progressLayout=view.findViewById(R.id.progressLayout)
        progressBar=view.findViewById(R.id.progressBar)
        txtItemNotFound=view.findViewById(R.id.txtItemNotFound)
        progressLayout.visibility= View.VISIBLE

        val layoutManager = LinearLayoutManager(activity as Context)
        layoutManager.reverseLayout
        layoutManager.stackFromEnd
        recyclerBachelor.layoutManager = layoutManager
        val dividerItemDecoration =
            DividerItemDecoration(recyclerBachelor.context, layoutManager.orientation)
        recyclerBachelor.addItemDecoration(dividerItemDecoration)
        val bachelorUser = ArrayList<BachelorUser>()
        adapter = BachelorAdapter(activity as Context, bachelorUser)
        recyclerBachelor.adapter = adapter
        recyclerBachelor.addOnScrollListener(object : RecyclerView.OnScrollListener() {
        })

        getUser()
        searchCity()
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

        val child = FirebaseDatabase.getInstance().reference.child("OWNERS")
        val anotherChild = child.child("BACHELOR OR FAMILY BOTH")
        val anotherChild1=anotherChild.child("3 BHK FLAT")
        anotherChild1.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }
            override fun onDataChange(snapshot: DataSnapshot) {
                val userList = ArrayList<BachelorUser>()
                progressLayout.visibility=View.GONE

                if (snapshot.exists()) {
                    for (p0 in snapshot.children) {
                        for (s0 in p0.children){
                            val p1 = s0.getValue(BachelorUser::class.java)
                            userList.add(0, p1!!)
                        }
                    }
                    adapter = BachelorAdapter(activity as Context, userList)
                    if (snapshot.exists()) {
                        val maxId = snapshot.childrenCount
                        val maxInt = maxId.toInt()
                        for (i in 0..maxInt) {
                            adapter.notifyItemInserted(i)
                            recyclerBachelor.smoothScrollToPosition(i)
                        }

                    }

                    recyclerBachelor.adapter = adapter

                } else {
                    Toast.makeText(activity as Context, " sorry item not found", Toast.LENGTH_SHORT)
                        .show()
                    txtItemNotFound.visibility=View.VISIBLE
                }
            }

        })
    }
}