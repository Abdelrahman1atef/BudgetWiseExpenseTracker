package com.example.budgetwiseexpensetracker.presentation.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.example.budgetwiseexpensetracker.R
import com.example.budgetwiseexpensetracker.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()

    }

    private fun initView() {
        cycleImageViewStyle()
    }

    private fun cycleImageViewStyle() {
        val imageView = binding.profileImage// Replace with your ImageView ID
        Glide.with(this)
            .load(R.drawable.pro_image) // Replace with your image source
            .apply(RequestOptions.bitmapTransform(CircleCrop()))
            .into(imageView)
    }

}