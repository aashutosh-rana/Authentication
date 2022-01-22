package com.bcebhagalpur.CheAshu.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bcebhagalpur.CheAshu.R
import com.bcebhagalpur.CheAshu.activity.DetailActivity
import com.bcebhagalpur.CheAshu.model.BachelorUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import java.lang.NullPointerException
import kotlin.collections.ArrayList

class DeleteAdapter(val context: Context, private val bachelorUserList: ArrayList<BachelorUser>,val s1:String, val s2:String) : RecyclerView.Adapter<DeleteAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        var image: ImageView = itemView.findViewById(R.id.image)
        var txtMinRent: TextView = itemView.findViewById(R.id.txtMinRent)
        var txtMaxRent: TextView = itemView.findViewById(R.id.txtMaxRent)
        var txtState: TextView = itemView.findViewById(R.id.txtState)
        var txtCity: TextView = itemView.findViewById(R.id.txtCity)
        var txtColony: TextView = itemView.findViewById(R.id.txtColony)
        var description: CardView =itemView.findViewById(R.id.description)
        var btnDelete: Button =itemView.findViewById(R.id.btnDelete)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView= LayoutInflater.from(context).inflate(R.layout.delete_recycler,parent,false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return bachelorUserList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val bachelor=bachelorUserList[position]
        holder.txtMinRent.text=bachelor.minRent
        holder.txtMaxRent.text=bachelor.maxRent
        holder.txtState.text=bachelor.state
        holder.txtCity.text=bachelor.city
        holder.txtColony.text=bachelor.colony

        holder.btnDelete.setOnClickListener {
            val storageRef = FirebaseStorage.getInstance().getReference("Uploads")
            val desertRef = storageRef.child(bachelor.iPath.toString())
            val desertRef1=storageRef.child(bachelor.vPath.toString())
            desertRef1.delete().addOnSuccessListener {

            }.addOnFailureListener {
                it.printStackTrace()
            }
            desertRef.delete().addOnSuccessListener {

            }.addOnFailureListener {
                it.printStackTrace()
            }
            val myDatabase=FirebaseDatabase.getInstance().getReference("OWNERS")
            val myDatabase1=myDatabase.child(s1)
            val myDatabase2=myDatabase1.child(s2)
            try {
                val myDatabase3 = myDatabase2.child(FirebaseAuth.getInstance().currentUser!!.uid)
                myDatabase3.child(bachelor.id.toString()).removeValue()
            }catch (e:NullPointerException){
                e.printStackTrace()
            }
            Toast.makeText(context,"data deleted",Toast.LENGTH_SHORT).show()
        }

        val imageView=holder.image
        val picasso= Picasso.get().load(bachelor.image).fit()
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
            val intent= Intent(context, DetailActivity::class.java)
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
        }
    }

}