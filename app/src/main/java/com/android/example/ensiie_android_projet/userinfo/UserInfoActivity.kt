package com.android.example.ensiie_android_projet.userinfo

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.android.example.ensiie_android_projet.R

class UserInfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_info)
        val avatar = findViewById<ImageView>(R.id.avatar2)
        avatar.load("https://goo.gl/gEgYUd")
    }

}