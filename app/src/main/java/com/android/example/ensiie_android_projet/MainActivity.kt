package com.android.example.ensiie_android_projet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.android.example.ensiie_android_projet.userinfo.UserInfoActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val avatar = findViewById<ImageView>(R.id.avatar)
        avatar.setOnClickListener{
            val intent = Intent(this, UserInfoActivity::class.java)
            startActivity(intent)
        }
    }
}