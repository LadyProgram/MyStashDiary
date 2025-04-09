package com.ladyprogram.mystashdiary.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.ladyprogram.mystashdiary.data.Element
import com.ladyprogram.mystashdiary.databinding.ItemElementBinding

class ElementAdapter(
    var items: List<Element>,
    val onClick: (Int) -> Unit,
    val onDelete: (Int) -> Unit,
) : Adapter<ElementViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ElementViewHolder {
        val binding = ItemElementBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ElementViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ElementViewHolder, position: Int) {
        val element = items[position]
        holder.render(element)
        holder.itemView.setOnClickListener {
            onClick(position)
        }
        holder.binding.deleteButton.setOnClickListener {
            onDelete(position)
        }
    }

    fun updateItems(items: List<Element>) {
        this.items = items
        notifyDataSetChanged()
    }
}

class ElementViewHolder(val binding: ItemElementBinding) : ViewHolder(binding.root) {

    fun render(element: Element) {
        
            binding.nameTextView.text = element.name
      
    }
}