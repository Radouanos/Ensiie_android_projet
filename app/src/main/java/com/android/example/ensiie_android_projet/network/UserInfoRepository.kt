package com.android.example.ensiie_android_projet.network

class UserInfoRepository  {

    private val userService = Api.userService

    suspend fun load() : UserInfo?
    {
        val response = userService.getInfo()
        return if(response.isSuccessful) response.body() else null

    }

    suspend fun update(info:UserInfo) : Boolean
    {
        val rep = userService.update(info)
        return (rep.isSuccessful)
    }
}