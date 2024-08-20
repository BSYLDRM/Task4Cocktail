package com.example.cocktail.fragment.glassCategories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.cocktail.data.adapter.AdapterFactory
import com.example.cocktail.data.adapter.GenericAdapter
import com.example.cocktail.data.adapter.AdapterType
import com.example.cocktail.databinding.FragmentGlassBinding
import com.example.cocktail.fragment.base.BaseListFragment
import com.example.cocktail.viewModel.CocktailViewModel

class GlassFragment : BaseListFragment<FragmentGlassBinding>() {
    override val viewModel: CocktailViewModel by viewModels()
    private lateinit var adapter: GenericAdapter

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentGlassBinding {
        return FragmentGlassBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeViewModel()
        setupHomeIcon()
    }

    private fun setupRecyclerView() {
        adapter = AdapterFactory.createAdapter(AdapterType.GLASS_CATEGORIES) { }
        setupRecyclerView(adapter, binding.recyclerRowGlass)
    }

    private fun observeViewModel() {
        observeData(viewModel.glassCategories, adapter)
        viewModel.fetchGlassCategories()
    }
}