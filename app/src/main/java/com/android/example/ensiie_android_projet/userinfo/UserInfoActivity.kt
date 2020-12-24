package com.android.example.ensiie_android_projet.userinfo

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.android.example.ensiie_android_projet.R

class UserInfoActivity : AppCompatActivity() {
    lateinit var imView:ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_info)
        imView = findViewById(R.id.image_view)
    }

    override fun onResume() {
        super.onResume()
        imView.load("https://goo.gl/gEgYUd")
    }
}