package com.example.practice_tasks.task15MultipartApi.ui

import android.net.Uri
import android.os.Bundle
import android.util.Log.e
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.practice_tasks.R
import com.example.practice_tasks.task15MultipartApi.network.RetrofitClient
import com.example.practice_tasks.task15MultipartApi.utils.fileUtils
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Retrofit
import retrofit2.http.Multipart

class ProfileActivity : AppCompatActivity() {

    private lateinit var imgProfile: ImageView
    private lateinit var btnPickImage: Button
    private lateinit var edtName: EditText
    private lateinit var edtBio: EditText
    private lateinit var btnUpdateProfile: Button

    private var imageUri: Uri? = null

    private lateinit var pickImageLauncher: ActivityResultLauncher<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profile)

        imgProfile = findViewById(R.id.imgViewProfile)
        btnPickImage = findViewById(R.id.btnImgPick)
        edtBio = findViewById(R.id.edtBio)
        edtName = findViewById(R.id.edtName)
        btnUpdateProfile = findViewById(R.id.btnUpdateProfile)

        pickImageLauncher =
            registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
                if (uri != null) {
                    imageUri = uri
                    imgProfile.setImageURI(uri)
                }
            }

        btnPickImage.setOnClickListener {
            pickImageLauncher.launch("image/*")
        }

        btnUpdateProfile.setOnClickListener {
            updateProfile()
        }
    }

    private fun updateProfile() {
        if (imageUri == null) {
            Toast.makeText(this, "Please pick an image", Toast.LENGTH_SHORT).show()
            return
        }

        if (edtName.text.isNullOrEmpty() || edtBio.text.isNullOrEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val file = fileUtils.uriToFile(this,imageUri!!)

        val imageRequestBody = file.asRequestBody("image/*".toMediaType())

        val imagePart = MultipartBody.Part.createFormData(
            "image",
            file.name,
            imageRequestBody
        )

        val nameBody = edtName.text.toString()
            .toRequestBody("text/plain".toMediaType())

        val bioBody = edtBio.text.toString()
            .toRequestBody("text/plain".toMediaType())

        lifecycleScope.launch {
            try {
                val response = RetrofitClient.api.updateProfile(
                    imagePart,
                    nameBody,
                    bioBody
                )

                if (response.isSuccessful) {
                    Toast.makeText(this@ProfileActivity, "Profile updated successfully", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this@ProfileActivity, "Failed to update profile", Toast.LENGTH_SHORT).show()
                }
            } catch(e: Exception) {
                Toast.makeText(this@ProfileActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}