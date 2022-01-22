package com.bcebhagalpur.CheAshu.activity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.bcebhagalpur.CheAshu.R
import java.util.regex.Pattern

class OwnerActivity : AppCompatActivity() {

    private lateinit var etName:TextView
    private lateinit var etEmail:TextView
    private lateinit var etNumber:TextView
    private lateinit var etState:TextView
    private lateinit var etCity:TextView
    private lateinit var etColony:TextView
    private lateinit var etLandmark:TextView
    private lateinit var btnNext:Button

    private lateinit var fullName: String
    private lateinit var email: String
    private lateinit var number: String
    private lateinit var state: String
    private lateinit var city: String
    private lateinit var colony: String
    private lateinit var landmark: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_owner)

        etName=findViewById(R.id.etName)
        etEmail=findViewById(R.id.etEmail)
        etNumber=findViewById(R.id.etNumber)
        etState=findViewById(R.id.etState)
        etCity=findViewById(R.id.etCity)
        etColony=findViewById(R.id.etColony)
        etLandmark=findViewById(R.id.etLandmark)
        btnNext=findViewById(R.id.btnNext)

        emailValidator()
        phoneValidator()
       btnNext.setOnClickListener { updateBasicDetail() }
        val mobileNumber=intent.getStringExtra("mobileNumber")
        etNumber.text=mobileNumber

    }

    private fun updateBasicDetail() {
        fullName = etName.text.toString()
        email = etEmail.text.toString()
        number = etNumber.text.toString()
       state = etState.text.toString()
        city = etCity.text.toString()
        colony = etColony.text.toString()
        landmark= etLandmark.text.toString()

        if (!TextUtils.isEmpty(fullName)  && !TextUtils.isEmpty(number) &&
            !TextUtils.isEmpty(state) && !TextUtils.isEmpty(city) && !TextUtils.isEmpty(colony) && !TextUtils.isEmpty(landmark))
        {
            updateUi()
        }else{
            Toast.makeText(this,"all details are required ! please enter all details",Toast.LENGTH_SHORT).show()
        }
    }

    private fun emailValidator(){
        etEmail.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (android.util.Patterns.EMAIL_ADDRESS.matcher(etEmail.text.toString()).matches()){
                     btnNext.isEnabled=true
                    }else{
                    btnNext.isEnabled=false
                    etEmail.error = "Invalid email !"
                }
            }

        })
    }

    private fun phoneValidate(text: String): Boolean {
        val p=Pattern.compile("[6-9][0-9]{9}")
        val m=p.matcher(text)
        return m.matches()
    }
    private fun phoneValidator(){
        etNumber.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (phoneValidate(etNumber.text.toString())){
                    btnNext.isEnabled=true
                }else{
                    btnNext.isEnabled=false
                    etNumber.error = "Invalid number !"
                }
            }
        })
    }

    private fun updateUi(){
        val intent1 = Intent(this@OwnerActivity, OwnerActivity2::class.java)
        val intent2=intent
        val minRent=intent2.getStringExtra("minRent")
        val maxRent=intent2.getStringExtra("maxRent")
        val spinnerString=intent2.getStringExtra("spinnerString")
        val itemSelected=intent2.getStringExtra("itemSelected")
        fullName = etName.text.toString()
        email = etEmail.text.toString()
        number = etNumber.text.toString()
        state = etState.text.toString()
        city = etCity.text.toString()
        colony = etColony.text.toString()
        landmark= etLandmark.text.toString()
        intent1.putExtra("fullName",fullName)
        intent1.putExtra("email",email)
        intent1.putExtra("number",number)
        intent1.putExtra("state",state)
        intent1.putExtra("city",city)
        intent1.putExtra("colony",colony)
        intent1.putExtra("landmark",landmark)
        intent1.putExtra("spinnerString",spinnerString)
        intent1.putExtra("minRent",minRent)
        intent1.putExtra("maxRent",maxRent)
        intent1.putExtra("itemSelected",itemSelected)
        intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent1)
    }

}
