@file:Suppress("DEPRECATION")
package com.bcebhagalpur.CheAshu.activity

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.bcebhagalpur.CheAshu.BuildConfig
import com.bcebhagalpur.CheAshu.R
import com.bcebhagalpur.CheAshu.fragment.*
import com.facebook.login.LoginManager
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.squareup.picasso.Picasso

@Suppress("DEPRECATION")
class MainActivity2 : AppCompatActivity(),GoogleApiClient.OnConnectionFailedListener {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var coordinatorLayout: CoordinatorLayout
    private lateinit var toolBar: androidx.appcompat.widget.Toolbar
    private lateinit var frameLayout: FrameLayout
    private lateinit var navigationView: NavigationView

    private var googleApiClient: GoogleApiClient? = null
    private lateinit var gso: GoogleSignInOptions

    private lateinit var imgHeader:ImageView
    private lateinit var imgHeader1:ImageView
    private lateinit var txtName:TextView
    private lateinit var txtEmail:TextView

    private var facebookUserId = ""
    private var user = FirebaseAuth.getInstance().currentUser
    private lateinit var mDatabaseReference: DatabaseReference
    private lateinit var mDatabase: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        drawerLayout = findViewById(R.id.drawerLayout)
        coordinatorLayout = findViewById(R.id.coordinatorLayout)
        toolBar = findViewById(R.id.toolBar)
        frameLayout = findViewById(R.id.frameLayout)
        navigationView = findViewById(R.id.navigationView)

        val view=navigationView.getHeaderView(0)
       imgHeader=view.findViewById(R.id.imgHeader)
        imgHeader1=view.findViewById(R.id.imgHeader1)
        txtName=view.findViewById(R.id.txtName)
        txtEmail=view.findViewById(R.id.txtEmail)
        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        try {
            googleApiClient = GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi<GoogleSignInOptions?>(Auth.GOOGLE_SIGN_IN_API, gso)
                .build()
        } catch (e: Exception) {

        }

        var previousMenuItem: MenuItem? = null

        setUpToolbar()

        openHome()
        val actionBarDrawerToggle = ActionBarDrawerToggle(
            this@MainActivity2
            , drawerLayout,
            R.string.open_drawer,
            R.string.close_drawer
        )
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
        myActivity()
        facebookProfile()
        emailLoginProfile()
        imageLodeFromFacebook()
        getNumber()
        navigationView.setNavigationItemSelectedListener{

            if (previousMenuItem!=null){
                previousMenuItem?.isChecked=false
            }
            it.isCheckable=true
            it.isChecked=true
            previousMenuItem=it
            when(it.itemId){
                R.id.home ->{ openHome()
                    drawerLayout.closeDrawers()
                }
                R.id.myUploads ->{

                    supportFragmentManager.beginTransaction().replace(R.id.frameLayout,MyUploadFragment()).commit()
                    supportActionBar?.title="My Uploads"
                    drawerLayout.closeDrawers()

                }
                R.id.share ->{
                    try {
                        val shareIntent = Intent(Intent.ACTION_SEND)
                        shareIntent.type = "text/plain"
                        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name")
                        var shareMessage =
                            "\nLet me recommend you CheAshu which help you to find the rooms on rent\n\n"
                        shareMessage =
                            """
                            ${shareMessage}https://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID}
                            """.trimIndent()
                        shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
                        startActivity(Intent.createChooser(shareIntent, "choose one"))
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                    drawerLayout.closeDrawers()
                }
                R.id.feedBack ->{
                    supportFragmentManager.beginTransaction().replace(R.id.frameLayout, FeedbackFragment()).commit()
                    supportActionBar?.title="Feedback"
                    drawerLayout.closeDrawers()
                }
                R.id.contactUs ->{
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameLayout,ContactUsFragment()).commit()
                    supportActionBar?.title = "Contact Us"
                    drawerLayout.closeDrawers()
                }
                R.id.rateUs ->{
                    try{
                        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$packageName")))
                    }catch (e:ActivityNotFoundException){
                        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=$packageName")))
                    }
                    drawerLayout.closeDrawers()
                }
                R.id.aboutUs ->{
                    supportFragmentManager.beginTransaction().replace(R.id.frameLayout, AboutUsFragment()).commit()
                    supportActionBar?.title="About Us"
                    drawerLayout.closeDrawers()
                }
                R.id.logOut ->{
                        FirebaseAuth.getInstance().signOut()
                        LoginManager.getInstance().logOut()
                        try {
                            Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback { status ->
                                if (status.isSuccess) {
                                    val intent = Intent(this, SocialLogin::class.java)
                                    startActivity(intent)
                                } else {
                                    Toast.makeText(
                                        this,
                                        "Session not close",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            }
                        } catch (e: Exception) {
                            startActivity(Intent(this,SocialLogin::class.java))
                        }
                    drawerLayout.closeDrawers()
                }
            }

            return@setNavigationItemSelectedListener true

        }

    }


    private fun setUpToolbar(){
        setSupportActionBar(toolBar)
        supportActionBar?.title="Toolbar Title"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id= item.itemId
        if(id==android.R.id.home){
            drawerLayout.openDrawer(GravityCompat.START)
        }

        return super.onOptionsItemSelected(item)
    }
    private fun openHome(){
        val fragment= HomeFragment1()
        val transaction=supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frameLayout,fragment)
        transaction.commit()
        supportActionBar?.title="CheAshu"
        navigationView.setCheckedItem(R.id.home)
    }

