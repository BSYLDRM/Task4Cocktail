package com.example.cocktail.fragment.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.cocktail.R
import com.example.cocktail.data.adapter.GenericAdapter
import com.example.cocktail.viewModel.CocktailViewModel

abstract class BaseListFragment<T : ViewBinding> : Fragment() {

    private var _binding: T? = null
    protected val binding get() = _binding!!

    abstract val viewModel: CocktailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = inflateBinding(inflater, container)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupHomeIcon()
    }

    abstract fun inflateBinding(inflater: LayoutInflater, container: ViewGroup?): T

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

   private  fun setupHomeIcon() {
        val homeIcon: View = binding.root.findViewById(R.id.homeIcon)
        homeIcon.setOnClickListener {
            findNavController().navigate(R.id.categoriesFragment)
        }
    }

    protected open fun <A : RecyclerView.Adapter<*>> setupRecyclerView(
        adapter: A, recyclerView: RecyclerView
    ) {
        recyclerView.adapter = adapter
    }

    protected open fun <T : Any> observeData(liveData: LiveData<List<T>>, adapter: GenericAdapter) {
        liveData.observe(viewLifecycleOwner) { items ->
            @Suppress("UNCHECKED_CAST")
            adapter.updateItems(items as List<Any>)
        }
    }
}