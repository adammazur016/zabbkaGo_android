package com.adayup.zabbkago

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText

class MainActivity : AppCompatActivity() {

    //creating variables to hold element of layouts
    lateinit var emailEdt: EditText
    lateinit var pwdEdt: EditText
    lateinit var loginBtn: Button
    lateinit var sharedPreferences: SharedPreferences

    //making the keys
    var PREFS_KEY = "prefs"
    var EMAIL_KEY = "email"
    var PWD_KEY = "pwd"
    var ID_KEY = "id"
    var API_KEY = "api_key"
    var RANK_KEY = "rank"

    //making the email and passwd
    var email = ""
    var pwd = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val loginButton = findViewById<Button>(R.id.login_button)
        loginButton.setOnClickListener {
            val i = Intent(this@MainActivity, LoginActivity::class.java)

            // on below line we are calling start
            // activity method to start our activity.
            startActivity(i)

            // on below line we are calling
            // finish to finish our main activity.
            finish()
        }

        val registerButton: Button = findViewById(R.id.register_button_main)
        registerButton.setOnClickListener {
            val i = Intent(this@MainActivity, RegisterActivity::class.java)

            // on below line we are calling start
            // activity method to start our activity.
            startActivity(i)

            // on below line we are calling
            // finish to finish our main activity.
            finish()
        }

        //getting the email data from the shared preferences and writing it to email var
        sharedPreferences = getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE)
        email = sharedPreferences.getString(EMAIL_KEY, "").toString()

        //same for pwd
        pwd = sharedPreferences.getString(PWD_KEY, "").toString()
    }



    override fun onStart() {
        super.onStart()
        // in this method we are checking if email and pwd are not empty.
        if (!email.equals("") && !pwd.equals("")) {
            Log.d("DEBUG", email)
            Log.d("DEBUG", pwd)
            // if email and pwd is not empty we
            // are opening our main 2 activity on below line.
            val i = Intent(this@MainActivity, MapsActivity::class.java)

            // on below line we are calling start
            // activity method to start our activity.
            startActivity(i)

            // on below line we are calling
            // finish to finish our main activity.
            finish()
        }
    }
}