package com.rodelindev.marketapp.presentation.shoppingcart

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rodelindev.marketapp.R
import com.rodelindev.marketapp.data.database.model.DBProduct
import com.rodelindev.marketapp.databinding.ItemShoppingCartBinding
import com.squareup.picasso.Picasso

class ShoppingCartAdapter : ListAdapter<DBProduct, ShoppingCartAdapter.ViewHolder>(DiffCallback) {

    lateinit var onDeleteClickItem: (DBProduct) -> Unit
    lateinit var onEditClickItem: (DBProduct) -> Unit

    companion object DiffCallback : DiffUtil.ItemCallback<DBProduct>() {
        override fun areItemsTheSame(oldItem: DBProduct, newItem: DBProduct): Boolean {
            return oldItem.proId == newItem.proId
        }

        override fun areContentsTheSame(oldItem: DBProduct, newItem: DBProduct): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemShoppingCartBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val noteElement = getItem(position)
        holder.bind(noteElement)
    }

    inner class ViewHolder(private val binding: ItemShoppingCartBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(dbProduct: DBProduct) = with(binding) {
            tvDescription.text = dbProduct.description
            Picasso.get().load(dbProduct.image).error(R.drawable.icon_empty).into(imgProductCover)

            binding.btnDeleteProduct.setOnClickListener {
                if (::onDeleteClickItem.isInitialized) {
                    onDeleteClickItem(dbProduct)
                }
            }
        }
    }
}