package com.bitcodetech.machine_test_product.models

import java.io.Serializable

data class Product(
    val id : String,
    val title : String,
    val description : String,
    val price : Int,
    val discountPercentage : String,
    val rating : String,
    val stock : String,
    val brand : String,
    val category : String,
    val thumbnail : String,
    //val images : List<String>
) : Serializable
