package dev.yash.keymanager.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import app.yash.keymanager.R
import dev.yash.keymanager.models.SshKey

class SshAdapter(private val keys: List<SshKey>) :
    RecyclerView.Adapter<SshAdapter.SshViewHolder>() {

    class SshViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val keyName: TextView = view.findViewById(R.id.key_public)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SshViewHolder {
        val adapterLayout =
            LayoutInflater.from(parent.context).inflate(R.layout.key_card, parent, false)
        return SshViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: SshViewHolder, position: Int) {
        val key = keys[position]
        holder.keyName.text = key.key
    }

    override fun getItemCount(): Int = keys.size
}
