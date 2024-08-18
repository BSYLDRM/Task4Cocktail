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
        when (adapterType) {
            AdapterType.COCKTAIL, AdapterType.RANDOM -> {
                if (item is CocktailDrink) {
                    holder.textView.text = item.strDrink
                    Glide.with(holder.itemView.context)
                        .load(item.strDrinkThumb)
                        .into(holder.imageView)
                }
            }

            AdapterType.ALCOHOLIC -> {
                if (item is CocktailDrink) {
                    holder.textView.text = item.strDrink
                    Glide.with(holder.itemView.context)
                        .load(item.strDrinkThumb)
                        .into(holder.imageView)
                }
            }

            AdapterType.ORDINARY_DRINK -> {
                if (item is OrdinaryDrink) {
                    holder.textView.text = item.strDrink
                    Glide.with(holder.itemView.context)
                        .load(item.strDrinkThumb)
                        .into(holder.imageView)
                }
            }

            AdapterType.GLASS_CATEGORIES -> {
                if (item is GlassListCategoryDrink) {
                    holder.textView.text = item.strGlass
                    holder.imageView.visibility = View.GONE
                }
            }

        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageRecyclerRow)
        val textView: TextView = itemView.findViewById(R.id.textRecyclerRow)

        init {
            itemView.setOnClickListener {
                onItemClick(itemList[adapterPosition])
            }
        }
    }
}