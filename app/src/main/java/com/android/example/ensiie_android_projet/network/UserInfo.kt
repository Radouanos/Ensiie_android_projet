package com.android.example.ensiie_android_projet.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserInfo (
    @SerialName("email")
    val email: String,
    @SerialName("firstname")
    val firstName: String,
    @SerialName("lastname")
    val lastName : String,
    val avatar: String = "https://goo.gl/gEgYUd" // L'url par défaut du Td6
    )

