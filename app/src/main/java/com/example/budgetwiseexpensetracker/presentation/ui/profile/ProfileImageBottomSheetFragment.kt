package com.example.budgetwiseexpensetracker.presentation.ui.profile

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.budgetwiseexpensetracker.R
import com.example.budgetwiseexpensetracker.databinding.BottomSheetLayoutBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class ProfileImageBottomSheetFragment : BottomSheetDialogFragment() {

    private var onOptionSelectedListener: OnOptionSelectedListener? = null
    private lateinit var binding: BottomSheetLayoutBinding

    interface OnOptionSelectedListener {
        fun onCameraSelected()
        fun onGallerySelected()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomSheetLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try {
            val pkg = "com.app.my" //your package name
            val icon = requireContext().packageManager.getApplicationIcon(pkg)

            binding.ivCamera.setImageDrawable(icon)

        } catch (_: PackageManager.NameNotFoundException) {
        }


        binding.ivCamera.setOnClickListener {
            onOptionSelectedListener?.onCameraSelected()
            dismiss()
        }

        binding.ivMyFile.setOnClickListener {
            onOptionSelectedListener?.onGallerySelected()
            dismiss()
        }
    }

    fun setOnOptionSelectedListener(listener: ProfileFragment) {
        onOptionSelectedListener = listener
    }
}
