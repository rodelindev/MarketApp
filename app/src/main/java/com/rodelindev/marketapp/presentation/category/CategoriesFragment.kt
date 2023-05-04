package com.rodelindev.marketapp.presentation.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.Navigation
import com.rodelindev.marketapp.R
import com.rodelindev.marketapp.databinding.FragmentCategoriesBinding
import com.rodelindev.marketapp.presentation.common.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class CategoriesFragment : Fragment() {

    private lateinit var binding : FragmentCategoriesBinding

    private val viewModel : CategoriesViewModel by viewModels()

    private lateinit var adapter : CategoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_categories, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCategoriesBinding.bind(view)
        init()
        setupObservers()
        setupAdapter()
    }

    private fun init() {
        viewModel.onUIReady()
    }

    private fun setupAdapter() = with(binding) {
        adapter = CategoryAdapter(){
            val idCategory = it.uuid
            //Origen -> Directions
            //Destino -> Args

            val directions = CategoriesFragmentDirections.actionCategoriesFragmentToProductFragment(idCategory)
            Navigation.findNavController(root).navigate(directions)
        }
        rvCategories.adapter = adapter
    }

    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch{
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.state.collect{ state ->
                    updateUI(state)
                }
            }
        }
    }

    private fun updateUI(state: CategoriesState?) = with(binding) {

        state?.isLoading?.let {
            if(it) progressBar.visibility = View.VISIBLE
            else progressBar.visibility = View.GONE
        }

        state?.error?.let {
            requireContext().toast(it)
        }

        state?.categories?.let { categories ->
            adapter.updateList(categories)
        }

        state?.isEmpty?.let {
            //TODO Buscar imagen para agregar al proyecto
        }
    }
}