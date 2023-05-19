package com.rodelindev.marketapp.presentation.productDetail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.rodelindev.marketapp.R
import com.rodelindev.marketapp.data.database.model.DBProduct
import com.rodelindev.marketapp.databinding.FragmentProductDetailBinding
import com.rodelindev.marketapp.domain.model.Product
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.math.abs

@AndroidEntryPoint
class ProductDetailFragment : Fragment() {

    private var _binding: FragmentProductDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProductDetailViewModel by viewModels()
    private val safeArgs: ProductDetailFragmentArgs by navArgs()

    private var productQuantity: Int = 0

    private val categoryId: String
        get() = safeArgs.categoryId

    private val productData: Product
        get() = safeArgs.product


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProductDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setProductArguments()
        onEvent()

        /*Log.d("product", productData.toString())*/
    }

    private fun onEvent() = with(binding) {

        btnMinusProduct.setOnClickListener {
            productQuantity -= 1
            tvSizeProduct.text = productQuantity.toString()
        }

        btnPlusProduct.setOnClickListener {
            productQuantity += 1
            tvSizeProduct.text = productQuantity.toString()
            /*if (productQuantity == abs(productData.stock)) {
                btnPlusProduct.isEnabled = false
            } else if(productQuantity < abs(productData.stock)) {
                btnPlusProduct.isEnabled = true
            }*/
            /*btnPlusProduct.isEnabled = productQuantity != abs(productData.stock)*/
        }

        btnAddCart.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {

                if (productQuantity != 0) {
                    viewModel.saveLocalProduct(
                        DBProduct(
                            proId = 0,
                            productId = productData.uuid,
                            categoryId = categoryId,
                            image = productData.images[0],
                            description = productData.description,
                            quantity = productQuantity,
                            totalPay = (productData.price * productQuantity)
                        )
                    )
                    Snackbar.make(binding.btnAddCart, R.string.message_add_cart_ok, Snackbar.LENGTH_LONG).show()
                } else {
                    Snackbar.make(binding.btnAddCart, R.string.message_add_cart, Snackbar.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun setProductArguments() {
        /*binding.tvProductTitle.text = productData.description.toString()*/
        Picasso.get().load(productData.images[0]).error(R.drawable.icon_empty)
            .into(binding.imgProduct)
        binding.product = productData
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}