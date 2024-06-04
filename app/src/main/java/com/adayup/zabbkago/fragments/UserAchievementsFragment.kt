package com.adayup.zabbkago.fragments

import AchievementAdapter
import AchievementItem
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.adayup.zabbkago.R
import com.adayup.zabbkago.apiFunctions.getAchievementDetailsApiCall
import com.adayup.zabbkago.apiFunctions.getAchievementsListApiCall
import kotlinx.coroutines.launch
import sharedKeys


class UserAchievementsFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var achievementAdapter: AchievementAdapter
    private lateinit var seeAllBtn: Button

    private val keys = sharedKeys()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_user_achievements, container, false)
        recyclerView = view.findViewById(R.id.recycler_view3)
        recyclerView.layoutManager = LinearLayoutManager(context)
        val sharedPreferences = activity?.getSharedPreferences(keys.PREFS_KEY, Context.MODE_PRIVATE)
        lifecycleScope.launch {
            if(sharedPreferences != null){
                val userID = sharedPreferences.getString(keys.USER_ID_KEY, null).toString().toInt()
                val achievementsIDList = getAchievementsListApiCall(view.context, userID)
                val data = mutableListOf<AchievementItem>()
                for (elem in achievementsIDList){
                    val achievementData = getAchievementDetailsApiCall(view.context, elem.achievement_id)
                    data.add(AchievementItem(achievementData.name, 100, achievementData.description))
                }
                achievementAdapter = AchievementAdapter(data)
                recyclerView.adapter = achievementAdapter
            }
        }

        seeAllBtn = view.findViewById(R.id.see_all_button)

        seeAllBtn.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()?.apply {
                replace(R.id.container, AllAchievementsFragments()) // 'fragment_container' is the ID of your FrameLayout in the activity's layout
                addToBackStack(null) // Add transaction to back stack (optional)
                commit()
            }
        }

        return view
    }
}