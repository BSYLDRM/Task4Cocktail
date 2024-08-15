package com.example.cocktail.Fragment.OrdinaryDrink

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cocktail.data.AdapterType
import com.example.cocktail.data.GenericAdapter
import com.example.cocktail.data.OrdinaryDrink
import com.example.cocktail.databinding.FragmentOrdinaryDrinksListBinding
import com.example.cocktail.viewModel.CocktailViewModel

class OrdinaryDrinksListFragment : Fragment() {
    private var _binding: FragmentOrdinaryDrinksListBinding? = null
    private val binding get() = _binding!!

    private val cocktailViewModel: CocktailViewModel by activityViewModels()
    private lateinit var ordinaryDrinkAdapter: GenericAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrdinaryDrinksListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        binding.recyclerRowOrdinaryDrinks.layoutManager = LinearLayoutManager(requireContext())

        ordinaryDrinkAdapter = GenericAdapter(
            itemList = mutableListOf(),
            adapterType = AdapterType.ORDINARY_DRINK
        ) { item ->
            if (item is OrdinaryDrink) {
                val action =
                    OrdinaryDrinksListFragmentDirections.actionOrdinaryDrinkToOrdinaryDrinkDetailFragment(
                        item.idDrink
                    )
                findNavController().navigate(action)
            }
        }

        binding.recyclerRowOrdinaryDrinks.adapter = ordinaryDrinkAdapter
    }

    private fun observeViewModel() {
        cocktailViewModel.ordinaryDrinks.observe(viewLifecycleOwner, Observer { ordinaryDrinks ->
            ordinaryDrinkAdapter.updateItems(ordinaryDrinks)
        })

        cocktailViewModel.fetchOrdinaryDrinks()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
