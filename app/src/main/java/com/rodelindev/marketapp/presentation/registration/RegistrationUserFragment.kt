package com.rodelindev.marketapp.presentation.registration

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import com.rodelindev.marketapp.R
import com.rodelindev.marketapp.databinding.FragmentRegistrationUserBinding
import com.rodelindev.marketapp.domain.model.Gender
import com.rodelindev.marketapp.presentation.common.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegistrationUserFragment : Fragment() {

    private lateinit var binding: FragmentRegistrationUserBinding

    private val viewModel: RegistrationUserViewModel by viewModels()

    private var genders: List<Gender> = listOf()
    private var genderCode = "M"


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_registration_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegistrationUserBinding.bind(view)

        events()
        setupObservers()

    }

    private fun setupObservers() = with(binding) {
        viewModel.state.observe(viewLifecycleOwner) { state ->
            updateUI(state)
        }
    }

    private fun updateUI(state: RegistrationUserState?)  {

        state?.isLoading?.let { isLoading ->
            if (isLoading) binding.progressBar.visibility = View.VISIBLE
            else binding.progressBar.visibility = View.GONE
        }

        state?.error?.let { error ->
            requireContext().toast(error)
        }

        state?.genders?.let { genders ->
            this.genders = genders
            populateGenders(genders)
        }

        state?.createAccount?.let { user ->
            println(user.names)
            requireContext().toast(user.names)
        }

    }

    private fun populateGenders(genders: List<Gender>) = with(binding) {

        spGender.setAdapter(
            ArrayAdapter(
                requireContext(),
                R.layout.item_gender,
                genders
            )
        )
    }

    private fun events() = with(binding) {

        btnCreateAccount.setOnClickListener {

            val names = edtNames.text.toString()
            val lastName = edtNames.text.toString()
            val email = edtNames.text.toString()
            val phone = edtNames.text.toString()
            val document = edtNames.text.toString()
            val password = edtNames.text.toString()

            viewModel.createAccount(names, lastName, email, phone, document, password, genderCode)
        }

        spGender.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                genderCode = genders[position].gender
                println(genderCode)
            }

        //LISTAS
        //MUTABLES
        /*val numbers= mutableListOf(2,8,10,12,18,20)

        numbers.forEach {

        }

        val numbersFilter = numbers.filter {
            it > 10
        }




        //IMMUTABLES
        val days = listOf("Lunes","Martes","Miercoles")
        days[0] //Lunes
        days[2] //Miercoles
        days.first()
        days.last()

        days.forEach {

        }*/

    }


}