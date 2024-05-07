package com.adayup.zabbkago

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val registerButton: Button = findViewById(R.id.register_button)
        registerButton.setOnClickListener {
            Toast.makeText(this, "Registered successfully, please login now", Toast.LENGTH_SHORT).show();
            val i = Intent(this@RegisterActivity, MainActivity::class.java)

            // on below line we are calling start
            // activity method to start our activity.
            startActivity(i)

            // on below line we are calling
            // finish to finish our main activity.
            finish()
        }
    }
}