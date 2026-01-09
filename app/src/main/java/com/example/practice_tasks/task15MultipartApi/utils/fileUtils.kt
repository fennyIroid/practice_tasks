package com.example.practice_tasks.task15MultipartApi.utils

import android.content.Context
import android.net.Uri
import java.io.File
import java.io.FileOutputStream

object fileUtils {

    fun uriToFile(context: Context, uri: Uri): File {
        val inputStream = context.contentResolver.openInputStream(uri)
        val file = File(context.cacheDir, "profile_image.jpg")
        val outputStream = FileOutputStream(file)

        inputStream?.copyTo(outputStream)

        inputStream?.close()
        outputStream.close()

        return file

        }
}