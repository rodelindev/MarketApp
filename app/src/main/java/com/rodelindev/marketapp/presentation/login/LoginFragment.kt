package com.rodelindev.marketapp.presentation.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.rodelindev.marketapp.R
import com.rodelindev.marketapp.databinding.FragmentLoginBinding
import com.rodelindev.marketapp.presentation.SingleDrawerActivity
import com.rodelindev.marketapp.presentation.common.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var binding: FragmentLoginBinding

    /*override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }*/

    private val viewModel: LoginViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)

        events()
        setupObservers()
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            viewModel.state.observe(viewLifecycleOwner) { state ->
                updateUI(state)
            }
        }
    }

    private fun updateUI(state: LoginState?) {
        state?.error?.let { error ->
            println(error)
        }

        state?.user?.let { user ->
            requireContext().toast("Bienvenido ${user.names}")
            val intent = Intent(requireContext(), SingleDrawerActivity::class.java)
            startActivity(intent)
            /*activity?.finish()*/
        }

        state?.loader?.let { loader ->
            if(loader) binding.progressBar.visibility = View.VISIBLE
            else binding.progressBar.visibility = View.GONE
        }

        state?.emailError?.let {
            binding.tilEmail.error = "El correo no debe estare vacio"
        }

        state?.emailError?.let {
            binding.tilPassword.error = "La contraseña no debe estare vacio"
        }

    }

    private fun events() = with(binding) {
        btnSignIn.setOnClickListener {
            val email = edtEmail.text.toString()
            val password = edtPassword.text.toString()
            viewModel.signIn(email, password)
        }

        tvCreateAccount.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_loginFragment_to_registrationUserFragment)
        }
    }
}