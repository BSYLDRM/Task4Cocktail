package com.example.cocktail.fragment.ordinaryDrink

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.cocktail.R
import com.example.cocktail.data.dataclass.CocktailDrink
import com.example.cocktail.databinding.FragmentOrdinaryDrinksDetailBinding
import com.example.cocktail.viewModel.CocktailViewModel

class OrdinaryDrinksDetailFragment : Fragment() {
    private var _binding: FragmentOrdinaryDrinksDetailBinding? = null
    private val binding get() = _binding!!

    private val cocktailViewModel: CocktailViewModel by viewModels()
    private val args: OrdinaryDrinksDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrdinaryDrinksDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val cocktailId = args.cocktail
        setupObservers()
        fetchCocktailDetails(cocktailId)
        fetchHomeIcon()
    }

    private fun fetchHomeIcon() {
        val homeIcon: View = binding.root.findViewById(R.id.homeIcon)
        homeIcon.setOnClickListener {
            findNavController().navigate(R.id.categoriesFragment)
        }
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
        binding.textViewOrdinaryName.text = cocktail.strDrink
        binding.textViewOrdinaryIng.text =
            cocktail.getIngredientsWithMeasurements().joinToString(separator = " ")
        Glide.with(this)
            .load(cocktail.strDrinkThumb)
            .into(binding.imageViewOrdinary)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}