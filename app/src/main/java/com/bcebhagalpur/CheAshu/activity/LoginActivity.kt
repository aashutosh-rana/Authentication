package com.bcebhagalpur.CheAshu.activity

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bcebhagalpur.CheAshu.R
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private val tag = "LoginActivity"
    //global variables
    private lateinit var email: String
    private lateinit var password: String

    //UI elements

    private lateinit var txtForgotPassword: TextView
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var mProgressBar: ProgressDialog

    //Firebase references
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initialise()

    }

    private fun initialise() {
        txtForgotPassword =findViewById(R.id.txtForgotPassword)
        etEmail =findViewById(R.id.etEmail)
        etPassword =findViewById(R.id.etPassword)
        btnLogin =findViewById(R.id.btnLogin)
        mProgressBar = ProgressDialog(this)
        mAuth = FirebaseAuth.getInstance()
        txtForgotPassword.setOnClickListener { startActivity(Intent(this,
            ForgotPasswordActivity::class.java))}
        btnLogin.setOnClickListener { loginUser() }

    }

    private fun loginUser() {

        email = etEmail.text.toString()
        password = etPassword.text.toString()

        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {

            mProgressBar.setMessage("Logging User...")
            mProgressBar.show()

            Log.d(tag, "Logging in user.")

            mAuth.signInWithEmailAndPassword(email, password)

                .addOnCompleteListener(this) { task ->

                    mProgressBar.hide()

                    if (task.isSuccessful) {
                        // Sign in success, update UI with signed-in user's information
                        Log.d(tag, "signInWithEmail:success")
                        updateUI()
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.e(tag, "signInWithEmail:failure", task.exception)
                        Toast.makeText(this@LoginActivity, "Password and Email doesn't match",
                            Toast.LENGTH_SHORT).show()
                    }
                }
        } else {
            Toast.makeText(this, "Enter all details", Toast.LENGTH_SHORT).show()
        }
    }
    private fun updateUI() {
        val intent = Intent(this@LoginActivity, MainActivity2::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        finish()
    }

}
