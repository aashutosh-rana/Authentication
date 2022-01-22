package com.bcebhagalpur.CheAshu.adapter

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.telephony.SmsManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.RecyclerView
import com.bcebhagalpur.CheAshu.R
import com.bcebhagalpur.CheAshu.activity.DetailActivity
import com.bcebhagalpur.CheAshu.model.BachelorUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import java.lang.Exception
import java.lang.IllegalArgumentException
import java.util.*
import kotlin.collections.ArrayList

class BachelorAdapter(val context: Context, private val bachelorUserList: ArrayList<BachelorUser>) : RecyclerView.Adapter<BachelorAdapter.MyViewHolder>() {

    var cityFilter = ArrayList<BachelorUser>()

    init {
        cityFilter = bachelorUserList
    }

    class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
       var image:ImageView= itemView.findViewById(R.id.image)
       var txtMinRent:TextView = itemView.findViewById(R.id.txtMinRent)
         var txtMaxRent:TextView = itemView.findViewById(R.id.txtMaxRent)
        var txtState:TextView = itemView.findViewById(R.id.txtState)
         var txtCity:TextView = itemView.findViewById(R.id.txtCity)
      var txtColony:TextView = itemView.findViewById(R.id.txtColony)
         var description:CardView=itemView.findViewById(R.id.description)
        var btnCallOwner1:Button=itemView.findViewById(R.id.btnOwnerCall1)
        var btnGetLocation11:Button=itemView.findViewById(R.id.btnGetLocation1)
        var btnSendMessage:Button=itemView.findViewById(R.id.btnSendMessage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
val itemView=LayoutInflater.from(context).inflate(R.layout.bachelor_item,parent,false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
     return cityFilter.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val bachelor=cityFilter[position]
        holder.txtMinRent.text=bachelor.minRent
        holder.txtMaxRent.text=bachelor.maxRent
        holder.txtState.text=bachelor.state
        holder.txtCity.text=bachelor.city
        holder.txtColony.text=bachelor.colony
        holder.btnCallOwner1.setOnClickListener {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(context as Activity, arrayOf(Manifest.permission.CALL_PHONE), 200)
            }
            else{
                val intent1 = Intent(Intent.ACTION_CALL, Uri.fromParts("tel", bachelor.number, null))
                context.startActivity(intent1)
            }


        }
        holder.btnGetLocation11.setOnClickListener {
            try {
                val mUriIntent=Uri.parse("geo:0,0?q=${bachelor.state!!+" "+bachelor.city+" "+bachelor.colony+" "+bachelor.landmark}")
                val mMapIntent=Intent(Intent.ACTION_VIEW,mUriIntent)
                mMapIntent.setPackage("com.google.android.apps.maps")
                context.startActivity(mMapIntent)
            }catch (e: Exception){
                e.printStackTrace()
            }

        }

        holder.btnSendMessage.setOnClickListener {
            if (ActivityCompat.checkSelfPermission(context,Manifest.permission.SEND_SMS)!=PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(context as Activity, arrayOf(Manifest.permission.SEND_SMS),2001)
            }
            else{
                sendMessage(bachelor.number.toString())
            }
        }
        val imageView=holder.image
      val picasso=Picasso.get().load(bachelor.image).fit()
        picasso.into(imageView)
        val facilitiesArray = arrayOf(
            "Bathroom",
            "Balcony",
            "powerSupply",
            "waterSupply",
            "Parking",
            "Kitchen",
            "Wi_Fi",
            "PowerBackUp",
            "AirConditioning",
            "Laundry",
            "Lift",
            "Security",
            "RoWaterSystem",
            "FireExtinguisher",
            "WasteDisposal",
            "ClubHouse",
            "SwimmingPool",
            "Park",
            "Gymnasium",
            "KidsPlayArea",
            "KidsClub",
            "Flower",
            "MultipurposeHall"
        )
        val facilitiesArray1 = arrayOf(
            bachelor.Bathroom,
            bachelor.Balcony,
            bachelor.powerSupply,
            bachelor.waterSupply,
            bachelor.Parking,
            bachelor.Kitchen,
            bachelor.Wi_Fi,
            bachelor.PowerBackUp,
            bachelor.AirConditioning,
            bachelor.Laundry,
            bachelor.Lift,
            bachelor.Security,
            bachelor.RoWaterSystem,
            bachelor.FireExtinguisher,
            bachelor.WasteDisposal,
            bachelor.ClubHouse,
            bachelor.SwimmingPool,
            bachelor.Park,
            bachelor.Gymnasium,
            bachelor.KidsPlayArea,
            bachelor.KidsClub,
            bachelor.Flower,
            bachelor.MultipurposeHall
        )
        holder.description.setOnClickListener {
            val intent=Intent(context,DetailActivity::class.java)
            for (i in 0..22){
                intent.putExtra(facilitiesArray[i],facilitiesArray1[i])
            }
            intent.putExtra("imageUrl",bachelor.image)
            intent.putExtra("landmark",bachelor.landmark)
            intent.putExtra("number",bachelor.number)
            intent.putExtra("rule",bachelor. rule)
            intent.putExtra("video",bachelor.video)
            intent.putExtra("fullName",bachelor.fullName)
            intent.putExtra("id",bachelor.id)
            intent.putExtra("minRent",bachelor. minRent)
            intent.putExtra("maxRent",bachelor.maxRent)
            intent.putExtra("state",bachelor.state)
            intent.putExtra("city",bachelor.city)
            intent.putExtra("colony",bachelor.colony)
            intent.putExtra("email",bachelor.email)
            context.startActivity(intent)

            val dataRef= FirebaseDatabase.getInstance().getReference("RENTERS")
            val dataRef1= dataRef.child(FirebaseAuth.getInstance().currentUser!!.uid)
            val dataRef2= dataRef1.child("HISTORY")
            dataRef2.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {}

                override fun onDataChange(snapshot: DataSnapshot) {
                    val maxId = snapshot.childrenCount
                    val anotherChild = dataRef2.child(bachelor.id.toString())
                    anotherChild.child("id").setValue(bachelor.id.toString())
                    anotherChild.child("fullName").setValue(bachelor.fullName.toString())
                    anotherChild.child("email").setValue(bachelor.email)
                    anotherChild.child("number").setValue(bachelor.number)
                    anotherChild.child("state").setValue(bachelor.state)
                    anotherChild.child("city").setValue(bachelor.city)
                    anotherChild.child("colony").setValue(bachelor.colony)
                    anotherChild.child("landmark").setValue(bachelor.landmark)
                    anotherChild.child("minRent").setValue(bachelor.minRent)
                    anotherChild.child("maxRent").setValue(bachelor.maxRent)
                    anotherChild.child("rule").setValue(bachelor.rule)
                    anotherChild.child("video").setValue(bachelor.video)
                    anotherChild.child("image").setValue(bachelor.image)
                    anotherChild.child("vPath").setValue(bachelor.vPath)
                    anotherChild.child("iPath").setValue(bachelor.iPath)
                    for (i in 0..22) {
                        anotherChild.child(facilitiesArray[i]).setValue(facilitiesArray1[i])
                    }

                }

            })
        }
    }
    fun getFilter():Filter{
        return object :Filter(){
            override fun performFiltering(constraint: CharSequence?): FilterResults {

                val charSearch = constraint.toString()
                cityFilter = if (charSearch.isEmpty()) {
                    bachelorUserList
                } else {
                    val resultList = ArrayList<BachelorUser>()
                    for (row in bachelorUserList) {
                        if (row.city!!.toLowerCase(Locale.ROOT).contains(charSearch.toLowerCase(Locale.ROOT)) || row.state!!.toLowerCase(Locale.ROOT).contains(charSearch
                                .toLowerCase(Locale.ROOT)) || row.colony!!.toLowerCase(Locale.ROOT).contains(charSearch.toLowerCase(Locale.ROOT))
                            || row.landmark!!.toLowerCase(Locale.ROOT).contains(charSearch.toLowerCase(Locale.ROOT))) {
                            resultList.add(row)
                        }
                    }
                    resultList
                }
                val filterResults = FilterResults()
                filterResults.values = cityFilter
                return filterResults

            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {

                cityFilter = results!!.values as java.util.ArrayList<BachelorUser>
                notifyDataSetChanged()

            }

        }
    }
    private fun sendMessage(number:String){
        val inflateView=LayoutInflater.from(context).inflate(R.layout.custom_view,null)
        val etSendMessage=inflateView.findViewById<EditText>(R.id.etSendMessage)
        val alertDialog=AlertDialog.Builder(context)
        alertDialog.setTitle("write sms to owner")
        alertDialog.setView(inflateView)
        alertDialog.setCancelable(false)
        alertDialog.setNegativeButton("Cancel"){dialog, which ->
            Toast.makeText(context,"you cancel the sms",Toast.LENGTH_SHORT).show()
        }
        alertDialog.setPositiveButton("Send"){dialog, which ->
            val message=etSendMessage.text.toString()
            val sms=SmsManager.getDefault()
            try {
                sms.sendTextMessage(number,"Me",message,null,null)
            }catch (e:IllegalArgumentException){
                Toast.makeText(context,"write something",Toast.LENGTH_SHORT).show()
            }

        }
        val dialog=alertDialog.create()
        dialog.show()
    }
}

