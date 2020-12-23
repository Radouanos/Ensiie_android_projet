package com.android.example.ensiie_android_projet.userinfo

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.android.example.ensiie_android_projet.R

class UserInfoActivity : AppCompatActivity() {
    lateinit var imageView:ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        imageView = findViewById(R.id.image_view)
        setContentView(R.layout.activity_user_info)
    }

    override fun onResume() {
        super.onResume()
        imageView.load("https://goo.gl/gEgYUd")
    }
}