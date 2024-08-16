package com.example.cocktail.fragment.cocktail

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.cocktail.data.adapter.AdapterType
import com.example.cocktail.data.dataclass.CocktailDrink
import com.example.cocktail.data.adapter.GenericAdapter
import com.example.cocktail.databinding.FragmentCocktailListBinding
import com.example.cocktail.viewModel.CocktailViewModel

class FragmentCocktailList : Fragment() {
    private var _binding: FragmentCocktailListBinding? = null
    private val binding get() = _binding!!

    private val cocktailViewModel: CocktailViewModel by viewModels()
    private lateinit var cocktailAdapter: GenericAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCocktailListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeViewModel()
        setupSearchFunctionality()
        setupScrollListener()
    }

    private fun setupRecyclerView() {
        cocktailAdapter = GenericAdapter(emptyList(), AdapterType.COCKTAIL) {}
        binding.cocktailListRecyclerView.adapter = cocktailAdapter
    }

    private fun observeViewModel() {
        cocktailViewModel.cocktailList.observe(viewLifecycleOwner) { cocktails ->
            cocktailAdapter = GenericAdapter(cocktails, AdapterType.COCKTAIL) { item ->
                val cocktail = item as CocktailDrink
                cocktailViewModel.selectCocktail(cocktail)
                val action =
                    FragmentCocktailListDirections.actionFragmentCocktailListToCocktailDetailFragment(
                        cocktail.idDrink
                    )
                findNavController().navigate(action)
            }
            binding.cocktailListRecyclerView.adapter = cocktailAdapter
        }
        cocktailViewModel.fetchCocktailsByName("")
    }

    private fun setupSearchFunctionality() {
        binding.editSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val query = s.toString()
                cocktailViewModel.fetchCocktailsByName(query)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun setupScrollListener() {
        binding.nestedScrollView.setOnScrollChangeListener { _, _, scrollY, _, _ ->
            if (scrollY > 0) {
                binding.editSearch.visibility = View.GONE
            } else {
                binding.editSearch.visibility = View.VISIBLE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}