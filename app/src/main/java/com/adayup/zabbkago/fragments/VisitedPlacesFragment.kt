package com.adayup.zabbkago.fragments

import PageDetailsFragment
import StoreAdapter
import StoreItemClickListener
import android.animation.ObjectAnimator
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.adayup.zabbkago.R
import com.adayup.zabbkago.VisitedStoresRecyclerViewFiles.StoreItem
import com.adayup.zabbkago.apiFunctions.getPlaceDetailApiCall
import com.adayup.zabbkago.apiFunctions.getVisitedPlacesListApiCall
import kotlinx.coroutines.launch


class VisitedPlacesFragment : Fragment(), StoreItemClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var storeAdapter: StoreAdapter
    private lateinit var loadingImageView: ImageView

    override fun onStoreItemClick(storeItem: StoreItem) {
        // Create a new fragment instance
        val placeDetailsFragment = PageDetailsFragment()

        // If you need to pass data to the next fragment, add it to a Bundle and set it as arguments
        val args = Bundle()
        args.putString("storeName", storeItem.title)
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

}