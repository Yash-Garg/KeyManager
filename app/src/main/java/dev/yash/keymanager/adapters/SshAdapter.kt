package dev.yash.keymanager.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import app.yash.keymanager.R
import app.yash.keymanager.databinding.KeyCardBinding
import dev.yash.keymanager.models.SshKey
import javax.inject.Inject

class SshAdapter @Inject constructor() :
    PagingDataAdapter<SshKey, SshAdapter.SshViewHolder>(SshKeyComparator) {

    class SshViewHolder(binding: KeyCardBinding) : RecyclerView.ViewHolder(binding.root) {
        val publicKey: TextView = binding.keyPublic
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SshViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val adapterLayout =
            KeyCardBinding.inflate(inflater, parent, false)
        return SshViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: SshViewHolder, position: Int) {
        getItem(position)?.let { key ->
            holder.publicKey.text = key.key
            holder.itemView.setOnClickListener {
                it.findNavController().navigate(R.id.sshDetailsFragment)
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
