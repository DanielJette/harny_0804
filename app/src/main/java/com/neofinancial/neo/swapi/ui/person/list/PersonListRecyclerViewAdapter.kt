package com.neofinancial.neo.swapi.ui.person.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.neofinancial.neo.swapi.R
import com.neofinancial.neo.swapi.data.Person
import com.neofinancial.neo.swapi.data.isDroid
import com.neofinancial.neo.swapi.databinding.ListItemPersonBinding

class PersonListRecyclerViewAdapter(
    private val onItemClick: (Person) -> Unit
) : RecyclerView.Adapter<PersonListRecyclerViewAdapter.ViewHolder>() {

    private val values = mutableListOf<Person>()

    fun setValues(values:List<Person>) {
        this.values.clear()
        this.values.addAll(values)
        this.notifyDataSetChanged()
    }

    private val onClickListener: View.OnClickListener = View.OnClickListener { v ->
        val item = v.tag as Person
        onItemClick(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListItemPersonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]

        if (item.species?.isDroid == true) {
            holder.avatar.setImageResource(R.drawable.ic_droid)
        } else {
            holder.avatar.setImageIcon(null)
        }

        holder.nameView.text = item.name
        holder.descriptionView.text = item.species?.name

        with(holder.itemView) {
            tag = item
            setOnClickListener(onClickListener)
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: ListItemPersonBinding) : RecyclerView.ViewHolder(binding.root) {
        val nameView: TextView = binding.name
        val descriptionView: TextView = binding.description
        val avatar: ImageView = binding.profileImage
    }
}
