package com.bcebhagalpur.CheAshu.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import androidx.appcompat.app.AppCompatActivity
import com.bcebhagalpur.CheAshu.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class OwnerActivity2 : AppCompatActivity() {

    private lateinit var checkBathroom: CheckBox
    private lateinit var checkBalcony: CheckBox
    private lateinit var checkWaterSupply: CheckBox
    private lateinit var checkParking: CheckBox
    private lateinit var checkKitchen: CheckBox
    private lateinit var checkPowerSupply: CheckBox
    private lateinit var checkWiFi: CheckBox
    private lateinit var checkPowerBackUp: CheckBox
    private lateinit var checkAirConditioning: CheckBox
    private lateinit var checkLaundry: CheckBox
    private lateinit var checkLift: CheckBox
    private lateinit var checkSecurity: CheckBox
    private lateinit var checkRoWaterSystem: CheckBox
    private lateinit var checkFireExtinguisher: CheckBox
    private lateinit var checkWasteDisposal: CheckBox
    private lateinit var checkClubHouse       : CheckBox
    private lateinit var checkSwimmingPool    : CheckBox
    private lateinit var checkPark            : CheckBox
    private lateinit var checkGymnasium       : CheckBox
    private lateinit var checkKidsPlayArea    : CheckBox
    private lateinit var checkKidsClub        : CheckBox
    private lateinit var checkFlower          : CheckBox
    private lateinit var checkMultipurposeHall: CheckBox
    private lateinit var btnNext:Button

    private lateinit var mDatabaseReference: DatabaseReference
    private lateinit var mDatabase: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_owner2)

        checkBathroom = findViewById(R.id.checkBathroom)
        checkBalcony = findViewById(R.id.checkBalcony)
        checkPowerSupply = findViewById(R.id.checkPowerSupply)
        checkWaterSupply = findViewById(R.id.checkWaterSupply)
        checkParking = findViewById(R.id.checkParking)
        checkKitchen = findViewById(R.id.checkKitchen)
     checkWiFi=findViewById(R.id.checkWiFi)
     checkPowerBackUp=findViewById(R.id.checkPowerBackUp)
     checkAirConditioning =findViewById(R.id.checkAirConditioning)
     checkLaundry =findViewById(R.id.checkLaundry)
     checkLift =findViewById(R.id.checkLift)
     checkSecurity =findViewById(R.id.checkSecurity)
     checkRoWaterSystem =findViewById(R.id.checkRoWaterSystem)
     checkFireExtinguisher =findViewById(R.id.checkFireExtinguisher)
    checkWasteDisposal=findViewById(R.id.checkWasteDisposal)
     checkClubHouse    =findViewById(R.id.checkClubHouse)
     checkSwimmingPool =findViewById(R.id.checkSwimmingPool)
     checkPark         =findViewById(R.id.checkPark)
     checkGymnasium    =findViewById(R.id.checkGymnasium)
     checkKidsPlayArea =findViewById(R.id.checkKidsPlayArea)
     checkKidsClub     =findViewById(R.id.checkKidsClub)
     checkFlower       =findViewById(R.id.checkFlowers)
     checkMultipurposeHall=findViewById(R.id.checkMultipurposeHall)
        btnNext=findViewById(R.id.btnNext)
        btnNext.setOnClickListener { updateUi2() }

        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mDatabase.reference.child("OWNERS")

    }

    private fun updateUi2(){
        val intent1 = Intent(this@OwnerActivity2, OwnerActivity4::class.java)
        val intent=intent
        val minRent=intent.getStringExtra("minRent")
        val maxRent=intent.getStringExtra("maxRent")
        val fullName=intent.getStringExtra("fullName")
        val email= intent.getStringExtra("email")
        val number=intent.getStringExtra("number")
        val state=intent.getStringExtra("state")
        val city=intent.getStringExtra("city")
        val colony=intent.getStringExtra("colony")
        val landmark=intent.getStringExtra("landmark")
        val spinnerString=intent.getStringExtra("spinnerString")
        val itemSelected=intent.getStringExtra("itemSelected")
        intent1.putExtra("minRent",minRent)
        intent1.putExtra("maxRent",maxRent)
        intent1.putExtra("fullName",fullName)
        intent1.putExtra("email",email)
        intent1.putExtra("number",number)
        intent1.putExtra("state",state)
        intent1.putExtra("city",city)
        intent1.putExtra("colony",colony)
        intent1.putExtra("landmark",landmark)
        intent1.putExtra("spinnerString",spinnerString)
        intent1.putExtra("itemSelected",itemSelected)

        val checkBoxArray = arrayOf(checkBathroom,checkBalcony,checkPowerSupply,checkWaterSupply,checkParking,checkKitchen,
            checkWiFi,checkPowerBackUp,checkAirConditioning,checkLaundry,checkLift,checkSecurity,checkRoWaterSystem,checkFireExtinguisher,checkWasteDisposal,
            checkClubHouse,checkSwimmingPool,checkPark,checkGymnasium,checkKidsPlayArea,checkKidsClub,checkFlower,checkMultipurposeHall)
        val facilitiesArray = arrayOf("Bathroom","Balcony","powerSupply","waterSupply","Parking","Kitchen",
            "Wi_Fi","PowerBackUp","AirConditioning","Laundry","Lift","Security","RoWaterSystem","FireExtinguisher","WasteDisposal",
            "ClubHouse","SwimmingPool","Park","Gymnasium","KidsPlayArea","KidsClub","Flower","MultipurposeHall")

        for (i in 0..22){
            if (checkBoxArray[i].isChecked){
                intent1.putExtra(facilitiesArray[i],"Yes")
            }else{
                intent1.putExtra(facilitiesArray[i],"No")
            }
        }
        intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent1)
    }
}

