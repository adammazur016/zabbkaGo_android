package com.adayup.zabbkago

import Auth
import authApiService
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch


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

    //making the email and passwd
    var email = ""
    var pwd = ""

    suspend fun makeApiCall(par1: String, par2: String): String{
        val service = RetrofitClient.retrofitInstance.create(authApiService::class.java)

        val response: Response<Auth> = withContext(Dispatchers.IO) {
            service.getTodo(par1, par2)
        }
        if (response.isSuccessful) {
            val todo = response.body()
            todo?.let {
                val editor: SharedPreferences.Editor = sharedPreferences.edit()
                editor.putString(ID_KEY, todo.id.toString())
                editor.putString(API_KEY, todo.api_key.toString())
                editor.apply()
                return it.auth // Assuming 'auth' is the field you're interested in
            }
        }
        return "Error or default value"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Binding the variables to elements from layout
        emailEdt = findViewById(R.id.idEdtEmail)
        pwdEdt = findViewById(R.id.idEdtPassword)
        loginBtn = findViewById(R.id.idBtnLogin)

        fun makeToast(){
            Toast.makeText(this, "Please Enter Email and Password", Toast.LENGTH_SHORT).show();
        }

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
                makeToast()
            } else {
                //else save the input

                //API call goes here
                lifecycleScope.launch {
                    // Since makeApiCall is a suspend function, it can be called directly here
                    val result = makeApiCall(emailEdt.text.toString(), pwdEdt.text.toString())
                    // Use the result, e.g., update UI or log the result
                    // Make sure to perform UI operations on the main thread
                    Log.d("LoginResult", "Result: $result")
                    // If updating UI, make sure this is run on the main thread. For example:
                    // textView.text = result

                    if(result.toString() == "1"){
                        Log.d("DEBUG", "poprawnie zalogowano")
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
                    } else {
                        Log.d("DEBUG", "NIEPOPRAWNIE ZALOGOWANO")
                        makeToast()
                    }
                }
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