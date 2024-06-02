package com.adayup.zabbkago

import com.adayup.zabbkago.responsesDataClasses.Auth
import com.adayup.zabbkago.interfaces.authApiService
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ActivityCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch


class LoginActivity : AppCompatActivity() {

    //creating variables to hold element of layouts
    private lateinit var emailEdt: EditText
    private lateinit var pwdEdt: EditText
    private lateinit var loginBtn: Button
    private lateinit var sharedPreferences: SharedPreferences

    //making the keys
    private val PREFS_KEY = "prefs"
    private val EMAIL_KEY = "email"
    private val PWD_KEY = "pwd"
    private val ID_KEY = "id"
    private val API_KEY = "api_key"
    private val permissionCode = 101

    //making the email and passwd
    private var email = ""
    private var pwd = ""

    //TODO: move function below to the apiFunctions as loginApiCall
    private suspend fun makeApiCall(par1: String, par2: String): String{
        val service = RetrofitClient.retrofitInstance.create(authApiService::class.java)

        val response: Response<Auth> = withContext(Dispatchers.IO) {
            service.getTodo(par1, par2)
        }
        if (response.isSuccessful) {
            val todo = response.body()
            todo?.let {
                val editor: SharedPreferences.Editor = sharedPreferences.edit()
                editor.putString(API_KEY, todo.session_token)
                editor.putString(ID_KEY, todo.user_id)
                editor.apply()
                return it.status // Assuming 'auth' is the field you're interested in
            }
        }
        return "Error or default value"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        if(ActivityCompat.checkSelfPermission(
                this, android.Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), permissionCode)
            return
        }

        //Binding the variables to elements from layout
        emailEdt = findViewById(R.id.idEdtEmail)
        pwdEdt = findViewById(R.id.idEdtPassword)
        loginBtn = findViewById(R.id.idBtnLogin)

        fun makeToast(){
            Toast.makeText(this, "Please Enter Email and Password", Toast.LENGTH_SHORT).show()
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

                    val result = makeApiCall(emailEdt.text.toString(), pwdEdt.text.toString())
                    Log.d("LoginResult", "Result: $result")

                    if(result == "success"){
                        //initializing the shared preferences editor
                        val editor: SharedPreferences.Editor = sharedPreferences.edit()

                        //save the email and the password to the shared pref
                        editor.putString(EMAIL_KEY, emailEdt.text.toString())
                        editor.putString(PWD_KEY, pwdEdt.text.toString())

                        //apply the changes
                        editor.apply()

                        //get to the maps
                        val i = Intent(this@LoginActivity, MapsActivity::class.java)
                        startActivity(i)

                        //close the current activity
                        finish()
                    } else {
                        makeToast()
                    }
                }
            }
        }
    }

    // If you open the app, you should be navigated to MapsActivity if you are logged already
    override fun onStart() {
        super.onStart()
        // in this method we are checking if email and pwd are not empty.
        // should be set when logged in successfully
        if (email !== "" && pwd !== "") {
            val i = Intent(this@LoginActivity, MapsActivity::class.java)
            startActivity(i)
            finish()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}