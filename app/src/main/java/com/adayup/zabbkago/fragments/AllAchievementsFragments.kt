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
import com.adayup.zabbkago.apiFunctions.getAllAchievementListApiCall
import kotlinx.coroutines.launch
import sharedKeys


class AllAchievementsFragments : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var achievementAdapter: AchievementAdapter

    private val keys = sharedKeys()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_all_achievements_fragments, container, false)
        recyclerView = view.findViewById(R.id.recycler_view4)
        recyclerView.layoutManager = LinearLayoutManager(context)
        lifecycleScope.launch {
            val achievements = getAllAchievementListApiCall()
            val data = mutableListOf<AchievementItem>()
            for (elem in achievements){
                data.add(AchievementItem(elem.name, 100, elem.description))
            }
            achievementAdapter = AchievementAdapter(data)
            recyclerView.adapter = achievementAdapter
        }

        return view
    }

}