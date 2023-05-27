package com.rodelindev.marketapp.presentation.shoppingcart

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
import com.google.android.material.snackbar.Snackbar
import com.rodelindev.marketapp.R
import com.rodelindev.marketapp.databinding.FragmentProductDetailBinding
import com.rodelindev.marketapp.databinding.FragmentShoppingCartBinding
import com.rodelindev.marketapp.presentation.category.CategoriesState
import com.rodelindev.marketapp.presentation.common.toast
import com.rodelindev.marketapp.presentation.productDetail.ProductDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ShoppingCartFragment : Fragment() {


    private var _binding: FragmentShoppingCartBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ShoppingCartViewModel by viewModels()

    private lateinit var adapter: ShoppingCartAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentShoppingCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        setupAdapter()
        events()
    }

    private fun events() = with(binding) {
        btnCheckIn.setOnClickListener {
            if (viewModel.state.value.products!!.isNotEmpty()) {
                Navigation.findNavController(binding.root)
                    .navigate(R.id.action_shoppingCartFragment_to_newShoppingFragment)
            } else {
                Snackbar.make(
                    binding.btnCheckIn,
                    getString(R.string.empty_cart),
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun setupAdapter() = with(binding) {
        adapter = ShoppingCartAdapter()
        rvShoppingProducts.adapter = adapter
        adapter.onDeleteClickItem = {
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.deleteShoppingProduct(it)
            }
        }
    }

    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    updateUI(state)
                }
            }
        }
    }

    private fun updateUI(state: ShoppingState?) = with(binding) {

        state?.products?.let { products ->
            adapter.submitList(products)
            val totalPay = products.sumOf { it.totalPay }.toString()
            binding.tvTotalPay.text = "S/$totalPay"
        }

        state?.error?.let {
            requireContext().toast(it)
        }

        state?.isLoading?.let {
            if (it) progressBar.visibility = View.VISIBLE
            else progressBar.visibility = View.GONE
        }

        state?.isEmpty?.let {
            //TODO Buscar imagen para agregar al proyecto
        }
    }

}