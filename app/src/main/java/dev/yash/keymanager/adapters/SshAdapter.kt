package dev.yash.keymanager.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import app.yash.keymanager.R
import dev.yash.keymanager.models.SshKey
import javax.inject.Inject

class SshAdapter @Inject constructor() :
    PagingDataAdapter<SshKey, SshAdapter.SshViewHolder>(SshKeyComparator) {

    class SshViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val publicKey: TextView = view.findViewById(R.id.key_public)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SshViewHolder {
        val adapterLayout =
            LayoutInflater.from(parent.context).inflate(R.layout.key_card, parent, false)
        return SshViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: SshViewHolder, position: Int) {
        getItem(position)?.let { key ->
            holder.publicKey.text = key.key

            holder.itemView.setOnLongClickListener {
                // TODO: Do stuff
                println("Key ID - ${key.id}")
                true
            }
        }
    }

    object SshKeyComparator : DiffUtil.ItemCallback<SshKey>() {
        override fun areItemsTheSame(oldItem: SshKey, newItem: SshKey): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: SshKey, newItem: SshKey): Boolean {
            return oldItem == newItem
        }
    }
}
