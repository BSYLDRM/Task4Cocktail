package com.example.cocktail.fragment.glassCategories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.cocktail.R
import com.example.cocktail.data.adapter.GenericAdapter
import com.example.cocktail.data.adapter.AdapterType
import com.example.cocktail.databinding.FragmentGlassBinding
import com.example.cocktail.viewModel.CocktailViewModel

class GlassFragment : Fragment() {

    private var _binding: FragmentGlassBinding? = null
    private val binding get() = _binding!!
    private val cocktailViewModel: CocktailViewModel by viewModels()
    private lateinit var adapter: GenericAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGlassBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeViewModel()
        setupHomeIcon()
    }

    private fun setupHomeIcon() {
        val homeIcon: View = binding.root.findViewById(R.id.homeIcon)
        homeIcon.setOnClickListener {
            findNavController().navigate(R.id.categoriesFragment)
        }
    }

    private fun setupRecyclerView() {
        adapter = GenericAdapter(emptyList(), AdapterType.GLASS_CATEGORIES) { }
        binding.recyclerRowGlass.adapter = adapter
    }

    private fun observeViewModel() {
        cocktailViewModel.glassCategories.observe(viewLifecycleOwner) { categories ->
            adapter.updateItems(categories)
        }
        cocktailViewModel.fetchGlassCategories()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}