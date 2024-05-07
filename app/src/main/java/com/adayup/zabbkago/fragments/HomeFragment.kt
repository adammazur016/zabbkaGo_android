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
import com.adayup.zabbkago.MainActivity
import com.adayup.zabbkago.MapsActivity
import com.adayup.zabbkago.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var logoutButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }


    //making the keys
    var PREFS_KEY = "prefs"
    var EMAIL_KEY = "email"
    var PWD_KEY = "pwd"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_home, container, false)
        val sharedPreferences = activity?.getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE)
        val usernameTextView = view.findViewById<TextView>(R.id.home_page_username)

        if (sharedPreferences != null) {
            usernameTextView.text = sharedPreferences.getString(EMAIL_KEY, null).toString()
        }

        logoutButton = view.findViewById(R.id.logout_button)
        logoutButton.setOnClickListener {


            //getting the email data from the shared preferences and writing it to email var

            sharedPreferences?.let {
                val editor = it.edit()
                editor.putString(EMAIL_KEY, "")
                editor.putString(PWD_KEY, "")
                editor.apply()
            }

            //save the email and the password to the shared pref


            //get to the
            val i = Intent(view.context, MainActivity::class.java)

            startActivity(i)

            //close the current activity
            activity?.finish()
        } ?: run {
            Log.e("SharedPreferencesError", "Failed to retrieve shared preferences.")
        }

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}