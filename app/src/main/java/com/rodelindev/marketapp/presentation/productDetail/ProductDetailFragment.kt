package com.rodelindev.marketapp.presentation.productDetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.rodelindev.marketapp.R
import com.rodelindev.marketapp.databinding.FragmentProductDetailBinding
import com.rodelindev.marketapp.databinding.FragmentProductDetailBindingImpl
import com.rodelindev.marketapp.presentation.product.ProductFragmentArgs
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDetailFragment : Fragment() {

    private lateinit var binding: FragmentProductDetailBinding
    private val safeArgs: ProductDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_product_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val productDetail = safeArgs.product
        binding.product = productDetail
    }


}