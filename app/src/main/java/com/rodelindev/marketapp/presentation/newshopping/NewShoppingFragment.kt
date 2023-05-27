package com.rodelindev.marketapp.presentation.newshopping

import android.app.AlertDialog
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.rodelindev.marketapp.data.remote.model.ShoppingDto
import com.rodelindev.marketapp.databinding.DialogCategoryBinding
import com.rodelindev.marketapp.databinding.DialogNewShippingBinding
import com.rodelindev.marketapp.databinding.FragmentNewShoppingBinding
import com.rodelindev.marketapp.presentation.common.toast
import com.rodelindev.marketapp.presentation.shoppingcart.ShoppingCartViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@AndroidEntryPoint
class NewShoppingFragment : Fragment() {

    private var _binding: FragmentNewShoppingBinding? = null
    private val binding get() = _binding!!

    private val viewModel: NewShoppingViewModel by viewModels<NewShoppingViewModel>()

    private var addressType: Int = 1
    private var typeMethod: Int = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentNewShoppingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        events()
        setupObservers()
        totalPay()
    }

    private fun totalPay() = with(binding) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.payState.collect { total ->
                    binding.tvTotalPay.text = "S/$total"

                    edtDate.setText(LocalDateTime.now().format(
                        DateTimeFormatter.ofPattern("yyyy-MM-dd")
                    ))
                    edtHour.setText(LocalDateTime.now().format(
                        DateTimeFormatter.ofPattern("HH:mm")
                    ))
                }
            }
        }
    }

    private fun setupObservers() {
        viewModel.state.observe(viewLifecycleOwner) { state ->
            updateUI(state)
        }
    }

    private fun updateUI(state: NewShoppingState?) = with(binding) {
        state?.createShopping?.let { shopping ->
            showAlertDialog(shopping).show()
        }

        state?.error?.let { error ->
            requireContext().toast(error)
        }

        state?.isLoading?.let { isLoading ->
            if (isLoading) binding.progressBar.visibility = View.VISIBLE
            else binding.progressBar.visibility = View.GONE
        }
    }

    private fun showAlertDialog(shopping: ShoppingDto?): AlertDialog {

        val alertBinding = DialogNewShippingBinding.inflate(layoutInflater)
        val builder = AlertDialog.Builder(requireContext())

        builder.setView(alertBinding.root)

        val alertDialog = builder.create()
        alertDialog.setCancelable(false)

        alertBinding.tvPurchaseNumber.text = "Orden de compra: ${shopping?.data}"

        alertBinding.btnAccept.setOnClickListener {
            alertDialog.dismiss()
        }

        return alertDialog
    }

    private fun events() = with(binding) {

        addressRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            addressType = when (checkedId) {
                rbHouse.id -> 1
                rbOffice.id -> 2
                rbOther.id -> 3
                else -> 1
            }
        }

        rbPayType.setOnCheckedChangeListener { _, checkedId ->
            typeMethod = when (checkedId) {
                rbTransfer.id -> 1
                rbCard.id -> 2
                rbCash.id -> 3
                else -> 1
            }
        }


        btnProcessPayment.setOnClickListener {

            val address = edtAddress.text.toString()
            val reference = edtReference.text.toString()
            val district = edtDistrict.text.toString()
            val amount = edtPayAmount.text.toString()

            viewModel.newShopping(
                type = addressType,
                address = address,
                reference = reference,
                district = district,
                typeMethod = typeMethod,
                amount = amount,
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}