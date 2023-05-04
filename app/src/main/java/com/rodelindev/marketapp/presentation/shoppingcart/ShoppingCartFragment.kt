package com.rodelindev.marketapp.presentation.shoppingcart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rodelindev.marketapp.R
import com.rodelindev.marketapp.databinding.FragmentShoppingCartBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ShoppingCartFragment : Fragment() {

    private lateinit var binding: FragmentShoppingCartBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shopping_cart, container, false)
    }

}