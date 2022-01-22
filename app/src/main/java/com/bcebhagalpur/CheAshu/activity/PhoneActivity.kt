package com.bcebhagalpur.CheAshu.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bcebhagalpur.CheAshu.R
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_phone.*
import java.lang.NullPointerException
import java.util.concurrent.TimeUnit

class PhoneActivity : AppCompatActivity() {
    private var mAuth: FirebaseAuth? = null
    private var mobileNumber: String = ""
    private var verificationID: String = ""
   private var token: String = ""
     lateinit var mCallBacks: OnVerificationStateChangedCallbacks
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phone)

        mAuth = FirebaseAuth.getInstance()

        btnSignIn.setOnClickListener {

            mobileNumber = etNumber.text.toString()

            if (mobileNumber.isNotEmpty()) {
                progressBar.visibility = View.VISIBLE
                loginTask()
            } else {
                etNumber.error = "Enter valid phone number"
            }
        }


    }
    private fun loginTask() {

         mCallBacks = object : OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                if (p0 != null) {
                    signInWithPhoneAuthCredential(p0)
                }
            }

            override fun onVerificationFailed(p0: FirebaseException) {
                progressBar.visibility = View.GONE
                Toast.makeText(this@PhoneActivity,"Invalid phone number or verification failed.",Toast.LENGTH_LONG).show()
            }

            override fun onCodeSent(p0: String, p1: ForceResendingToken) {
                super.onCodeSent(p0, p1)
                progressBar.visibility = View.GONE
                verificationID = p0
                token = p1.toString()

                etNumber.setText("")

                etNumber.hint = "Enter OTP "
                btnSignIn.text = getString(R.string.v1)

                btnSignIn.setOnClickListener {
                    progressBar.visibility = View.VISIBLE
                    verifyAuthentication(verificationID, etNumber.text.toString())
                }

                Log.e("Login : verificationId ", verificationID)
                Log.e("Login : token ", token)

                btnResentOtp.setOnClickListener {
                    if (mobileNumber!=null) {
                        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                            mobileNumber, 1, TimeUnit.MINUTES, this@PhoneActivity, mCallBacks
                        )
                    }else{
                        Toast.makeText(this@PhoneActivity,"please enter mobile number",Toast.LENGTH_LONG).show()
                    }
                }

            }

            override fun onCodeAutoRetrievalTimeOut(p0: String) {
                super.onCodeAutoRetrievalTimeOut(p0)
                progressBar.visibility = View.GONE
                Toast.makeText(this@PhoneActivity,"Time out",Toast.LENGTH_LONG).show()
            }
        }

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            mobileNumber,
            60,
            TimeUnit.SECONDS,
            this,
            mCallBacks)
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {

        mAuth!!.signInWithCredential(credential)
            .addOnCompleteListener(this@PhoneActivity
            ) { task ->
                if (task.isSuccessful) {
                    task.result!!.user
                    progressBar.visibility = View.GONE

                    try {
                        val  mDatabase = FirebaseDatabase.getInstance()
                        val  mDatabaseReference = mDatabase.reference.child("RENTERS")
                        val currentUserDb = mDatabaseReference.child(FirebaseAuth.getInstance().currentUser!!.uid)
                        val currentUserDb1=currentUserDb.child("mobile number user")
                        currentUserDb1.child("number").setValue(mobileNumber)
                    }catch (e:NullPointerException){
                        e.printStackTrace()
                    }

                    val intent=Intent(this@PhoneActivity,MainActivity2::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        progressBar.visibility = View.GONE
                        Toast.makeText(this@PhoneActivity,"invalid otp",Toast.LENGTH_LONG).show()
                    }
                }
            }


    }

    private fun verifyAuthentication(verificationID: String, otpText: String) {

        val phoneAuthCredential = PhoneAuthProvider.getCredential(verificationID, otpText)
        signInWithPhoneAuthCredential(phoneAuthCredential)
    }

}
