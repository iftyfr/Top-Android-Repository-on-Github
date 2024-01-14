import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ifty.gitHubApiResponse.R
import com.ifty.gitHubApiResponse.model.Items

class RepositoryAdapter(private val onItemClick: (Items) -> Unit) :
    RecyclerView.Adapter<RepositoryAdapter.RepositoryViewHolder>() {

    private val repositories = mutableListOf<Items>()

    fun setRepositories(items: List<Items>) {
        repositories.clear()
        repositories.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.layout_item_repository, parent, false)
        return RepositoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        holder.bind(repositories[position])
    }

    override fun getItemCount(): Int = repositories.size

    inner class RepositoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(repository: Items) {
            itemView.findViewById<TextView>(R.id.tvRepositoryName).text = repository.name
            itemView.findViewById<TextView>(R.id.tvRepositoryOwner).text = "Owner Name: ${repository.owner?.login ?: "N/A"}"
            itemView.findViewById<TextView>(R.id.tvStarsCount).text = "Stars: ${repository.stargazersCount?.toString() ?: "0"}"


            itemView.setOnClickListener { onItemClick.invoke(repository) }

            Glide.with(itemView.context)
                .load(repository.owner?.avatarUrl)
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(itemView.findViewById(R.id.ivOwnerAvatar))
        }
    }
}
