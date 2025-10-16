package sibpardazan.gharb.satzpuzzle

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import sibpardazan.gharb.satzpuzzle.databinding.ItemReferenceUrlBinding

class ReferenceUrlAdapter(private val urls: List<String>) : RecyclerView.Adapter<ReferenceUrlAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemReferenceUrlBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(urls[position])
    }

    override fun getItemCount(): Int = urls.size

    inner class ViewHolder(private val binding: ItemReferenceUrlBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(url: String) {
            binding.urlTextView.text = url

            binding.root.setOnClickListener {
                openWebsite(url)
            }

            binding.urlTextView.setOnClickListener {
                openWebsite(url)
            }
        }

        private fun openWebsite(url: String) {
            try {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                binding.root.context.startActivity(intent)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}