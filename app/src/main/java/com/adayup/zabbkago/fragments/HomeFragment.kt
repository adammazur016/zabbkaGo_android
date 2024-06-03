package com.adayup.zabbkago.fragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.adayup.zabbkago.MainActivity
import com.adayup.zabbkago.MapsActivity
import com.adayup.zabbkago.R
import com.adayup.zabbkago.apiFunctions.getUserDetailsApiCall
import com.adayup.zabbkago.apiFunctions.getVisitedPlacesListApiCall
import kotlinx.coroutines.launch
import sharedKeys
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone


class HomeFragment : Fragment() {
    private lateinit var logoutButton: Button
    private lateinit var todayVisitTextView: TextView
    private lateinit var totalVisitTextView: TextView
    private lateinit var rankingPointTextView: TextView


    //making the keys
    private val keys = sharedKeys()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_home, container, false)
        val sharedPreferences = activity?.getSharedPreferences(keys.PREFS_KEY, Context.MODE_PRIVATE)
        val usernameTextView = view.findViewById<TextView>(R.id.home_page_username)
        todayVisitTextView = view.findViewById(R.id.today_visit_count)
        totalVisitTextView = view.findViewById(R.id.total_visit_count)
        rankingPointTextView = view.findViewById(R.id.ranking_points_count)


        lifecycleScope.launch {
            val result = getVisitedPlacesListApiCall(view.context)
            var i = 0
            for(elem in result){
                val dateFormat = SimpleDateFormat("EEE, dd MMM yyyy", Locale.ENGLISH)
                dateFormat.timeZone = TimeZone.getTimeZone("GMT")
                val currentDate = Date()
                val formattedDate = dateFormat.format(currentDate)
                val fullDate = formattedDate + " 00:00:00 GMT"
                Log.d("DATE", formattedDate.toString())
                if(elem.date == fullDate){
                    i++;
                }
            }
            todayVisitTextView.text = i.toString()
            totalVisitTextView.text = result.size.toString()

            if (sharedPreferences != null) {
                usernameTextView.text = sharedPreferences.getString(keys.EMAIL_KEY, null).toString()
                rankingPointTextView.text = sharedPreferences.getString(keys.RANK_KEY, null).toString()
            }

        }



        logoutButton = view.findViewById(R.id.logout_button)
        logoutButton.setOnClickListener {

            sharedPreferences?.let {
                val editor = it.edit()
                editor.putString(keys.EMAIL_KEY, "")
                editor.putString(keys.PWD_KEY, "")
                editor.apply()
            }

            //get to the
            val i = Intent(view.context, MainActivity::class.java)

            startActivity(i)

            //close the current activity
            activity?.finish()
        }

        return view
    }

}