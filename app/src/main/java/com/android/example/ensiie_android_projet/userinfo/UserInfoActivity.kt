package com.android.example.ensiie_android_projet.userinfo

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import androidx.lifecycle.lifecycleScope
import coil.load
import com.android.example.ensiie_android_projet.BuildConfig
import com.android.example.ensiie_android_projet.R
import com.android.example.ensiie_android_projet.network.Api
import com.android.example.ensiie_android_projet.network.TasksWebService
import com.android.example.ensiie_android_projet.network.UserService
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class UserInfoActivity : AppCompatActivity() {

    lateinit var avatar:ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_info)
        avatar = findViewById(R.id.avatar2)
        val takePic = findViewById<Button>(R.id.take_picture_button)
        val uploadIm = findViewById<Button>(R.id.upload_image_button)

        takePic.setOnClickListener {
            askCameraPermissionAndOpenCamera()
        }

        uploadIm.setOnClickListener{
            getFromGallery()
        }

    }

    private fun askCameraPermissionAndOpenCamera() {
        when {
            ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED -> openCamera()
            shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) -> showExplanationDialog()
            else -> requestCameraPermission()
        }
    }

    private fun showExplanationDialog() {
        AlertDialog.Builder(this).apply {
            setMessage("On a besoin de la caméra sivouplé ! 🥺")
            setPositiveButton("Bon, ok") { _, _ ->
                requestCameraPermission()
            }
            setCancelable(true)
            show()
        }
    }

    private fun requestCameraPermission() {
        requestPermissionLauncher.launch(Manifest.permission.CAMERA)
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) openCamera()
            else showExplanationDialog()
        }

    private fun handleImage(toUri: Uri)
    {
        val converted = convert(toUri)
        lifecycleScope.launch {
            Api.userService.updateAvatar(converted)
        }
    }

    //convertir l'image afin de pouvoir l'envoyer en HTTP
    private fun convert(uri: Uri) =
        MultipartBody.Part.createFormData(
            name = "avatar",
            filename = "temp.jpeg",
            body = contentResolver.openInputStream(uri)!!.readBytes().toRequestBody()
        )

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch {
            val userInfo = Api.userService.getInfo()
            avatar.load(userInfo.body()?.avatar)
        }
    }
    // create a temp file and get a uri for it
    private val photoUri by lazy {
        FileProvider.getUriForFile(
                this,
                BuildConfig.APPLICATION_ID +".fileprovider",
                File.createTempFile("avatar", ".jpeg", externalCacheDir)

        )
    }

    // register
    private val takePicture = registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        if (success) handleImage(photoUri)
        else
            Toast.makeText(this, "Erreur ! 😢", Toast.LENGTH_LONG).show()
    }

    private val pickInGallery = registerForActivityResult(ActivityResultContracts.GetContent()){ uri ->
        handleImage(uri)
    }

    private fun getFromGallery() = pickInGallery.launch("image/*")
    // use
    private fun openCamera() = takePicture.launch(photoUri)
}