package com.adayup.zabbkago

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity2 : AppCompatActivity() {
    //initiate the variables
    lateinit var userTV: TextView
    lateinit var logoutBtn: Button
    var PREFS_KEY = "prefs"
    var EMAIL_KEY = "email"
    var email = ""

    lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        userTV = findViewById(R.id.idTVUserName)
        logoutBtn = findViewById(R.id.idBtnLogOut)
//
        sharedPreferences = getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE)
//
        email = sharedPreferences.getString(EMAIL_KEY, null).toString()
//
        userTV.setText("hej\n$email")

        logoutBtn.setOnClickListener {
            val editor: SharedPreferences.Editor = sharedPreferences.edit()

            editor.clear()

            editor.apply()

            val i = Intent(this@MainActivity2, MainActivity::class.java)
            startActivity(i)
            finish()
        }
    }
}