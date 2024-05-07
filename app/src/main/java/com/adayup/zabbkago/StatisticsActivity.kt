package com.adayup.zabbkago

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.adayup.zabbkago.fragments.AchievementsFragment
import com.adayup.zabbkago.fragments.HomeFragment
import com.adayup.zabbkago.fragments.VisitedPlacesFragment
import com.adayup.zabbkago.fragments.RankingFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class StatisticsActivity : AppCompatActivity() {

    lateinit var bottomNav : BottomNavigationView

    public fun loadFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.commit()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        loadFragment(HomeFragment())

        bottomNav = findViewById(R.id.navMenu)
        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    loadFragment(HomeFragment())
                    true
                }
                R.id.ranking -> {
                    loadFragment(RankingFragment())
                    true
                }
                R.id.achievements -> {
                    loadFragment(AchievementsFragment())
                    true
                }
                R.id.places -> {
                    loadFragment(VisitedPlacesFragment())
                    true
                }

                else -> {
                    Log.d("NAV ERROR", "Unexpected behaviour")
                    true
                }
            }
        }

        val backButton: ImageView = findViewById(R.id.back_button)
        backButton.setOnClickListener {
            val i = Intent(this@StatisticsActivity, MapsActivity::class.java)

            // on below line we are calling start
            // activity method to start our activity.
            startActivity(i)

            // on below line we are calling
            // finish to finish our main activity.
            finish()
        }
    }
}