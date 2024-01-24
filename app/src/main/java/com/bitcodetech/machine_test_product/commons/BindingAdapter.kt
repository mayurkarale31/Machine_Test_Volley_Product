package com.bitcodetech.machine_test_product.commons

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bitcodetech.machine_test_product.R
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso

@BindingAdapter("image_url")
fun setImageUrlToImageView(imageView: ImageView, imageUrl : String?) {
    //if (!imageUrl.isNullOrEmpty()) {
        Glide.with(imageView)
            .load(imageUrl)
            .error(R.mipmap.ic_launcher)
            .into(imageView)
    //}

    /*Picasso.get().load(imagesArray[0]).into(image1)
    Picasso.get().load(imagesArray[1]).into(image2)*/
}