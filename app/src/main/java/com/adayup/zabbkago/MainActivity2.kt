package com.adayup.zabbkago

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView.FindListener
import android.widget.Button
import android.widget.TextView

class MainActivity2 : AppCompatActivity() {
    //initiate the variables
    lateinit var userTV: TextView
    lateinit var logoutBtn: Button
    lateinit var seeMapBtn: Button
    var PREFS_KEY = "prefs"
    var EMAIL_KEY = "email"
    var PWD_KEY = "pwd"
    var ID_KEY = "id"
    var API_KEY = "api_key"
    var email = ""
    var apiKey = ""
    var userId = ""

    lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        userTV = findViewById(R.id.idTVUserName)
        logoutBtn = findViewById(R.id.idBtnLogOut)
        seeMapBtn = findViewById(R.id.idSeeMapsBtn)
//
        sharedPreferences = getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE)
//
        email = sharedPreferences.getString(EMAIL_KEY, null).toString()
        userId = sharedPreferences.getString(ID_KEY, null).toString()
        apiKey = sharedPreferences.getString(API_KEY, null).toString()
//
        userTV.setText("hej\n$email\nYour user id is: $userId\nYour api_key is $apiKey")

        logoutBtn.setOnClickListener {
            val editor: SharedPreferences.Editor = sharedPreferences.edit()

            editor.clear()

            editor.apply()

            val i = Intent(this@MainActivity2, MainActivity::class.java)
            startActivity(i)
            finish()
        }

        seeMapBtn.setOnClickListener {
            val i = Intent(this@MainActivity2, maps::class.java)
            startActivity(i)
            finish()
        }
    }
}