    override fun onBackPressed() {
//        if (doubleBackToExitPressedOnce) {
//            return super.onBackPressed()
//        }
//            this.doubleBackToExitPressedOnce = true
//            Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show()
//            Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
        drawerLayout.closeDrawers()
        when(supportFragmentManager.findFragmentById(R.id.frameLayout)){
            !is HomeFragment1 ->openHome()
            else ->{
                val a =Intent(Intent.ACTION_MAIN)
                a.addCategory(Intent.CATEGORY_HOME)
                a.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(a)
            }


        }



    }
    private fun myActivity() {
        val opr =
            Auth.GoogleSignInApi.silentSignIn(googleApiClient)
        if (opr.isDone) {
            val result = opr.get()
            handleSignInResult(result)
        } else {
            opr.setResultCallback { googleSignInResult -> handleSignInResult(googleSignInResult) }
        }
    }

    private fun handleSignInResult(result: GoogleSignInResult) {
        if (result.isSuccess) {
            result.signInAccount
            try{
                val name = user!!.displayName
                val email = user!!.email
               txtName.text = name
//               txtName.text = email
                val userId = user!!.uid
                mDatabase = FirebaseDatabase.getInstance()
                mDatabaseReference = mDatabase.reference.child("RENTERS")

                val currentUserDb = mDatabaseReference.child(userId)
                val currentUserDb1=currentUserDb.child("google user")
                currentUserDb1.child("email").setValue(email)
                currentUserDb1.child("firstName").setValue(name)
            }catch (e:NullPointerException){
                e.printStackTrace()
            }

            try {
                Picasso.get().load(user!!.photoUrl)
                    .into(imgHeader1)
            } catch (e: NullPointerException) {
                Toast.makeText(this, "image not found", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {}

    private fun facebookProfile() {
        try {
            for (profile in user!!.providerData) {

                // check if the provider id matches "facebook.com"
                if (FacebookAuthProvider.PROVIDER_ID == profile.providerId) {
                    facebookUserId = profile.uid
                    mDatabase = FirebaseDatabase.getInstance()
                    mDatabaseReference = mDatabase.reference.child("RENTERS")

                    val name = user!!.displayName
                    val email = user!!.email
                    txtName.text=name
                    txtEmail.text=email

                    val userId= user!!.uid
                    val photoUrl = "https://graph.facebook.com/$facebookUserId/picture?height=500"
                    //update user profile information
                    val currentUserDb = mDatabaseReference.child(userId)
                    val currentUserDb1=currentUserDb.child("facebook user")
                    currentUserDb1.child("email").setValue(email)
                    currentUserDb1.child("firstName").setValue(name)
                    currentUserDb1.child("image").setValue(photoUrl)

                }
            }
        } catch (e: KotlinNullPointerException) {
            Toast.makeText(
               this,
                "make a profile by login or register to see the profile",
                Toast.LENGTH_LONG
            ).show()
        }
    }


    private fun imageLodeFromFacebook() {
        val photoUrl1 = "https://graph.facebook.com/$facebookUserId/picture?height=500"
        val name = user!!.displayName
        val email = user!!.email
        user!!.uid
        txtName.text = name
        txtEmail.text = email
        try {
            Picasso.get().load(photoUrl1).into(imgHeader)
        } catch (e: Exception) {
            Toast.makeText(this, "facebook image not found", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun emailLoginProfile(){
        try {
            val mDatabase=FirebaseDatabase.getInstance().getReference("RENTERS")
            val currentChild=mDatabase.child(user!!.uid)
            val currentChild2=currentChild.child("EMAIL USERS")
            txtEmail.text= user!!.email
            currentChild2.addListenerForSingleValueEvent(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){
                        try {
                            txtName.text=snapshot.child("firstName").value as String
                        }
                       catch (e:TypeCastException){
                           e.printStackTrace()
                       }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                }

            })
        }catch (e:KotlinNullPointerException){
            Toast.makeText(this,"Login Or SignUp to see your profile",Toast.LENGTH_LONG).show()
        }
    }
    private fun getNumber(){
        try {
        val mDatabase= FirebaseDatabase.getInstance().getReference("RENTERS")
        val currentChild=mDatabase.child(user!!.uid)
        val currentChild2=currentChild.child("mobile number user")
        currentChild2.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    try {
                        txtName.text=snapshot.child("number").value as String
                    }
                    catch (e:TypeCastException){
                        e.printStackTrace()
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
    }catch (e:KotlinNullPointerException){
        Toast.makeText(this,"Login Or SignUp to see your profile",Toast.LENGTH_LONG).show()
    }
    }
}
