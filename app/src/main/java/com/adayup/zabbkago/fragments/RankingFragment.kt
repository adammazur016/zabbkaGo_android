package com.adayup.zabbkago.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.adayup.zabbkago.R
import com.adayup.zabbkago.apiFunctions.GetUserRankingListApiCall
import com.adayup.zabbkago.apiFunctions.getAchievementsListApiCall
import kotlinx.coroutines.launch


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RankingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RankingFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_ranking, container, false)

        val rankingList = view.findViewById<ListView>(R.id.ranking_list_view)

        lifecycleScope.launch {
            var ranking = listOf<String>()
            val usersList = GetUserRankingListApiCall(view.context)

            var i = 1

            for (elem in usersList){
                val rankname: String = i.toString() + ". " + elem.name + " - " + elem.points + " points"
                ranking = ranking + rankname
                i++
            }

            val adapter = ArrayAdapter<String>(requireActivity(), android.R.layout.simple_list_item_1, ranking)

            rankingList.adapter = adapter
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
         * @return A new instance of fragment RankingFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RankingFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}