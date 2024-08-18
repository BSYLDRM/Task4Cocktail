package com.example.cocktail.fragment.alcohol

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.cocktail.R
import com.example.cocktail.data.adapter.AdapterType
import com.example.cocktail.data.adapter.GenericAdapter
import com.example.cocktail.data.dataclass.CocktailDrink
import com.example.cocktail.databinding.FragmentAlcoholDrinksListBinding
import com.example.cocktail.viewModel.CocktailViewModel


class AlcoholDrinksListFragment : Fragment() {
    private var _binding: FragmentAlcoholDrinksListBinding? = null
    private val binding get() = _binding!!
    private val cocktailViewModel: CocktailViewModel by activityViewModels()
    private lateinit var alcoholAdapter: GenericAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAlcoholDrinksListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeViewModel()
        setupHomeIcon()
    }
    private fun setupHomeIcon(){
        val homeIcon :View=binding.root.findViewById(R.id.homeIcon)
        homeIcon.setOnClickListener {
            findNavController().navigate(R.id.categoriesFragment)
        }
    }

    private fun setupRecyclerView() {
        alcoholAdapter = GenericAdapter(emptyList(), AdapterType.ALCOHOLIC) { item ->
            val cocktail = item as CocktailDrink
            val action =
                AlcoholDrinksListFragmentDirections.actionAlcoholDrinksListFragmentToAlcoholDetailDrinksFragment(
                    cocktail.idDrink
                )
            findNavController().navigate(action)
        }
        binding.alcoholDrinksRecyclerView.adapter = alcoholAdapter
    }

    private fun observeViewModel() {
        cocktailViewModel.filteredCocktails.observe(viewLifecycleOwner) { cocktails ->
            alcoholAdapter.updateItems(cocktails)
        }
        cocktailViewModel.fetchAlcoholicCocktails()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}