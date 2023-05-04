package com.rodelindev.marketapp.presentation.product

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rodelindev.marketapp.R
import com.rodelindev.marketapp.databinding.ItemProductBinding
import com.rodelindev.marketapp.domain.model.Category
import com.rodelindev.marketapp.domain.model.Product
import com.squareup.picasso.Picasso

class ProductAdapter constructor(
    private var products: List<Product> = listOf(),
    private val onClickItem: (Product) -> Unit
) : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    inner class ViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val binding: ItemProductBinding = ItemProductBinding.bind(itemView)

        fun bind(product: Product) = with(binding) {
            tvCode.text = product.code
            tvDescription.text = product.description
            tvPrice.text = "$.${product.price}"

            Picasso.get().load(product.images[0]).error(R.drawable.icon_empty).into(imgProduct)

            root.setOnClickListener {
                onClickItem(product)
            }
        }
    }

    fun updateList(products: List<Product>) {
        this.products = products
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = products.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = products[position]
        holder.bind(product)
    }

}
