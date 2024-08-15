package com.example.cocktail.Fragment.GlassCategories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cocktail.data.GenericAdapter
import com.example.cocktail.data.AdapterType
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
    ): View? {
        _binding = FragmentGlassBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        adapter = GenericAdapter(emptyList(), AdapterType.GLASS_CATEGORIES) {  }
        binding.recyclerRowGlass.layoutManager = LinearLayoutManager(requireContext())
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





