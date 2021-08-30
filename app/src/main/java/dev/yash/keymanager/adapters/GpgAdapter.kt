package dev.yash.keymanager.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import app.yash.keymanager.R
import dev.yash.keymanager.models.GpgKey
import javax.inject.Inject

class GpgAdapter @Inject constructor() :
    PagingDataAdapter<GpgKey, GpgAdapter.GpgViewHolder>(GpgKeyComparator) {

    class GpgViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val publicKey: TextView = view.findViewById(R.id.key_public)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GpgViewHolder {
        val adapterLayout =
            LayoutInflater.from(parent.context).inflate(R.layout.key_card, parent, false)
        return GpgViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: GpgViewHolder, position: Int) {
        getItem(position)?.let { key ->
            holder.publicKey.text = key.publicKey

            holder.itemView.setOnClickListener {
                it.findNavController().navigate(R.id.action_homeFragment_to_gpgDetailsFragment)
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
