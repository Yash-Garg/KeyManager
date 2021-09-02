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
import dev.yash.keymanager.models.GpgKey
import javax.inject.Inject

class GpgAdapter @Inject constructor() :
    PagingDataAdapter<GpgKey, GpgAdapter.GpgViewHolder>(GpgKeyComparator) {

    class GpgViewHolder(binding: KeyCardBinding) : RecyclerView.ViewHolder(binding.root) {
        val publicKey: TextView = binding.keyPublic
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GpgViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val adapterLayout =
            KeyCardBinding.inflate(inflater, parent, false)
        return GpgViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: GpgViewHolder, position: Int) {
        getItem(position)?.let { key ->
            holder.publicKey.text = key.publicKey
            holder.itemView.setOnClickListener {
                it.findNavController().navigate(R.id.gpgDetailsFragment)
            }
        }
    }

    object GpgKeyComparator : DiffUtil.ItemCallback<GpgKey>() {
        override fun areItemsTheSame(oldItem: GpgKey, newItem: GpgKey): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: GpgKey, newItem: GpgKey): Boolean {
            return oldItem == newItem
        }
    }
}
