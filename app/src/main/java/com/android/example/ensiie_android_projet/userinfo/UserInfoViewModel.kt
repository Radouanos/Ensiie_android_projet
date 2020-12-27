package com.android.example.ensiie_android_projet.userinfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.example.ensiie_android_projet.network.UserInfo
import com.android.example.ensiie_android_projet.network.UserInfoRepository
import kotlinx.coroutines.launch

class UserInfoViewModel : ViewModel()
{
    private val _userInfo = MutableLiveData<UserInfo>()

    public val userInfo : LiveData<UserInfo> = _userInfo

    private val userRepo = UserInfoRepository()

    fun loadInfo()
    {
        viewModelScope.launch {
            val usInfo = userRepo.load()
            _userInfo.value=usInfo!!
        }
    }

    fun updateInfo(user:UserInfo)
    {
        viewModelScope.launch {
            if(userRepo.update(user))
                _userInfo.value=user
        }
    }
}