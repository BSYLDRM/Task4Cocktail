package com.example.cocktail.Fragment.Alcohol

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.cocktail.databinding.FragmentAlcoholDetailBinding
import com.example.cocktail.data.CocktailDrink
import com.example.cocktail.viewModel.CocktailViewModel


class AlcoholDetailDrinksFragment : Fragment() {
    private var _binding: FragmentAlcoholDetailBinding? = null
    private val binding get() = _binding!!

    private val cocktailViewModel: CocktailViewModel by viewModels()
    private val args: AlcoholDetailDrinksFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAlcoholDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val cocktailId = args.cocktail
        setupObservers()
        fetchCocktailDetails(cocktailId)
    }

    private fun fetchCocktailDetails(cocktailId: String) {
        cocktailViewModel.fetchCocktailDetails(cocktailId)
    }

    private fun setupObservers() {
        cocktailViewModel.selectedCocktail.observe(viewLifecycleOwner) { cocktail ->
            updateUI(cocktail)
        }
    }

    private fun updateUI(cocktail: CocktailDrink) {
        binding.textViewAlcoholName.text = cocktail.strDrink
        binding.textViewAlcoholIng.text =
            cocktail.getIngredientsWithMeasurements().joinToString(separator = " ")
        Glide.with(this)
            .load(cocktail.strDrinkThumb)
            .into(binding.imageViewAlcohol)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


