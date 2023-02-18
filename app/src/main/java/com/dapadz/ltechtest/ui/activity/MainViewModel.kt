package com.dapadz.ltechtest.ui.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dapadz.ltechtest.data.di.KodeinSDK
import com.dapadz.ltechtest.data.di.modules.repository
import com.dapadz.ltechtest.data.di.modules.sharedPrefs
import com.dapadz.ltechtest.utils.ResponseResult
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {

    private val sharedPrefs = KodeinSDK.sharedPrefs.prefs
    private val repository = KodeinSDK.repository.authRepository

    fun checkAuthorization(
        isAuth:() -> Unit,
        isNotAuth:() -> Unit
    ) {
        viewModelScope.launch {
            val authData = sharedPrefs.getUser()
            when (repository.authorization(authData.first, authData.second)) {
                is ResponseResult.Success -> isAuth()
                is ResponseResult.Error -> isNotAuth()
            }
        }
    }
}