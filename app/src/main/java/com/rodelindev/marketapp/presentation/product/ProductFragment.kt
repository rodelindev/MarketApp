package com.rodelindev.marketapp.presentation.product

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.rodelindev.marketapp.R
import com.rodelindev.marketapp.databinding.FragmentProductBinding
import com.rodelindev.marketapp.presentation.common.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductFragment : Fragment() {

    private val safeArgs: ProductFragmentArgs by navArgs()
    private val viewModel : ProductViewModel by viewModels()
    private lateinit var binding : FragmentProductBinding

    private lateinit var adapter : ProductAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProductBinding.bind(view)

        init()
        setupAdapter()
        collectData()
    }

    private fun setupAdapter() = with(binding) {
        adapter = ProductAdapter() { product ->
            val categoryId = safeArgs.uuid
            val directions = ProductFragmentDirections.actionProductFragmentToProductDetailFragment(product, categoryId)
            Navigation.findNavController(root).navigate(directions)
        }
        rvProducts.adapter = adapter
    }

    private fun collectData() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect {
                    updateUI(it)
                }
            }
        }
    }

    private fun updateUI(state: ProductState) = with(binding) {

        state?.isLoading?.let { isLoading ->
            if(isLoading) progressBar.visibility = View.VISIBLE
            else progressBar.visibility = View.GONE
        }

        state?.error?.let { error ->
            requireContext().toast(error)
        }

        state?.success?.let { products ->
            adapter.updateList(products)
        }

    }

    private fun init() {
        val categoryId = safeArgs.uuid
        viewModel.populateProducts(categoryId)
    }
}