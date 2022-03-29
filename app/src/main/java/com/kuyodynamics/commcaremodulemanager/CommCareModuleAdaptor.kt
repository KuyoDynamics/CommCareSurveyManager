package com.kuyodynamics.commcaremodulemanager

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.commcaremodulemanager.R
import com.kuyodynamics.commcaremodulemanager.database.entities.CommCareModule

class CommCareModuleAdaptor: RecyclerView.Adapter<CommCareModuleViewHolder>() {
    var data = listOf<CommCareModule>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommCareModuleViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view =  layoutInflater.inflate(R.layout.module_item_view,parent,false) as TextView
        return  CommCareModuleViewHolder(view);
    }

    override fun onBindViewHolder(holder: CommCareModuleViewHolder, position: Int) {
        val item = data[position]
        holder.textView.text = item.name
    }

    override fun getItemCount(): Int {
        return data.size
    }

}