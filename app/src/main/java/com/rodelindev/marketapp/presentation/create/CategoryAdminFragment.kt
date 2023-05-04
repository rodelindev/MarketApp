package com.rodelindev.marketapp.presentation.create

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.graphics.Bitmap
import android.util.Base64
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import com.rodelindev.marketapp.R
import com.rodelindev.marketapp.databinding.DialogCategoryBinding
import com.rodelindev.marketapp.databinding.FragmentCategoryAdminBinding
import com.rodelindev.marketapp.presentation.common.toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream


class CategoryAdminFragment : Fragment(R.layout.fragment_category_admin) {

    private lateinit var binding: FragmentCategoryAdminBinding
    private var imageBase64 = ""


    /*override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_category_admin, container, false)
    }*/

    private val loadImage = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        binding.imgCategory.setImageURI(uri)

        val inputStream = uri?.let {
            requireContext().contentResolver.openInputStream(it)
        }
        val imageBitmap = BitmapFactory.decodeStream(inputStream)

        lifecycleScope.launch {
            withContext(Dispatchers.Default) {
                converterBase64(imageBitmap)
            }
        }


    }

    private val cameraPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            when {
                isGranted -> startForResult.launch(Intent(MediaStore.ACTION_IMAGE_CAPTURE))
                else -> requireContext().toast("Permiso denegado")
            }
        }

    private val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {

                val data = result.data?.extras?.get("data") as Bitmap
                binding.imgCategory.setImageBitmap(data)

                lifecycleScope.launch {
                    withContext(Dispatchers.Default) {
                        converterBase64(data)
                    }
                }


            } else if (result.resultCode == RESULT_CANCELED) {
                requireContext().toast("Usuario no tomo la foto")
            }
        }

    private fun converterBase64(imageBitmap: Bitmap) {

        val byteArrayOutputStream = ByteArrayOutputStream()
        imageBitmap?.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream)
        val bytes = byteArrayOutputStream.toByteArray()
        imageBase64 = Base64.encodeToString(bytes, Base64.DEFAULT)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCategoryAdminBinding.bind(view)

        events()

    }

    private fun events() = with(binding) {

        btnLoadImage.setOnClickListener {
            loadAlertOptions().show()
        }

        btnSave.setOnClickListener {

            val names = edtName.text.toString()

            if (imageBase64.isBlank()) {
                requireContext().toast("Debes seleccionar una imagen")
                return@setOnClickListener
            }

            //ViewModel // names,imagebase64
        }
    }

    private fun loadAlertOptions(): AlertDialog {

        val alertBinding = DialogCategoryBinding.inflate(layoutInflater)
        val builder = AlertDialog.Builder(requireContext())

        builder.setView(alertBinding.root)

        val alertDialog = builder.create()
        alertDialog.setCancelable(false)

        alertBinding.imgClose.setOnClickListener {
            alertDialog.dismiss()
        }

        alertBinding.btnGalery.setOnClickListener {
            loadImage.launch("image/*")
            alertDialog.dismiss()
        }

        alertBinding.btnCamera.setOnClickListener {
            cameraPermission.launch(Manifest.permission.CAMERA)
            alertDialog.dismiss()
        }

        return alertDialog
    }
}