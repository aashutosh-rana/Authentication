package com.bcebhagalpur.CheAshu.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.bcebhagalpur.CheAshu.R
import com.bcebhagalpur.CheAshu.activity.MainActivity2
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_main2.*
import java.lang.Exception


class FeedbackFragment : Fragment() {
    
    private lateinit var etF1:EditText
    private lateinit var etF2:EditText
    private lateinit var etF3:EditText
    private lateinit var etF4:EditText
    private lateinit var etF5:EditText
    private lateinit var ratingBar:RatingBar
    private lateinit var txtRating:TextView
    private lateinit var btnSendFeedback:Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view= inflater.inflate(R.layout.fragment_feedback, container, false)

        etF1=view.findViewById(R.id.etF1)
        etF2=view.findViewById(R.id.etF2)
        etF3=view.findViewById(R.id.etF3)
        etF4=view.findViewById(R.id.etF4)
        etF5=view.findViewById(R.id.etF5)
        ratingBar=view.findViewById(R.id.ratingBar)
        txtRating=view.findViewById(R.id.txtRate)
        btnSendFeedback=view.findViewById(R.id.btnSendFeedback)

            ratingBar.setOnRatingBarChangeListener { _, rating, _ ->
           txtRating.text = rating.toString()
       }
        btnSendFeedback.setOnClickListener {
            try {
                val fa=etF1.text.toString().trim()
                val fb=etF2.text.toString().trim()
                val fc=etF3.text.toString().trim()
                val fd=etF4.text.toString().trim()
                val fe=etF5.text.toString().trim()
                val ratingValue= ratingBar.rating.toString()
                val array= arrayOf(fa,fb,fc,fd,fe)
                val array1= arrayOf("fa","fb","fc","fd","fe")
                val currentUser= FirebaseAuth.getInstance().currentUser!!.uid
                val mDatabase=FirebaseDatabase.getInstance().getReference("RENTERS")
                val currentUserDb=mDatabase.child(currentUser)
                val currentUserDb1=currentUserDb.child("Feedback")


                        currentUserDb1.addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onDataChange(snapshot: DataSnapshot) {
                                for (i in 0..4){
                                    if (!TextUtils.isEmpty(array[i])){
                                        val maxId = snapshot.childrenCount
                                        val anotherChild =currentUserDb1.child((maxId + 1).toString())
                                        anotherChild.child(array1[i]).setValue(array[i])
                                        anotherChild.child("rating").setValue(ratingValue)
                                    }
                                }

                            }
                            override fun onCancelled(error: DatabaseError) {
                            }

                        })
                        Toast.makeText(activity as Context,"Thank you to give us your time",Toast.LENGTH_SHORT).show()
                        startActivity(Intent(activity as Context, MainActivity2::class.java))
                        drawerLayout.closeDrawers()

            }catch (e:Exception){
                e.printStackTrace()
            }

        }

        return view
    }

}
