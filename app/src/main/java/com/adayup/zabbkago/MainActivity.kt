package com.adayup.zabbkago

import Todo
import TodoApiService
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //make api call and display the text
        val service = RetrofitClient.retrofitInstance.create(TodoApiService::class.java)
        val call = service.getTodo()

        val napis = findViewById<TextView>(R.id.testID)
        call.enqueue(object : Callback<Todo> {
            override fun onResponse(call: Call<Todo>, response: Response<Todo>) {
                if (response.isSuccessful) {
                    val todo = response.body()
                    Log.d("MainActivity", "Todo: ${todo?.todo1}")
                    napis.text = "${todo?.todo1}"
                }
            }

            override fun onFailure(call: Call<Todo>, t: Throwable) {
                Log.e("MainActivity", "Error fetching todo", t)
            }
        })
    }
}