package com.example.cocktail.data.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cocktail.R
import com.example.cocktail.data.dataclass.CocktailDrink
import com.example.cocktail.data.dataclass.GlassListCategoryDrink
import com.example.cocktail.data.dataclass.OrdinaryDrink

class GenericAdapter(
    private var itemList: List<Any>,
    private val adapterType: AdapterType,
    private val onItemClick: (Any) -> Unit
) : RecyclerView.Adapter<GenericAdapter.ViewHolder>() {

    fun updateItems(newItems: List<Any>) {
        itemList = newItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.imageRecyclerRow)
        private val textView: TextView = itemView.findViewById(R.id.textRecyclerRow)

        init {
            itemView.setOnClickListener {
                onItemClick(itemList[adapterPosition])
            }
        }

        fun bind(item: Any) {
            when (adapterType) {
                AdapterType.COCKTAIL, AdapterType.RANDOM, AdapterType.ALCOHOLIC -> {
                    if (item is CocktailDrink) {
                        bindTextAndImage(item.strDrink, item.strDrinkThumb)
                    }
                }
                AdapterType.ORDINARY_DRINK -> {
                    if (item is OrdinaryDrink) {
                        bindTextAndImage(item.strDrink, item.strDrinkThumb)
                    }
                }
                AdapterType.GLASS_CATEGORIES -> {
                    if (item is GlassListCategoryDrink) {
                        bindText(item.strGlass)
                    }
                }
            }
        }

        private fun bindTextAndImage(text: String, imageUrl: String) {
            textView.text = text
            Glide.with(itemView.context)
                .load(imageUrl)
                .into(imageView)
        }

        private fun bindText(text: String) {
            textView.text = text
            imageView.visibility = View.GONE
        }
    }
}