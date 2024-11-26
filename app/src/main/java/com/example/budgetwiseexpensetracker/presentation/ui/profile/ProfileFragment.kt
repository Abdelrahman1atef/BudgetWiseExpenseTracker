package com.example.budgetwiseexpensetracker.presentation.ui.profile

import android.Manifest
import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.example.budgetwiseexpensetracker.R
import com.example.budgetwiseexpensetracker.data.local.database.entities.PersonalInfoEntity
import com.example.budgetwiseexpensetracker.databinding.FragmentProfileBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.ByteArrayOutputStream

class ProfileFragment : Fragment(), ProfileImageBottomSheetFragment.OnOptionSelectedListener {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var cameraPermissionLauncher: ActivityResultLauncher<String>
    private lateinit var cameraLauncher: ActivityResultLauncher<Intent>
    private lateinit var galleryLauncher: ActivityResultLauncher<Intent>
    private val viewModel by viewModel<ProfileViewModel>()
    private var selectedImageBitmap: Bitmap? = null
    private var newUsername: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupPermissionLauncher()
        setupCameraLauncher()
        setupGalleryLauncher()
        setupViewModel()
        setupObservers()

        initView()
    }


    private fun setupViewModel() {
        viewModel.getPersonalInfo()
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            viewModel.showPersonalInfo.collect { personalInfo ->
                // Safely handle name
                binding.tvUserName.text = personalInfo.name

                // Safely handle profile image
                personalInfo.imagePath?.let { imageBytes ->
                    Glide.with(requireContext())
                        .load(imageBytes)
                        .apply(RequestOptions.bitmapTransform(CircleCrop()))
                        .into(binding.profileImage)
                } ?: run {
                    // Fallback if imagePath is null
                    binding.profileImage.setImageResource(R.drawable.user) // Replace with your default image
                }
            }
        }

    }

    private fun initView() {
        binding.profileImage.setOnClickListener { openBottomSheet() }
        binding.ivEditUsername.setOnClickListener { showEditUsernameDialog() }
    }

    private fun savePersonalInfo() {
        val imagePath = selectedImageBitmap?.let { bitmapToByteArray(it) }
        viewModel.savePersonalInfo(
            PersonalInfoEntity(
                name = newUsername,
                imagePath = imagePath // Correct parameter name
            )
        )
    }

    private fun setupGalleryLauncher() {
        galleryLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    val selectedImageUri = result.data?.data
                    if (selectedImageUri != null) {
                        val bitmap = MediaStore.Images.Media.getBitmap(
                            requireContext().contentResolver,
                            selectedImageUri
                        )
                        selectedImageBitmap = bitmap
//                        Glide.with(this)
//                            .load(bitmap)
//                            .apply(RequestOptions.bitmapTransform(CircleCrop()))
//                            .into(binding.profileImage)

                        savePersonalInfo()
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Failed to select image",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
    }

    private fun setupCameraLauncher() {
        cameraLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    val capturedBitmap = result.data?.extras?.get("data") as Bitmap?
                    if (capturedBitmap != null) {
                        selectedImageBitmap = capturedBitmap
//                        Glide.with(this)
//                            .load(capturedBitmap)
//                            .apply(RequestOptions.bitmapTransform(CircleCrop()))
//                            .into(binding.profileImage)

                        savePersonalInfo()
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Failed to capture image",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
    }

    private fun showEditUsernameDialog() {
        val editText = EditText(requireContext()).apply {
            hint = "Enter new username"
            setPadding(30, 16, 30, 16)
        }

        AlertDialog.Builder(requireContext())
            .setTitle("Edit Username")
            .setView(editText)
            .setPositiveButton("Save") { dialog, _ ->
                val username = editText.text.toString()
                if (username.isNotBlank()) {
                    newUsername = username
//                    binding.tvUserName.text = username
                    savePersonalInfo()
                } else {
                    Toast.makeText(requireContext(), "Username cannot be empty", Toast.LENGTH_SHORT)
                        .show()
                }
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }
            .show()
    }

    private fun bitmapToByteArray(bitmap: Bitmap): ByteArray {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        return outputStream.toByteArray()
    }


    private fun openBottomSheet() {
        val bottomSheetFragment = ProfileImageBottomSheetFragment()
        bottomSheetFragment.setOnOptionSelectedListener(this)
        bottomSheetFragment.show(childFragmentManager, bottomSheetFragment.tag)
    }

    override fun onCameraSelected() {
        openCamera()
    }

    override fun onGallerySelected() {
        openGallery()
    }

    private fun openCamera() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            cameraLauncher.launch(cameraIntent)
        } else {
            cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    private fun openGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        galleryLauncher.launch(galleryIntent)
    }

    private fun setupPermissionLauncher() {
        cameraPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                if (isGranted) {
                    openCamera()
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Camera permission is required to take pictures",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
}
