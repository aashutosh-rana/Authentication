@file:Suppress("DEPRECATION")

package com.bcebhagalpur.CheAshu.activity

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.bcebhagalpur.CheAshu.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

@Suppress("DEPRECATION")
class RegisterActivity : AppCompatActivity() {

    private lateinit var etFirstName: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnCreateAccount: Button
    private lateinit var mProgressBar: ProgressDialog
    private lateinit var txtLoginNow:TextView

    private lateinit var mDatabaseReference: DatabaseReference
    private lateinit var mDatabase: FirebaseDatabase
    private lateinit var mAuth: FirebaseAuth

    private val tag = "RegistrationActivity"

    //global variables
    private lateinit var firstName: String
    private lateinit var email: String
    private lateinit var password: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        initialise()
    }

    private fun initialise() {
        txtLoginNow=findViewById(R.id.txtLoginNow)
        etFirstName = findViewById(R.id.etName)
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        btnCreateAccount = findViewById(R.id.btnRegister)
        mProgressBar = ProgressDialog(this)
        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mDatabase.reference.child("RENTERS")
        mAuth = FirebaseAuth.getInstance()
        btnCreateAccount.setOnClickListener { createNewAccount() }
        txtLoginNow.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }
    }

    private fun createNewAccount() {
        firstName = etFirstName.text.toString()
        email = etEmail.text.toString()
        password = etPassword.text.toString()

        if (!TextUtils.isEmpty(firstName) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
            mProgressBar.setMessage("Registering User...")
            mProgressBar.show()

            mAuth
                .createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    mProgressBar.hide()

                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(tag, "createUserWithEmail:success")

                        val userId = mAuth.currentUser!!.uid

                        //Verify Email
                        verifyEmail()

                        //update user profile information
                        val currentUserDb = mDatabaseReference.child(userId)
                        val currentUserDb1=currentUserDb.child("EMAIL USERS")
                        currentUserDb1.child("email").setValue(email)
                        currentUserDb1.child("firstName").setValue(firstName)
                        currentUserDb1.child("Password").setValue(password)
                        updateUserInfoAndUI()
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(tag, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(this@RegisterActivity, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()
                    }
                }
        } else {
            Toast.makeText(this, "Enter all details", Toast.LENGTH_SHORT).show()
        }
    }
    private fun updateUserInfoAndUI() {

        //start next activity
        val intent = Intent(this@RegisterActivity,
            MainActivity2::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

    private fun verifyEmail() {
        val mUser = mAuth.currentUser
        mUser!!.sendEmailVerification()
            .addOnCompleteListener(this) { task ->

                if (task.isSuccessful) {
                    Toast.makeText(this@RegisterActivity,
                        "Verification email sent to " + mUser.email,
                        Toast.LENGTH_SHORT).show()
                } else {
                    Log.e(tag, "sendEmailVerification", task.exception)
                    Toast.makeText(this@RegisterActivity,
                        "Failed to send verification email.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }


}
