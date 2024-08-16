package com.example.cocktail.data.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cocktail.data.dataclass.Categories
import com.example.cocktail.databinding.RecylerCotegoriesItemBinding


class CategoriesAdapter(
    private val categoriesList: List<Categories>,
    private val onItemClick: (Categories) -> Unit
) : RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        val binding = RecylerCotegoriesItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoriesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        val category = categoriesList[position]
        holder.bind(category)
    }

    override fun getItemCount(): Int = categoriesList.size

    inner class CategoriesViewHolder(private val binding: RecylerCotegoriesItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(category: Categories) {
            binding.textViewCategories.text = category.buttonString
            Glide.with(binding.root.context)
                .load(category.imageUrl)
                .into(binding.imageViewCategories)

            binding.root.setOnClickListener {
                onItemClick(category)
            }
        }
    }
}