package com.example.cocktail.data.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cocktail.data.dataclass.CocktailDrink
import com.example.cocktail.data.dataclass.GlassListCategoryDrink
import com.example.cocktail.data.dataclass.OrdinaryDrink
import com.example.cocktail.databinding.RecyclerRowBinding

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
        val binding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class ViewHolder(private val binding: RecyclerRowBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
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
            binding.textRecyclerRow.text = text
            Glide.with(binding.root.context)
                .load(imageUrl)
                .into(binding.imageRecyclerRow)
        }

        private fun bindText(text: String) {
            binding.textRecyclerRow.text = text
            binding.imageRecyclerRow.visibility = View.GONE
        }
    }
}