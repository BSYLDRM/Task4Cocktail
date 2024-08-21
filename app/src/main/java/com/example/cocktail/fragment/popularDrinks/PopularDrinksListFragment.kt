package com.example.cocktail.fragment.popularDrinks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.cocktail.data.adapter.AdapterFactory
import com.example.cocktail.data.adapter.AdapterType
import com.example.cocktail.data.dataclass.CocktailDrink
import com.example.cocktail.data.adapter.GenericAdapter
import com.example.cocktail.databinding.FragmentPopularDrinksBinding
import com.example.cocktail.fragment.base.BaseListFragment
import com.example.cocktail.viewModel.CocktailViewModel

class PopularDrinksListFragment : BaseListFragment<FragmentPopularDrinksBinding>() {

    override val viewModel: CocktailViewModel by activityViewModels()
    private lateinit var popularAdapter: GenericAdapter

    override fun inflateBinding(
        inflater: LayoutInflater, container: ViewGroup?
    ): FragmentPopularDrinksBinding {
        return FragmentPopularDrinksBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        popularAdapter = AdapterFactory.createAdapter(AdapterType.COCKTAIL) { item ->
            if (item is CocktailDrink) {
                val action =
                    PopularDrinksListFragmentDirections.actionPopularDrinksListFragmentToDetailFragment(
                        item.idDrink
                    )
                findNavController().navigate(action)
            }
        }
        setupRecyclerView(popularAdapter, binding.recyclerRowPopularDrinks)
    }

    private fun observeViewModel() {
        observeData(viewModel.randomCocktails, popularAdapter)
        viewModel.fetchRandomCocktails()
    }
}