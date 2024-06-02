package com.adayup.zabbkago.fragments

import StoreAdapter
import StoreItemClickListener
import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.widget.Button
import android.widget.ImageView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.adayup.zabbkago.R
import com.adayup.zabbkago.VisitedStoresRecyclerViewFiles.StoreItem
import com.adayup.zabbkago.apiFunctions.getPlaceDetailApiCall
import com.adayup.zabbkago.apiFunctions.getUserDetailsApiCall
import com.adayup.zabbkago.apiFunctions.getVisitedPlacesListApiCall
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class VisitedPlacesFragment : Fragment(), StoreItemClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var storeAdapter: StoreAdapter
    private lateinit var button: Button
    private lateinit var loadingImageView: ImageView
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

    override fun onStoreItemClick(storeItem: StoreItem) {
        // Create a new fragment instance
        val placeDetailsFragment = PageDetailsFragment()

        // If you need to pass data to the next fragment, add it to a Bundle and set it as arguments
        val args = Bundle()
        args.putString("storeName", storeItem.title) // Pass the store name for example
        args.putInt("storeID", storeItem.storeID)

        placeDetailsFragment.arguments = args

        // Replace the current fragment with the new one
        activity?.supportFragmentManager?.beginTransaction()?.apply {
            replace(R.id.container, placeDetailsFragment) // 'fragment_container' is the ID of your FrameLayout in the activity's layout
            addToBackStack(null) // Add transaction to back stack (optional)
            commit()
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_place, container, false)
        loadingImageView = view.findViewById(R.id.loading_image)
        val rotateAnimation = ObjectAnimator.ofFloat(loadingImageView, "rotation", 0f, 360f).apply {
            duration = 1000 // 1 second for a full rotation
            repeatCount = ObjectAnimator.INFINITE // Repeat indefinitely
            interpolator = LinearInterpolator() // Ensure smooth rotation
        }
        rotateAnimation.start()
        lifecycleScope.launch {
            recyclerView = view.findViewById(R.id.recycler_view)
            recyclerView.layoutManager = LinearLayoutManager(context)

            var data = listOf<StoreItem>()

            val visitedShops = getVisitedPlacesListApiCall(view.context)

            var ids = listOf<Int>()
            for (elem in visitedShops){
                if (!ids.contains(elem.shop_id)){
                    val placeDetail = getPlaceDetailApiCall(elem.shop_id)
                    val shop = StoreItem(placeDetail.name, R.drawable.store_baner, elem.shop_id)
                    data = data + shop
                    ids = ids + elem.shop_id
                }
            }
            storeAdapter = StoreAdapter(data, this@VisitedPlacesFragment)
            recyclerView.adapter = storeAdapter
            rotateAnimation.cancel()
            loadingImageView.visibility = View.INVISIBLE
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
         * @return A new instance of fragment PlaceFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            VisitedPlacesFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}