package com.example.cocktail.fragment.ordinaryDrink

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.cocktail.data.adapter.AdapterFactory
import com.example.cocktail.data.adapter.AdapterType
import com.example.cocktail.data.adapter.GenericAdapter
import com.example.cocktail.data.dataclass.OrdinaryDrink
import com.example.cocktail.databinding.FragmentOrdinaryDrinksListBinding
import com.example.cocktail.fragment.base.BaseListFragment
import com.example.cocktail.viewModel.CocktailViewModel

class OrdinaryDrinksListFragment : BaseListFragment<FragmentOrdinaryDrinksListBinding>() {

    override val viewModel: CocktailViewModel by activityViewModels()
    private lateinit var ordinaryDrinkAdapter: GenericAdapter

    override fun inflateBinding(
        inflater: LayoutInflater, container: ViewGroup?
    ): FragmentOrdinaryDrinksListBinding {
        return FragmentOrdinaryDrinksListBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeViewModel()
        setupHomeIcon()
    }

    private fun setupRecyclerView() {
        ordinaryDrinkAdapter = AdapterFactory.createAdapter(
            type = AdapterType.ORDINARY_DRINK
        ) { item ->
            if (item is OrdinaryDrink) {
                val action =
                    OrdinaryDrinksListFragmentDirections.actionOrdinaryDrinksListFragmentToDetailFragment(
                        item.idDrink
                    )
                findNavController().navigate(action)
            }
        }
        setupRecyclerView(ordinaryDrinkAdapter, binding.recyclerRowOrdinaryDrinks)
    }

    private fun observeViewModel() {
        observeData(viewModel.ordinaryDrinks, ordinaryDrinkAdapter)
        viewModel.fetchOrdinaryDrinks()
    }
}