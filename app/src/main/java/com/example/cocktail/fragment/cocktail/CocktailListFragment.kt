package com.example.cocktail.fragment.cocktail

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.cocktail.R
import com.example.cocktail.data.adapter.AdapterFactory
import com.example.cocktail.data.adapter.AdapterType
import com.example.cocktail.data.dataclass.CocktailDrink
import com.example.cocktail.data.adapter.GenericAdapter
import com.example.cocktail.databinding.FragmentCocktailListBinding
import com.example.cocktail.fragment.base.BaseListFragment
import com.example.cocktail.viewModel.CocktailViewModel

class FragmentCocktailList : BaseListFragment<FragmentCocktailListBinding>() {
    override val viewModel: CocktailViewModel by viewModels()
    private lateinit var cocktailAdapter: GenericAdapter

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCocktailListBinding {
        return FragmentCocktailListBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeViewModel()
        setupSearchFunctionality()
    }

    private fun setupRecyclerView() {
        cocktailAdapter = AdapterFactory.createAdapter(AdapterType.COCKTAIL) { item ->
            val cocktail = item as CocktailDrink
            viewModel.selectCocktail(cocktail)
            val action =
                FragmentCocktailListDirections.actionFragmentCocktailListToDetailFragment(cocktail.idDrink)
            findNavController().navigate(action)
        }
        setupRecyclerView(cocktailAdapter, binding.cocktailListRecyclerView)
    }

    private fun observeViewModel() {
        observeData(viewModel.cocktailList, cocktailAdapter)
        viewModel.fetchCocktailsByName("")
    }

    private fun setupSearchFunctionality() {
        binding.editSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val query = s.toString()
                viewModel.fetchCocktailsByName(query)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
        binding.editSearch.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.editSearch.hint = ""
            } else {
                if (binding.editSearch.text.isEmpty()) {
                    binding.editSearch.hint = getString(R.string.hint)
                }
            }
        }
    }
}