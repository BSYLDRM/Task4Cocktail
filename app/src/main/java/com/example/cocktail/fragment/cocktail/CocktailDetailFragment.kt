package com.example.cocktail.fragment.cocktail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.cocktail.data.dataclass.CocktailDrink
import com.example.cocktail.databinding.FragmentCocktailDetailBinding
import com.example.cocktail.viewModel.CocktailViewModel

class CocktailDetailFragment : Fragment() {
    private var _binding: FragmentCocktailDetailBinding? = null
    private val binding get() = _binding!!
    private val cocktailViewModel: CocktailViewModel by viewModels()
    private val args: CocktailDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCocktailDetailBinding.inflate(inflater, container, false)
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
        binding.tvCocktailDetailName.text = cocktail.strDrink
        binding.textViewCocktailDetailIng.text =
            cocktail.getIngredientsWithMeasurements().joinToString(separator = " ")
        Glide.with(this)
            .load(cocktail.strDrinkThumb)
            .into(binding.imageViewCocktailDetail)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}