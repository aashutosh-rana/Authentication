package com.bcebhagalpur.CheAshu.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.bcebhagalpur.CheAshu.R
import com.google.firebase.auth.FirebaseAuth

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class ForgotPasswordActivity : AppCompatActivity() {

    private val tag = "ForgotPasswordActivity"
    //UI elements
    private lateinit var etEmail: EditText
    private lateinit var btnSubmit: Button
    //Firebase references
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        initialise()
    }

    private fun initialise() {
        etEmail =findViewById(R.id.etEmail)
        btnSubmit =findViewById(R.id.btnResetPassword)
        mAuth =FirebaseAuth.getInstance()
        btnSubmit.setOnClickListener { sendPasswordResetEmail() }
    }
    private fun sendPasswordResetEmail() {

        val email = etEmail.text.toString()

        if (!TextUtils.isEmpty(email)) {
            mAuth
                .sendPasswordResetEmail(email)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {

                        val message = "Email sent."
                        Log.d(tag, message)
                        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                        updateUI()
                    } else {
                        Log.w(tag, task.exception!!.message)
                        Toast.makeText(this, "No user found with this email.", Toast.LENGTH_SHORT).show()
                    }
                }
        } else {
            Toast.makeText(this, "Enter Email", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUI() {
        val intent = Intent(this@ForgotPasswordActivity, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
}
