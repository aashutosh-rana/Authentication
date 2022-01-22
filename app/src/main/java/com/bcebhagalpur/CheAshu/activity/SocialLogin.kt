package com.bcebhagalpur.CheAshu.activity

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bcebhagalpur.CheAshu.R
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.*
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.TwitterAuthProvider
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso

import com.twitter.sdk.android.core.Callback
import com.twitter.sdk.android.core.Result
import com.twitter.sdk.android.core.Twitter
import com.twitter.sdk.android.core.TwitterAuthConfig
import com.twitter.sdk.android.core.TwitterConfig
import com.twitter.sdk.android.core.TwitterException
import com.twitter.sdk.android.core.TwitterSession
import com.twitter.sdk.android.core.identity.TwitterLoginButton
import kotlinx.android.synthetic.main.activity_social_login.*


@Suppress("DEPRECATION")
class SocialLogin : AppCompatActivity() {

    private lateinit var twitter_button:TwitterLoginButton
    private lateinit var mProgressBar: ProgressDialog
    private lateinit var txtRegisterNow:TextView
    private lateinit var btnLoginEmail:Button
    private lateinit var btnLoginNumber:Button

    val RC_SIGN_IN: Int = 1
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var mGoogleSignInOptions: GoogleSignInOptions
    private lateinit var firebaseAuth: FirebaseAuth

    private val TAG = "FacebookLogin"

    private var mCallbackManager: CallbackManager? = null

    private var mAuth: FirebaseAuth? = null
    private lateinit var facebookButton:LoginButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mTwitterAuthConfig = TwitterAuthConfig(
            getString(R.string.twitter_consumer_key),
            getString(R.string.twitter_consumer_secret)
        )
        val twitterConfig: TwitterConfig = TwitterConfig.Builder(this)
            .twitterAuthConfig(mTwitterAuthConfig)
            .build()
        Twitter.initialize(twitterConfig)

        setContentView(R.layout.activity_social_login)
        firebaseAuth = FirebaseAuth.getInstance()
        mProgressBar = ProgressDialog(this)

        txtRegisterNow=findViewById(R.id.txtRegisterNow)
        btnLoginEmail=findViewById(R.id.btnLoginEmail)
        btnLoginNumber=findViewById(R.id.btnLoginNumber)

        configureGoogleSignIn()
        setupUI()
        twitter_button=findViewById(R.id.twitter_button)
        mAuth = FirebaseAuth.getInstance()
        mCallbackManager = CallbackManager.Factory.create()
        facebookButton=findViewById(R.id.facebook_button)
        facebookButton.setReadPermissions("email", "public_profile")
        facebookButton.setOnClickListener {
            signInWithFacebook()
        }

       handleTwitterCallback()

        btnLoginNumber.setOnClickListener {
            startActivity(Intent(this,PhoneActivity::class.java))
        }
        btnLoginEmail.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))
        }
        txtRegisterNow.setOnClickListener {
            startActivity(Intent(this,RegisterActivity::class.java))
        }

//        myActivity()
        }

    private fun signInWithFacebook() {
         facebookButton.registerCallback(mCallbackManager,object :FacebookCallback<LoginResult>{
             override fun onSuccess(result: LoginResult?) {
                 handleFacebookAccessToken(result!!.accessToken)
             }

             override fun onCancel() {

             }

             override fun onError(error: FacebookException?) {

             }
         })
    }

    private fun handleFacebookAccessToken(accessToken: AccessToken) {
        mProgressBar.setMessage("Logging User...")
        mProgressBar.show()
        val credential = FacebookAuthProvider.getCredential(accessToken.token)
    firebaseAuth.signInWithCredential(credential)
        .addOnFailureListener { e->
            Toast.makeText(this,e.message,Toast.LENGTH_SHORT).show()
        }
        .addOnSuccessListener { result->
                mProgressBar.hide()
            val email = result.user
            Toast.makeText(this, "You logged with email:$email",Toast.LENGTH_SHORT).show()
            startActivity( Intent(this, MainActivity2::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            })
        }
    }


    private fun configureGoogleSignIn() {
        mGoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, mGoogleSignInOptions)
    }

    private fun setupUI() {
        google_button.setOnClickListener {
            signIn()
        }
    }

    private fun signIn() {
        val signInIntent: Intent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mCallbackManager!!.onActivityResult(requestCode,resultCode,data)
        twitter_button.onActivityResult(requestCode,resultCode,data)
        if (requestCode == RC_SIGN_IN) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                if (account != null) {
                    firebaseAuthWithGoogle(account)
                }
            } catch (e: ApiException) {
                Toast.makeText(this, "Google sign in failed:(", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        mProgressBar.setMessage("Logging User...")
        mProgressBar.show()
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {

                mProgressBar.hide()
                startActivity( Intent(this, MainActivity2::class.java).apply {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                })
            } else {
                Toast.makeText(this, "Google sign in failed:(", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun handleTwitterCallback() {
        twitter_button.callback = object : Callback<TwitterSession>() {
            override fun success(result: Result<TwitterSession>?) {
                Log.d(TAG, "twitterLogin:success$result")
                handleTwitterSession(result!!.data)
            }
            override fun failure(exception: TwitterException) {
                Log.w(TAG, "twitterLogin:failure", exception)
            }
        }
    }

    private fun handleTwitterSession(session: TwitterSession) {
        Log.d(TAG, "handleTwitterSession:$session")
        val credential = TwitterAuthProvider.getCredential(
            session.authToken.token,
            session.authToken.secret)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    startActivity(Intent(this@SocialLogin, MainActivity2::class.java))
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    Toast.makeText(this@SocialLogin, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

    override fun onStart() {
        super.onStart()
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            startActivity( Intent(this, MainActivity2::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                finish()
            })

        }
    }

    override fun onBackPressed() {
        val a = Intent(Intent.ACTION_MAIN)
        a.addCategory(Intent.CATEGORY_HOME)
        a.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(a)
    }
}

