package com.example.cocktail.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.cocktail.R
import com.example.cocktail.data.adapter.CategoriesAdapter
import com.example.cocktail.data.`object`.CategoriesObject
import com.example.cocktail.databinding.FragmentCategoriesBinding

class CategoriesFragment : Fragment() {

    private var _binding: FragmentCategoriesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val categoriesList = CategoriesObject.getCategoriesList(requireContext())

        val categoriesAdapter = CategoriesAdapter(categoriesList) { category ->
            when (category.buttonString) {
                getString(R.string.main_object_cock) -> findNavController().navigate(R.id.action_categoriesFragment_to_fragmentCocktailList)
                getString(R.string.main_object_alcohol) -> findNavController().navigate(R.id.action_categoriesFragment_to_alcoholDrinksListFragment)
                getString(R.string.main_object_original) -> findNavController().navigate(R.id.action_categoriesFragment_to_ordinaryDrinksListFragment)
                getString(R.string.main_object_popular) -> findNavController().navigate(R.id.action_categoriesFragment_to_popularDrinksListFragment)
                getString(R.string.main_object_glass) -> findNavController().navigate(R.id.action_categoriesFragment_to_glassFragment)
            }
        }
        binding.recyclerCategories.adapter = categoriesAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}