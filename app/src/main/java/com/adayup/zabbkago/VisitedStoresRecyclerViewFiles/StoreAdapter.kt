import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.adayup.zabbkago.MapsActivity
import com.adayup.zabbkago.R
import com.adayup.zabbkago.VisitedStoresRecyclerViewFiles.StoreItem


class StoreAdapter(private val storeItems: List<StoreItem>, private val itemClickListener: StoreItemClickListener) : RecyclerView.Adapter<StoreAdapter.StoreViewHolder>() {

    class StoreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val storeBanner: ImageView = itemView.findViewById(R.id.store_banner)
        val storeTitle: TextView = itemView.findViewById(R.id.store_title)
        val storeButton: Button = itemView.findViewById(R.id.store_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_store, parent, false)
        return StoreViewHolder(v)
    }

    override fun onBindViewHolder(holder: StoreViewHolder, position: Int) {
        val currentItem = storeItems[position]
        holder.storeTitle.text = currentItem.title
        // Set image for holder.storeBanner using Glide or similar
        // Set click listener for holder.storeButton
        holder.storeButton.setOnClickListener {
            itemClickListener.onStoreItemClick(currentItem)
        }
    }

    override fun getItemCount(): Int {
        return storeItems.size
    }
}
