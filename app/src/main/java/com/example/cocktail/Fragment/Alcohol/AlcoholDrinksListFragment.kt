package com.example.cocktail.Fragment.Alcohol

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
    ): View? {
        _binding = FragmentAlcoholDrinksListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeViewModel()
        setupSearchFunctionality()
    }

    private fun setupRecyclerView() {
        binding.alcoholDrinksRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        alcoholAdapter = GenericAdapter(emptyList(), AdapterType.COCKTAIL) { item ->
            val cocktail = item as CocktailDrink
            val action =
                AlcoholDrinksListFragmentDirections.actionAlcoholDrinksFragmentToAlcoholDetailDrinksFragment(
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
        // Alkollü içecekleri yükle
        cocktailViewModel.fetchAlcoholicCocktails()

    }

    private fun setupSearchFunctionality() {
        binding.editAlcoholDrinksSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val query = s.toString()
                cocktailViewModel.fetchCocktailsByName(query)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
