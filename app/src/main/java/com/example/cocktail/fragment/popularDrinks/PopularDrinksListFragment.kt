package com.example.cocktail.fragment.popularDrinks

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.cocktail.R
import com.example.cocktail.data.adapter.AdapterType
import com.example.cocktail.data.dataclass.CocktailDrink
import com.example.cocktail.data.adapter.GenericAdapter
import com.example.cocktail.databinding.FragmentPopularDrinksBinding
import com.example.cocktail.viewModel.CocktailViewModel

class PopularDrinksListFragment : Fragment() {
    private var _binding: FragmentPopularDrinksBinding? = null
    private val binding get() = _binding!!

    private val cocktailViewModel: CocktailViewModel by activityViewModels()
    private lateinit var popularAdapter: GenericAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPopularDrinksBinding.inflate(inflater, container, false)
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
        popularAdapter = GenericAdapter(emptyList(), AdapterType.COCKTAIL) { item ->
            if (item is CocktailDrink) {
                val action =
                    PopularDrinksListFragmentDirections.actionPopularDrinksListFragmentToPopularDrinksDetailFragment(
                        item.idDrink
                    )
                findNavController().navigate(action)
            }
        }
        binding.recyclerRowPopularDrinks.adapter = popularAdapter
    }

    private fun observeViewModel() {
        cocktailViewModel.randomCocktails.observe(viewLifecycleOwner) { cocktails ->
            popularAdapter.updateItems(cocktails)
        }
        cocktailViewModel.fetchRandomCocktails()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}