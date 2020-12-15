package com.android.example.ensiie_android_projet.tasklist

import java.io.Serializable

data class Task (val id:String,val title:String,val description:String="description"): Serializable
