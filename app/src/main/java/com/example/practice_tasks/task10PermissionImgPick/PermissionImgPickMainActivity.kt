package com.example.practice_tasks.task10PermissionImgPick

import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.practice_tasks.R

class PermissionImgPickMainActivity : AppCompatActivity() {

    private lateinit var ImageView: ImageView

    private val imagePickerLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri.let {
                loadImage(it)
            }
        }

    private val permissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                openImagePicker()
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_permission_img_pick_main)
        ImageView = findViewById<ImageView>(R.id.selectedImage)

        findViewById<Button>(R.id.btnPickImg).setOnClickListener {
            checkPermissionandPickImage()
        }
    }

    private fun checkPermissionandPickImage() {

        val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            android.Manifest.permission.READ_MEDIA_IMAGES
        } else {
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        }

        if (ContextCompat.checkSelfPermission(this, permission) ==
            PackageManager.PERMISSION_GRANTED
        ) {
            openImagePicker()
        } else {
            permissionLauncher.launch(permission)
        }

    }
    private fun openImagePicker() {
        imagePickerLauncher.launch("image/*")
    }

    private fun loadImage(uri: Uri?) {
        Glide.with(this)
            .load(uri)
            .placeholder(android.R.drawable.progress_indeterminate_horizontal)
            .error(android.R.drawable.stat_notify_error)
            .into(ImageView)
    }
}