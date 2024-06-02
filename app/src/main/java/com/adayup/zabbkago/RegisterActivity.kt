package com.adayup.zabbkago

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.widget.AppCompatEditText
import androidx.lifecycle.lifecycleScope
import com.adayup.zabbkago.apiFunctions.registerApiCall
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch
import org.w3c.dom.Text

class RegisterActivity : AppCompatActivity() {

    private lateinit var usernameInput: AppCompatEditText
    private lateinit var passwordInput: AppCompatEditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val registerButton: Button = findViewById(R.id.register_button)
        registerButton.setOnClickListener {
            usernameInput = findViewById(R.id.idEdtEmail)
            passwordInput = findViewById(R.id.idEdtPassword)
            val username = usernameInput.text.toString()
            val password = passwordInput.text.toString()
            // TODO: Make the register API call
            lifecycleScope.launch {
                val response = registerApiCall(username, password)
                if(response.status == "success"){
                    Toast.makeText(applicationContext, "Registered successfully, please login now", Toast.LENGTH_SHORT).show();
                    // Redirecting to the menu with login option.
                    val i = Intent(this@RegisterActivity, MainActivity::class.java)
                    startActivity(i)
                    finish()
                }
            }

        }
    }
}