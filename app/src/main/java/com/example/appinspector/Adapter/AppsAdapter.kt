package com.example.AppInspector.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.AppInspector.Model.InstalledApp
import com.example.appinspector.R

class AppsAdapter(
    private val items: List<InstalledApp>,
    private val onClick: (InstalledApp) -> Unit
) : RecyclerView.Adapter<AppsAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val icon: ImageView = view.findViewById(R.id.imgIcon)
        val title: TextView = view.findViewById(R.id.txtTitle)
        val pkg: TextView = view.findViewById(R.id.txtPackage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_app, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val app = items[position]
        holder.icon.setImageDrawable(app.icon)
        holder.title.text = app.appName
        holder.pkg.text = app.packageName

        holder.itemView.setOnClickListener { onClick(app) }
    }

    override fun getItemCount(): Int = items.size
}
