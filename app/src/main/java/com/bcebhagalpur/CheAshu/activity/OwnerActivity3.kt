package com.bcebhagalpur.CheAshu.activity

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.bcebhagalpur.CheAshu.R

class OwnerActivity3 : AppCompatActivity() {

    private lateinit var radioGroup1:RadioGroup
    private lateinit var radioGroup2:RadioGroup
    private lateinit var radioGroup3:RadioGroup
    private lateinit var radioGroup4:RadioGroup
    private lateinit var radioGroup5:RadioGroup
    private lateinit var radioGroup6:RadioGroup
    private lateinit var radioGroup7:RadioGroup

    private lateinit var btnNext:Button
    private lateinit var etMinRent:EditText
    private lateinit var etMaxRent:EditText

    private lateinit var spinnerRoomType1: Spinner
    private lateinit var linearShop:LinearLayout
    private lateinit var linearFamily:LinearLayout
    private lateinit var linearPg:LinearLayout
    private lateinit var linearBachelorBoys:LinearLayout
    private lateinit var linearBachelorGirls:LinearLayout
    private lateinit var linearBachelorGirlsBoys:LinearLayout
    private lateinit var linearBachelorFamily:LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_owner3)
        btnNext=findViewById(R.id.btnNext)
        etMinRent=findViewById(R.id.etMinRent)
        etMaxRent=findViewById(R.id.etMaxRent)
        spinnerRoomType1=findViewById(R.id.spinnerRoomType1)

        //linear layout
        linearShop=findViewById(R.id.linearShop)
        linearFamily=findViewById(R.id.linearFamily)
        linearPg=findViewById(R.id.linearPg)
        linearBachelorBoys=findViewById(R.id.linearBachelorBoys)
        linearBachelorGirls=findViewById(R.id.linearBachelorGirls)
        linearBachelorGirlsBoys=findViewById(R.id.linearBachelorGirlsBoys)
        linearBachelorFamily=findViewById(R.id.linearBachelorFamily)

        //radio group
        radioGroup1=findViewById(R.id.radioGroup1)
        radioGroup2=findViewById(R.id.radioGroup2)
        radioGroup3=findViewById(R.id.radioGroup3)
        radioGroup4=findViewById(R.id.radioGroup4)
        radioGroup5=findViewById(R.id.radioGroup5)
        radioGroup6=findViewById(R.id.radioGroup6)
        radioGroup7=findViewById(R.id.radioGroup7)

        val room= arrayOf("select one","SHOP OR SHOWROOM","FAMILY","PG","BACHELOR ONLY FOR BOYS","BACHELOR ONLY FOR GIRLS","BACHELOR FOR BOTH GIRLS AND BOYS",
            "BACHELOR OR FAMILY BOTH")
        val arrayAdp=ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,room)
        spinnerRoomType1.adapter=arrayAdp
        spinnerSet()
        btnNext.setOnClickListener {goToNext() }

    }
    private fun goToNext(){
        val spinnerString=spinnerRoomType1.selectedItem.toString()
        val minRent=etMinRent.text.toString().trim()
        val maxRent=etMaxRent.text.toString().trim()
        if (!TextUtils.isEmpty(minRent)&& !TextUtils.isEmpty(maxRent)){
            if (minRent.toInt()<=maxRent.toInt()){
                if (minRent!=maxRent){
                    if (spinnerString!="select one"){
                        updateUi()
                    }else{
                        Toast.makeText(this,"please select category of property type",Toast.LENGTH_SHORT).show()
                    }

                }else
                {
                    Toast.makeText(this,"minimum rent and maximum rent should not be equal",Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this,"maximum rent should be greater than minimum rent",Toast.LENGTH_SHORT).show()
            }
        }
        else{
            Toast.makeText(this,"please enter rent range",Toast.LENGTH_SHORT).show()
        }
    }
    private fun updateUi(){
        val spinnerString=spinnerRoomType1.selectedItem.toString()
        val radioGroupArray= arrayOf(radioGroup1,radioGroup2,radioGroup3,radioGroup4,radioGroup5,radioGroup6,radioGroup7)
        val room= arrayOf("select one","SHOP OR SHOWROOM","FAMILY","PG","BACHELOR ONLY FOR BOYS","BACHELOR ONLY FOR GIRLS","BACHELOR FOR BOTH GIRLS AND BOYS",
            "BACHELOR OR FAMILY BOTH")
        val id1=radioGroup1.checkedRadioButtonId
        val id2=radioGroup2.checkedRadioButtonId
        val id3=radioGroup3.checkedRadioButtonId
        val id4=radioGroup4.checkedRadioButtonId
        val id5=radioGroup5.checkedRadioButtonId
        val id6=radioGroup6.checkedRadioButtonId
        val id7=radioGroup7.checkedRadioButtonId
        when (spinnerString) {
            room[1] -> {
                if (id1!=-1) {
                    val radio: RadioButton = findViewById(id1)
                    val checkedRadioButton = radio.text.toString()
                    passData(checkedRadioButton)
                }else{
                    Toast.makeText(this,"please select one",Toast.LENGTH_LONG).show()
                }
                radioGroupArray[1].clearCheck()
                radioGroupArray[2].clearCheck()
                radioGroupArray[3].clearCheck()
                radioGroupArray[4].clearCheck()
                radioGroupArray[5].clearCheck()
                radioGroupArray[6].clearCheck()
            }
            room[2] -> {
                if (id2!=-1) {
                    val radio: RadioButton = findViewById(id2)
                    val checkedRadioButton = radio.text.toString()
                    passData(checkedRadioButton)
                }else{
                    Toast.makeText(this,"please select one",Toast.LENGTH_LONG).show()
                }
            }
            room[3] -> {
                if (id3!=-1) {
                    val radio: RadioButton = findViewById(id3)
                    val checkedRadioButton = radio.text.toString()
                    passData(checkedRadioButton)
                }else{
                    Toast.makeText(this,"please select one",Toast.LENGTH_LONG).show()
                }
            }
            room[4] -> {
                if (id4!=-1) {
                    val radio: RadioButton = findViewById(id4)
                    val checkedRadioButton = radio.text.toString()
                    passData(checkedRadioButton)
                }else{
                    Toast.makeText(this,"please select one",Toast.LENGTH_LONG).show()
                }
            }
            room[5] -> {
                if (id5!=-1) {
                    val radio: RadioButton = findViewById(id5)
                    val checkedRadioButton = radio.text.toString()
                    passData(checkedRadioButton)
                }else{
                    Toast.makeText(this,"please select one",Toast.LENGTH_LONG).show()
                }
            }
            room[6] -> {
                if (id6!=-1) {
                    val radio: RadioButton = findViewById(id6)
                    val checkedRadioButton = radio.text.toString()
                    passData(checkedRadioButton)
                }else{
                    Toast.makeText(this,"please select one",Toast.LENGTH_LONG).show()
                }
            }
            room[7] -> {
                if (id7!=-1) {
                    val radio: RadioButton = findViewById(id7)
                    val checkedRadioButton = radio.text.toString()
                    passData(checkedRadioButton)
                }else{
                    Toast.makeText(this,"please select one",Toast.LENGTH_LONG).show()
                }
            }else->{
            Toast.makeText(this,"please select category of property type",Toast.LENGTH_SHORT).show()
        }
        }

    }
    private fun passData(radioSelected:String){
        val spinnerString=spinnerRoomType1.selectedItem.toString()
        val minRent=etMinRent.text.toString().trim()
        val maxRent=etMaxRent.text.toString().trim()
        val intent1=intent
        val intent=Intent(this, OwnerActivity::class.java)
        val mobileNumber= intent1.getStringExtra("mobileNumber")
        intent.putExtra("minRent", minRent)
        intent.putExtra("maxRent",maxRent)
        intent.putExtra("itemSelected",radioSelected)
        intent.putExtra("spinnerString",spinnerString)
        intent.putExtra("mobileNumber",mobileNumber)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
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
                        linearShop.visibility=View.VISIBLE
                        linearFamily.visibility=View.GONE
                        linearPg.visibility=View.GONE
                        linearBachelorBoys.visibility=View.GONE
                        linearBachelorGirls.visibility=View.GONE
                        linearBachelorGirlsBoys.visibility=View.GONE
                        linearBachelorFamily.visibility=View.GONE
                        radioGroup2.clearCheck()
                        radioGroup3.clearCheck()
                        radioGroup4.clearCheck()
                        radioGroup5.clearCheck()
                        radioGroup6.clearCheck()
                        radioGroup7.clearCheck()

                    }
                    2 -> {
                        linearShop.visibility=View.GONE
                        linearFamily.visibility=View.VISIBLE
                        linearPg.visibility=View.GONE
                        linearBachelorBoys.visibility=View.GONE
                        linearBachelorGirls.visibility=View.GONE
                        linearBachelorGirlsBoys.visibility=View.GONE
                        linearBachelorFamily.visibility=View.GONE
                        radioGroup1.clearCheck()
                        radioGroup3.clearCheck()
                        radioGroup4.clearCheck()
                        radioGroup5.clearCheck()
                        radioGroup6.clearCheck()
                        radioGroup7.clearCheck()
                    }
                    3 -> {
                        linearShop.visibility=View.GONE
                        linearFamily.visibility=View.GONE
                        linearPg.visibility=View.VISIBLE
                        linearBachelorBoys.visibility=View.GONE
                        linearBachelorGirls.visibility=View.GONE
                        linearBachelorGirlsBoys.visibility=View.GONE
                        linearBachelorFamily.visibility=View.GONE
                        radioGroup2.clearCheck()
                        radioGroup1.clearCheck()
                        radioGroup4.clearCheck()
                        radioGroup5.clearCheck()
                        radioGroup6.clearCheck()
                        radioGroup7.clearCheck()
                    }
                    4 -> {
                        linearShop.visibility=View.GONE
                        linearFamily.visibility=View.GONE
                        linearPg.visibility=View.GONE
                        linearBachelorBoys.visibility=View.VISIBLE
                        linearBachelorGirls.visibility=View.GONE
                        linearBachelorGirlsBoys.visibility=View.GONE
                        linearBachelorFamily.visibility=View.GONE
                        radioGroup2.clearCheck()
                        radioGroup3.clearCheck()
                        radioGroup1.clearCheck()
                        radioGroup5.clearCheck()
                        radioGroup6.clearCheck()
                        radioGroup7.clearCheck()
                    }
                    5 -> {
                        linearShop.visibility=View.GONE
                        linearFamily.visibility=View.GONE
                        linearPg.visibility=View.GONE
                        linearBachelorBoys.visibility=View.GONE
                        linearBachelorGirls.visibility=View.VISIBLE
                        linearBachelorGirlsBoys.visibility=View.GONE
                        linearBachelorFamily.visibility=View.GONE
                        radioGroup2.clearCheck()
                        radioGroup3.clearCheck()
                        radioGroup4.clearCheck()
                        radioGroup1.clearCheck()
                        radioGroup6.clearCheck()
                        radioGroup7.clearCheck()
                    }
                    6 -> {
                        linearShop.visibility=View.GONE
                        linearFamily.visibility=View.GONE
                        linearPg.visibility=View.GONE
                        linearBachelorBoys.visibility=View.GONE
                        linearBachelorGirls.visibility=View.GONE
                        linearBachelorGirlsBoys.visibility=View.VISIBLE
                        linearBachelorFamily.visibility=View.GONE
                        radioGroup2.clearCheck()
                        radioGroup3.clearCheck()
                        radioGroup4.clearCheck()
                        radioGroup5.clearCheck()
                        radioGroup1.clearCheck()
                        radioGroup7.clearCheck()
                    }
                    7->{
                        linearShop.visibility=View.GONE
                        linearFamily.visibility=View.GONE
                        linearPg.visibility=View.GONE
                        linearBachelorBoys.visibility=View.GONE
                        linearBachelorGirls.visibility=View.GONE
                        linearBachelorGirlsBoys.visibility=View.GONE
                        linearBachelorFamily.visibility=View.VISIBLE
                        radioGroup2.clearCheck()
                        radioGroup3.clearCheck()
                        radioGroup4.clearCheck()
                        radioGroup5.clearCheck()
                        radioGroup6.clearCheck()
                        radioGroup1.clearCheck()
                    }
                    else->{
                        linearShop.visibility=View.GONE
                        linearFamily.visibility=View.GONE
                        linearPg.visibility=View.GONE
                        linearBachelorBoys.visibility=View.GONE
                        linearBachelorGirls.visibility=View.GONE
                        linearBachelorGirlsBoys.visibility=View.GONE
                        linearBachelorFamily.visibility=View.GONE
                        radioGroup1.clearCheck()
                        radioGroup2.clearCheck()
                        radioGroup3.clearCheck()
                        radioGroup4.clearCheck()
                        radioGroup5.clearCheck()
                        radioGroup6.clearCheck()
                        radioGroup7.clearCheck()
                    }

                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

    }

}

