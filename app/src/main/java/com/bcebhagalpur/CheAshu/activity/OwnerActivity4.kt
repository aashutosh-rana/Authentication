@file:Suppress("DEPRECATION")
package com.bcebhagalpur.CheAshu.activity

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.bcebhagalpur.CheAshu.R
import com.fxn.pix.Options
import com.fxn.pix.Pix
import com.fxn.utility.PermUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso

@Suppress("DEPRECATION")
class OwnerActivity4 : AppCompatActivity() {

    private lateinit var btnVideo: Button
    private lateinit var img4: ImageView
    private lateinit var btnNext: Button
    private lateinit var videoView: VideoView
    private lateinit var etRule: EditText
    private lateinit var btnImg4:Button
    private lateinit var mDatabaseReference: DatabaseReference
    private lateinit var mDatabase: FirebaseDatabase
    private var maxId:Long=0
    private  var uri4 : Uri?=null
    private  var uri : Uri?=null
    private lateinit var mStorage : StorageReference
    private lateinit var mAuth: FirebaseAuth

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_owner4)
        mAuth = FirebaseAuth.getInstance()
        initialise()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initialise(){
        btnVideo = findViewById(R.id.btnVideo)
        btnNext = findViewById(R.id.btnNext)
        img4 = findViewById(R.id.img4)
        videoView = findViewById(R.id.videoView)
        etRule=findViewById(R.id.etRule)
        btnImg4=findViewById(R.id.btnImg4)
        btnNext.setOnTouchListener(View.OnTouchListener { _, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN
                -> {
                    btnNext.setBackgroundColor(R.drawable.blue_btn_bg)
                }
                MotionEvent.ACTION_UP -> {
                    btnNext.setBackgroundResource(R.color.com_facebook_button_background_color)
                    submitFinal()
                }

            }
            return@OnTouchListener false
        })
        mStorage = FirebaseStorage.getInstance().reference.child("Uploads")


        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mDatabase.reference.child("OWNERS")

        permissionChecking()
        btnVideo.setOnClickListener { chooseVideo() }
        btnImg4.setOnClickListener { chooseImg4() }
    }



    private fun permissionChecking() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                200
            )
        }else{
            btnVideo.setOnClickListener { chooseVideo() }
            btnImg4.setOnClickListener { chooseImg4() }
        }

        if (
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                200
            )
        }

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 200)
        }

    }
    private fun chooseVideo() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, 1)
    }
    private fun chooseImg4() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, 5)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 1 && data != null) {
            uri = data.data!!
            try {
                val mediaController = MediaController(this)
                videoView.setVideoURI(uri)
                videoView.setMediaController(object : MediaController(this) {
                    override fun dispatchKeyEvent(event: KeyEvent): Boolean {
                        if (event.keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_UP)
                            mediaController.hide()
                        (this@OwnerActivity4).finish()
                        return super.dispatchKeyEvent(event)
                    }
                })
                mediaController.setAnchorView(videoView)
                videoView.start()
                mediaController.show(2000)
                if (mediaController.isShowing) {
                    mediaController.hide()
                }


            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
        else if (resultCode == Activity.RESULT_OK && requestCode == 5 && data != null) {
            uri4= data.data!!
//            img4.setImageURI(uri4)
            Picasso.get().load(uri4).fit().error(R.drawable.gradient_bg).into(img4)
        }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PermUtil.REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Pix.start(this, Options.init().setRequestCode(200))
                }
                else {
                    Toast.makeText(this, "Approve permissions to open ImagePicker", Toast.LENGTH_LONG).show()
                }
                return
            }
        }
    }

    private fun submitFinal() {
        if (uri != null  && uri4 != null) {
        val progress = ProgressDialog(this).apply {
            setTitle("Uploading Picture and a video....")
            setCancelable(false)
            setCanceledOnTouchOutside(false)
            show()
        }
            val etRule = etRule.text.toString()
            val intent = intent
            val userId = mAuth.currentUser!!.uid
            val minRent = intent.getStringExtra("minRent")
            val maxRent = intent.getStringExtra("maxRent")
            val fullName = intent.getStringExtra("fullName")
            val email = intent.getStringExtra("email")
            val number = intent.getStringExtra("number")
            val state = intent.getStringExtra("state")
            val city = intent.getStringExtra("city")
            val colony = intent.getStringExtra("colony")
            val landmark = intent.getStringExtra("landmark")
            val itemSelected=intent.getStringExtra("itemSelected")
            val spinnerString=intent.getStringExtra("spinnerString")
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
            val currentUserDb = mDatabaseReference.child(spinnerString!!)
            val currentUserDb1=currentUserDb.child(itemSelected!!)
            val currentUserDb2=currentUserDb1.child(userId)
            currentUserDb2.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {}
                override fun onDataChange(snapshot: DataSnapshot) {
                    maxId = snapshot.childrenCount
                    val anotherChild = currentUserDb2.child((maxId + 1).toString() + spinnerString.toString() + itemSelected.toString() + userId)
                    anotherChild.child("id")
                        .setValue((maxId + 1).toString() + spinnerString.toString() + itemSelected.toString() + userId)
                    anotherChild.child("fullName").setValue(fullName)
                    anotherChild.child("email").setValue(email)
                    anotherChild.child("number").setValue(number)
                    anotherChild.child("state").setValue(state)
                    anotherChild.child("city").setValue(city)
                    anotherChild.child("colony").setValue(colony)
                    anotherChild.child("landmark").setValue(landmark)
                    anotherChild.child("minRent").setValue(minRent)
                    anotherChild.child("maxRent").setValue(maxRent)
                    anotherChild.child("rule").setValue(etRule)
                    for (i in 0..22) {
                        val facilities = intent.getStringExtra(facilitiesArray[i])
                        anotherChild.child(facilitiesArray[i]).setValue(facilities)
                    }
                    val mReference = mStorage.child(uri?.lastPathSegment!!)
                    val uploadTask = mReference.putFile(uri!!)
                    uploadTask.addOnProgressListener { taskSnapshot ->
                        val value: Double =
                            (100.0 * taskSnapshot.bytesTransferred) / taskSnapshot.totalByteCount
                        Log.v("value", "value==$value")
                        progress.setMessage("Uploaded.. " + value.toInt() + "%")
                    }
                    try {
                        uploadTask.continueWith {
                            if (!it.isSuccessful) {
                                it.exception?.let { t -> throw t }
                            }
                            mReference.downloadUrl
                        }.addOnFailureListener {
                            Toast.makeText(
                                this@OwnerActivity4,
                                " video uploading fail! please try again ",
                                Toast.LENGTH_LONG
                            ).show()
                        }.addOnCompleteListener {
                            if (it.isSuccessful) {
                                updateUi()
                                progress.dismiss()
                                it.result!!.addOnSuccessListener { task ->
                                    val myUri = task.toString()
                                    Toast.makeText(
                                        this@OwnerActivity4,
                                        "video Uploaded Successfully ",
                                        Toast.LENGTH_LONG
                                    )
                                        .show()
                                    anotherChild.child("video").setValue(myUri)
                                    anotherChild.child("vPath").setValue(uri?.lastPathSegment)
                                }
                            }

                        }

                    } catch (e: java.lang.Exception) {
                        Toast.makeText(
                            this@OwnerActivity4,
                            "please upload images and video",
                            Toast.LENGTH_SHORT
                        ).show()
                    }


                    val mReference4 = mStorage.child(uri4?.lastPathSegment!!)
                    val uploadTask4 = mReference4.putFile(uri4!!)
                    uploadTask4.addOnProgressListener { taskSnapshot ->
                        val value: Double =
                            (100.0 * taskSnapshot.bytesTransferred) / taskSnapshot.totalByteCount
                        Log.v("value", "value==$value")
                        progress.setMessage("Uploaded.. " + value.toInt() + "%")
                    }
                    try {
                        uploadTask4.continueWith {
                            if (!it.isSuccessful) {
                                it.exception?.let { t -> throw t }
                            }
                            mReference4.downloadUrl
                        }.addOnFailureListener {
                            Toast.makeText(
                                this@OwnerActivity4,
                                " image uploading fail! please try again ",
                                Toast.LENGTH_LONG
                            ).show()
                        }.addOnCompleteListener {
                            if (it.isSuccessful) {
                                it.result!!.addOnSuccessListener { task ->
                                    val myUri = task.toString()
                                    Toast.makeText(
                                        this@OwnerActivity4,
                                        "image Uploaded Successfully ",
                                        Toast.LENGTH_LONG
                                    )
                                        .show()
                                    anotherChild.child("image").setValue(myUri)
                                    anotherChild.child("iPath").setValue(uri4?.lastPathSegment)
                                }
                            }

                        }

                    }
                    catch (e: java.lang.Exception) {
                        Toast.makeText(
                            this@OwnerActivity4,
                            "please upload images and video",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }

                }

            })



        }
        else {
            Toast.makeText(
                this,
                "please upload a images and a clean video of your property video..",
                Toast.LENGTH_LONG
            )
                .show()
        }
    }



    private fun updateUi(){
        startActivity(Intent(this, MainActivity2::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            finish()
        })
    }
}