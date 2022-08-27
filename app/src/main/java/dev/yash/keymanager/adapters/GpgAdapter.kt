package dev.yash.keymanager.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import dev.yash.keymanager.databinding.KeyCardBinding
import dev.yash.keymanager.models.GpgKey
import dev.yash.keymanager.ui.home.HomeFragmentDirections
import javax.inject.Inject

class GpgAdapter @Inject constructor() :
    PagingDataAdapter<GpgKey, GpgAdapter.GpgViewHolder>(GpgKeyComparator) {

    class GpgViewHolder(binding: KeyCardBinding) : RecyclerView.ViewHolder(binding.root) {
        val publicKey: TextView = binding.keyPublic
        val title: TextView = binding.keyTitle
        val encryption: TextView = binding.keyEncryption
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GpgViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val adapterLayout = KeyCardBinding.inflate(inflater, parent, false)
        return GpgViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: GpgViewHolder, position: Int) {
        getItem(position)?.let { key ->
            holder.title.text = key.name ?: key.keyID
            holder.publicKey.text = key.publicKey
            "GIT ID - ${key.id}".also { holder.encryption.text = it }
            holder.itemView.setOnClickListener { view ->
                val action = HomeFragmentDirections.actionHomeFragmentToGpgDetailsFragment(key)
                view.findNavController().navigate(action)
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
