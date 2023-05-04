package com.rodelindev.marketapp.presentation.category

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rodelindev.marketapp.R
import com.rodelindev.marketapp.databinding.ItemCategoryBinding
import com.rodelindev.marketapp.domain.model.Category
import com.squareup.picasso.Picasso

class CategoryAdapter(
    private var categories: List<Category> = listOf(),
    private val onClickItem: (Category) -> Unit
) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {


    /*lateinit var onClickItem: (Category) -> Unit*/

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val binding: ItemCategoryBinding = ItemCategoryBinding.bind(itemView)

        fun bind(category: Category) = with(binding) {
            tvUuid.text = category.uuid
            Picasso.get().load(category.cover).error(R.drawable.logo).into(imgCategory)

            root.setOnClickListener {
                onClickItem(category)
            }
        }
    }

    fun updateList(categories: List<Category>) {
        this.categories = categories
        notifyDataSetChanged()
    }

    //Infla una vista
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
        return ViewHolder(view)
    }

    //Cuantos elementos tiene la lista
    override fun getItemCount(): Int {
        return categories.size
    }

    //Itera tantas veces como elementos tiene la lista
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = categories[position]
        holder.bind(category)
    }
}