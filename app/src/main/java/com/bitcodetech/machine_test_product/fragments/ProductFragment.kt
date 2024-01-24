package com.bitcodetech.machine_test_product.fragments

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.bitcodetech.machine_test_product.R
import com.bitcodetech.machine_test_product.VolleyRequestQueue
import com.bitcodetech.machine_test_product.adapter.ProductAdapter
import com.bitcodetech.machine_test_product.databinding.ProductFragmentBinding
import com.bitcodetech.machine_test_product.models.ApiResponse
import com.bitcodetech.machine_test_product.models.Product
import com.google.gson.Gson
import org.json.JSONObject

class ProductFragment : Fragment() {
    private lateinit var binding : ProductFragmentBinding
    private lateinit var productAdapter: ProductAdapter
    private lateinit var recyclerProducts : RecyclerView
    private var products = ArrayList<Product>()
    private lateinit var progressDialog : ProgressDialog


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.product_fragment, null)

        showProgressDialog()

        initViews(view)
        initListeners()

        val productApiRequest = JsonObjectRequest(
            Request.Method.GET,
            "https://dummyjson.com/products",
            null,
            object : Response.Listener<JSONObject> {
                @SuppressLint("NotifyDataSetChanged")
                override fun onResponse(response: JSONObject?) {
                    Log.e("tag", response!!.toString())
                    val apiResponse = Gson().fromJson<ApiResponse>(response.toString(),
                        ApiResponse::class.java)
                    products.addAll(apiResponse!!.products!!)
                    productAdapter.notifyDataSetChanged()
                    progressDialog.dismiss()
                }
            },
            object : Response.ErrorListener {
                override fun onErrorResponse(error: VolleyError?) {
                    Log.e("tag", "${error?.message}")
                }
            }
        )

        VolleyRequestQueue.getRequestQueue(requireContext()).add(productApiRequest)

        return view
    }

    private fun showProgressDialog() {
        progressDialog = ProgressDialog(requireContext())
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        progressDialog.setMessage("Fetching products....")
        progressDialog.show()
    }

    private fun initViews(view: View) {
        recyclerProducts = view.findViewById(R.id.recyclerProducts)
        recyclerProducts.layoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.VERTICAL,
            false)

        productAdapter = ProductAdapter(products)
        recyclerProducts.adapter = productAdapter
        }

    private fun initListeners(){
        productAdapter.onProductImageListener =
            object : ProductAdapter.OnProductImageListener{
                override fun onProductCLick(product: Product, position: Int, productAdapter: ProductAdapter) {
                    Log.e("tag", product.toString())
                    showDetailsFragment(product)
                }
            }
    }
    private fun showDetailsFragment(product: Product){
        val productDetailsFragment = ProductDetailsFragment()

        val bundles = Bundle()
        bundles.putSerializable("products",product)
        productDetailsFragment.arguments = bundles

        parentFragmentManager.beginTransaction()
            .add(R.id.mainContainer,productDetailsFragment,null)
            .addToBackStack(null)
            .commit()
    }
}