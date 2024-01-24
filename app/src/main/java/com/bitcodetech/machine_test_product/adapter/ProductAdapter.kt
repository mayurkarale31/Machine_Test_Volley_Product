package com.bitcodetech.machine_test_product.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bitcodetech.machine_test_product.R
import com.bitcodetech.machine_test_product.databinding.ProductViewBinding
import com.bitcodetech.machine_test_product.models.Product
import com.bumptech.glide.Glide

class ProductAdapter(
    private val products: ArrayList<Product>
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    interface OnProductImageListener{
        fun onProductCLick(product: Product, position: Int, productAdapter: ProductAdapter)
    }
    var onProductImageListener : OnProductImageListener? = null

    inner class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding : ProductViewBinding

        init {
            binding = ProductViewBinding.bind(view)

            binding.root.setOnClickListener {
                onProductImageListener?.onProductCLick(
                    products[adapterPosition],
                    adapterPosition,
                    this@ProductAdapter
                )

            }
        }
    }

    override fun getItemCount() = products.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.product_view, null)
        )
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = products[position]

        Glide.with(holder.itemView)
            .load(product.thumbnail)
            .placeholder(R.mipmap.ic_launcher)
            .error(R.mipmap.ic_launcher)
            .circleCrop()
            .into(holder.binding.imgThumbnail)

        holder.binding.txtTitle.text = product.title
        holder.binding.txtDescription.text = product.description
        holder.binding.txtPrice.text = product.price.toString()
        holder.binding.txtBrand.text = product.brand
    }
}