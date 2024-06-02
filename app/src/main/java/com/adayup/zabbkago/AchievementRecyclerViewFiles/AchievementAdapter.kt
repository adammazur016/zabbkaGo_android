// In this file the list of achievement is displayed as recycler view.
// It uses item_achievement.xml layout for every element of the list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.adayup.zabbkago.R

class AchievementAdapter(private val achievementItem: List<AchievementItem>) : RecyclerView.Adapter<AchievementAdapter.AchievementViewHolder>() {

    class AchievementViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.achievement_title)
        val description: TextView = itemView.findViewById(R.id.achievement_description)
        val progressBar: ProgressBar = itemView.findViewById(R.id.achievement_progress)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AchievementViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_achievement, parent, false)
        return AchievementViewHolder(v)
    }

    override fun onBindViewHolder(holder: AchievementViewHolder, position: Int) {
        val currentItem = achievementItem[position]
        holder.title.text = currentItem.title
        holder.description.text = currentItem.description
        holder.progressBar.progress = currentItem.progress
    }

    override fun getItemCount(): Int {
        return achievementItem.size
    }
}
