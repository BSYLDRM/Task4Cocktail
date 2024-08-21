package com.example.cocktail.fragment.alcohol

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.cocktail.data.adapter.AdapterFactory
import com.example.cocktail.data.adapter.AdapterType
import com.example.cocktail.data.adapter.GenericAdapter
import com.example.cocktail.data.dataclass.CocktailDrink
import com.example.cocktail.databinding.FragmentAlcoholDrinksListBinding
import com.example.cocktail.fragment.base.BaseListFragment
import com.example.cocktail.viewModel.CocktailViewModel

class AlcoholDrinksListFragment : BaseListFragment<FragmentAlcoholDrinksListBinding>() {
    override val viewModel: CocktailViewModel by activityViewModels()
    private lateinit var alcoholAdapter: GenericAdapter

    override fun inflateBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentAlcoholDrinksListBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        alcoholAdapter = AdapterFactory.createAdapter(AdapterType.ALCOHOLIC) { item ->
            val cocktail = item as CocktailDrink
            val action =
                AlcoholDrinksListFragmentDirections.actionAlcoholDrinksListFragmentToDetailFragment(
                    cocktail.idDrink
                )
            findNavController().navigate(action)
        }

        setupRecyclerView(alcoholAdapter, binding.alcoholDrinksRecyclerView)
        observeData(viewModel.filteredCocktails, alcoholAdapter)

        viewModel.fetchAlcoholicCocktails()
    }
}