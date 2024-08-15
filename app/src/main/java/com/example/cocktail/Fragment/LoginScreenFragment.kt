package com.example.cocktail.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.cocktail.R
import com.example.cocktail.databinding.FragmentLoginScreenBinding

class LoginScreenFragment : Fragment() {

    private var _binding: FragmentLoginScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonCocktails.setOnClickListener {
            findNavController().navigate(R.id.action_loginScreenFragment_to_cocktailListFragment)
        }

        Glide.with(this)
            .load("https://www.thecocktaildb.com/images/media/drink/vrwquq1478252802.jpg")
            .into(binding.imageCocktails)

        binding.imageCocktails.setOnClickListener {
            findNavController().navigate(R.id.action_loginScreenFragment_to_cocktailListFragment)
        }
        binding.buttonAlcoholDrinks.setOnClickListener {
            findNavController().navigate(R.id.action_loginScreenFragment_to_alcoholDrinksFragment)
        }

        Glide.with(this)
            .load("https://www.thecocktaildb.com/images/media/drink/qyyvtu1468878544.jpg")
            .into(binding.imageAlcoholDrinks)

        binding.imageAlcoholDrinks.setOnClickListener {
            findNavController().navigate(R.id.action_loginScreenFragment_to_alcoholDrinksFragment)
        }

        binding.buttonGlass.setOnClickListener {
            findNavController().navigate(R.id.action_loginScreenFragment_to_glassFragment)
        }

        Glide.with(this)
            .load("https://www.thecocktaildb.com/images/media/drink/utypqq1441554367.jpg")
            .into(binding.imageNonAlcohol)

        binding.imageNonAlcohol.setOnClickListener {
            findNavController().navigate(R.id.action_loginScreenFragment_to_glassFragment)
        }
        binding.buttonDrinks.setOnClickListener {
            findNavController().navigate(R.id.action_loginScreenFragment_to_popularDrinksFragment)
        }

        Glide.with(this)
            .load("https://www.thecocktaildb.com/images/media/drink/iwml9t1492976255.jpg")
            .into(binding.imageDrinks)

        binding.imageDrinks.setOnClickListener {
            findNavController().navigate(R.id.action_loginScreenFragment_to_popularDrinksFragment)
        }

        binding.buttonIngredients.setOnClickListener {
            findNavController().navigate(R.id.action_loginScreenFragment_to_ordinaryDrinkFragment)
        }

        Glide.with(this)
            .load("https://www.thecocktaildb.com/images/media/drink/yrtypx1473344625.jpg")
            .into(binding.imageIngredients)

        binding.imageIngredients.setOnClickListener {
            findNavController().navigate(R.id.action_loginScreenFragment_to_ordinaryDrinkFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
