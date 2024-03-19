package com.adayup.zabbkago

import Todo
import TodoApiService
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


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

    //making the email and passwd
    var email = ""
    var pwd = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Binding the variables to elements from layout
        emailEdt = findViewById(R.id.idEdtEmail)
        pwdEdt = findViewById(R.id.idEdtPassword)
        loginBtn = findViewById(R.id.idBtnLogin)

        //Initializing shared preferences with the key to them
        sharedPreferences = getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE)

        //getting the email data from the shared preferences and writing it to email var
        email = sharedPreferences.getString(EMAIL_KEY, "").toString()

        //same for pwd
        pwd = sharedPreferences.getString(PWD_KEY, "").toString()

        //now listening for btn to be clicked
        loginBtn.setOnClickListener {
            //if there is no data entered display en error
            if (TextUtils.isEmpty(emailEdt.text.toString()) || TextUtils.isEmpty(pwdEdt.text.toString())){
                Toast.makeText(this, "Please Enter Email and Password", Toast.LENGTH_SHORT).show();
            } else {
                //else save the input
                //initializing the shared preferences editor
                val editor: SharedPreferences.Editor = sharedPreferences.edit()

                //save the email and the password to the shared pref
                Log.d("Put", emailEdt.text.toString())
                Log.d("Put", pwdEdt.text.toString())
                editor.putString(EMAIL_KEY, emailEdt.text.toString())
                editor.putString(PWD_KEY, pwdEdt.text.toString())

                //apply the changes
                editor.apply()

                //get to the mainactivity2
                val i = Intent(this@MainActivity, MainActivity2::class.java)

                startActivity(i)

                //close the current activity
                finish()
            }
        }
    }
    override fun onStart() {
        super.onStart()
        // in this method we are checking if email and pwd are not empty.
        if (!email.equals("") && !pwd.equals("")) {
            Log.d("DEBUG", email)
            Log.d("DEBUG", pwd)
            // if email and pwd is not empty we
            // are opening our main 2 activity on below line.
            val i = Intent(this@MainActivity, MainActivity2::class.java)

            // on below line we are calling start
            // activity method to start our activity.
            startActivity(i)

            // on below line we are calling
            // finish to finish our main activity.
            finish()
        }
    }
}