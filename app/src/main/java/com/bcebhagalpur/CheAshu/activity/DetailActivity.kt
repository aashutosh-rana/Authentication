package com.bcebhagalpur.CheAshu.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color.GREEN
import android.graphics.Color.RED
import android.net.Uri
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.bcebhagalpur.CheAshu.R
import java.lang.Exception

class DetailActivity : AppCompatActivity() {
    private lateinit var checkBathroom: TextView
    private lateinit var checkBalcony: TextView
    private lateinit var checkWaterSupply: TextView
    private lateinit var checkParking: TextView
    private lateinit var checkKitchen: TextView
    private lateinit var checkPowerSupply: TextView
    private lateinit var checkWiFi: TextView
    private lateinit var checkPowerBackUp: TextView
    private lateinit var checkAirConditioning: TextView
    private lateinit var checkLaundry: TextView
    private lateinit var checkLift: TextView
    private lateinit var checkSecurity: TextView
    private lateinit var checkRoWaterSystem: TextView
    private lateinit var checkFireExtinguisher: TextView
    private lateinit var checkWasteDisposal: TextView
    private lateinit var checkClubHouse: TextView
    private lateinit var checkSwimmingPool: TextView
    private lateinit var checkPark: TextView
    private lateinit var checkGymnasium: TextView
    private lateinit var checkKidsPlayArea: TextView
    private lateinit var checkKidsClub: TextView
    private lateinit var checkFlower: TextView
    private lateinit var checkMultipurposeHall: TextView

    private lateinit var txtState: TextView
    private lateinit var txtCity: TextView
    private lateinit var txtColony: TextView
    private lateinit var txtLandMark: TextView
    private lateinit var txtMinRent: TextView
    private lateinit var txtMaxRent: TextView
    private lateinit var btnCallOwner: Button
    private lateinit var btnCallCheashu: Button
    private lateinit var txtEmail: TextView
    private lateinit var txtFullName: TextView
    private lateinit var txtNumber: TextView
    private lateinit var txtRule: TextView
    private lateinit var btnWatchVideo: Button
    private lateinit var btnGetLocation:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        btnGetLocation=findViewById(R.id.btnGetLocation)
        checkBathroom = findViewById(R.id.checkBathroom)
        checkBalcony = findViewById(R.id.checkBalcony)
        checkPowerSupply = findViewById(R.id.checkPowerSupply)
        checkWaterSupply = findViewById(R.id.checkWaterSupply)
        checkParking = findViewById(R.id.checkParking)
        checkKitchen = findViewById(R.id.checkKitchen)
        checkWiFi = findViewById(R.id.checkWiFi)
        checkPowerBackUp = findViewById(R.id.checkPowerBackUp)
        checkAirConditioning = findViewById(R.id.checkAirConditioning)
        checkLaundry = findViewById(R.id.checkLaundry)
        checkLift = findViewById(R.id.checkLift)
        checkSecurity = findViewById(R.id.checkSecurity)
        checkRoWaterSystem = findViewById(R.id.checkRoWaterSystem)
        checkFireExtinguisher = findViewById(R.id.checkFireExtinguisher)
        checkWasteDisposal = findViewById(R.id.checkWasteDisposal)
        checkClubHouse = findViewById(R.id.checkClubHouse)
        checkSwimmingPool = findViewById(R.id.checkSwimmingPool)
        checkPark = findViewById(R.id.checkPark)
        checkGymnasium = findViewById(R.id.checkGymnasium)
        checkKidsPlayArea = findViewById(R.id.checkKidsPlayArea)
        checkKidsClub = findViewById(R.id.checkKidsClub)
        checkFlower = findViewById(R.id.checkFlowers)
        checkMultipurposeHall = findViewById(R.id.checkMultipurposeHall)

        txtState = findViewById(R.id.txtState)
        txtCity = findViewById(R.id.txtCity)
        txtColony = findViewById(R.id.txtColony)
        txtMinRent = findViewById(R.id.txtMinRent)
        txtMaxRent = findViewById(R.id.txtMaxRent)
        btnCallOwner = findViewById(R.id.btnOwnerCall)
        btnCallCheashu = findViewById(R.id.btnCheashuCall)
        txtFullName = findViewById(R.id.txtFullName)
        txtEmail = findViewById(R.id.txtEmail)
        txtNumber = findViewById(R.id.txtNumber)
        btnWatchVideo = findViewById(R.id.btnWatchVideo)
        txtLandMark = findViewById(R.id.txtLandmark)
        txtRule = findViewById(R.id.txtRule)
        settingText()

    }

    private fun settingText() {
        val checkBoxArray = arrayOf(
            checkBathroom,
            checkBalcony,
            checkPowerSupply,
            checkWaterSupply,
            checkParking,
            checkKitchen,
            checkWiFi,
            checkPowerBackUp,
            checkAirConditioning,
            checkLaundry,
            checkLift,
            checkSecurity,
            checkRoWaterSystem,
            checkFireExtinguisher,
            checkWasteDisposal,
            checkClubHouse,
            checkSwimmingPool,
            checkPark,
            checkGymnasium,
            checkKidsPlayArea,
            checkKidsClub,
            checkFlower,
            checkMultipurposeHall
        )
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
        val intent = intent
        for (i in 0..22) {
            val getData = intent.getStringExtra(facilitiesArray[i])
            if (getData == "Yes") {
                checkBoxArray[i].text = getData
                checkBoxArray[i].setTextColor(GREEN)
            } else {
                checkBoxArray[i].text = getData
                checkBoxArray[i].setTextColor(RED)
            }
        }
        val landmark = intent.getStringExtra("landmark")
        val number = intent.getStringExtra("number")
        val rule = intent.getStringExtra("rule")
        val video = intent.getStringExtra("video")
        val fullName = intent.getStringExtra("fullName")
        val id = intent.getStringExtra("id")
        val minRent = intent.getStringExtra("minRent")
        val maxRent = intent.getStringExtra("maxRent")
        val state = intent.getStringExtra("state")
        val city = intent.getStringExtra("city")
        val colony = intent.getStringExtra("colony")
        val email = intent.getStringExtra("email")
        if (rule == "") {
            txtRule.text = getString(R.string.rule)
        } else {
            txtRule.text = rule
        }
        txtLandMark.text = landmark
        txtMaxRent.text = maxRent
        txtColony.text = colony
        txtMinRent.text = minRent
        txtCity.text = city
        txtState.text = state
        txtEmail.text = email
        txtFullName.text = fullName
        txtNumber.text = number
        btnWatchVideo.setOnClickListener {
            val intent1 = Intent(this, VideoActivity::class.java)
            intent1.putExtra("video", video)
            startActivity(intent1)
        }
        btnCallOwner.setOnClickListener {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CALL_PHONE), 200)
            }else{
                val intent1 = Intent(Intent.ACTION_CALL, Uri.fromParts("tel", number, null))
                startActivity(intent1)
            }
        }
        btnCallCheashu.setOnClickListener {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CALL_PHONE), 200)
            }else {
                val intent1 = Intent(Intent.ACTION_CALL, Uri.fromParts("tel", "8434230999", null))
                startActivity(intent1)
            }

        }
        btnGetLocation.setOnClickListener {
            try {
                val mUriIntent=Uri.parse("geo:0,0?q=${state!!+city+colony+landmark}")
                val mMapIntent=Intent(Intent.ACTION_VIEW,mUriIntent)
                mMapIntent.setPackage("com.google.android.apps.maps")
                startActivity(mMapIntent)
            }catch (e:Exception){
                e.printStackTrace()
            }

        }
    }

    }

