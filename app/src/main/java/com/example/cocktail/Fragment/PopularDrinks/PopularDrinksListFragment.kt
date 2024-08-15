package com.example.cocktail.Fragment.PopularDrinks

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cocktail.data.AdapterType
import com.example.cocktail.data.CocktailDrink
import com.example.cocktail.data.GenericAdapter
import com.example.cocktail.databinding.FragmentPopularDrinksBinding
import com.example.cocktail.viewModel.CocktailViewModel

class PopularDrinksListFragment : Fragment() {
    private var _binding: FragmentPopularDrinksBinding? = null
    private val binding get() = _binding!!

    private val cocktailViewModel: CocktailViewModel by activityViewModels()
    private lateinit var populerAdapter: GenericAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPopularDrinksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        binding.recyclerRowPopularDrinks.layoutManager = LinearLayoutManager(requireContext())

        populerAdapter = GenericAdapter(emptyList(), AdapterType.COCKTAIL) { item ->
            if (item is CocktailDrink) {
                val action =
                    PopularDrinksListFragmentDirections.actionPopularDrinksFragmentToPopularDrinksDetailFragment(
                        item.idDrink
                    )
                findNavController().navigate(action)
            }
        }

        binding.recyclerRowPopularDrinks.adapter = populerAdapter
    }

    private fun observeViewModel() {
        cocktailViewModel.randomCocktails.observe(viewLifecycleOwner) { cocktails ->
            populerAdapter.updateItems(cocktails)
        }
        cocktailViewModel.fetchRandomCocktails()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
