package com.dapadz.ltechtest.ui.fragments.authorization

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dapadz.ltechtest.data.di.KodeinSDK
import com.dapadz.ltechtest.data.di.modules.repository
import com.dapadz.ltechtest.data.di.modules.sharedPrefs
import com.dapadz.ltechtest.utils.ResponseResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AuthorizationViewModel : ViewModel() {

    private val prefs = KodeinSDK.sharedPrefs.prefs
    private val repository = KodeinSDK.repository.authRepository

    private val _isAuthorized : MutableLiveData<Boolean> = MutableLiveData()
    val isAuthorized : LiveData<Boolean> get() = _isAuthorized

    private val _error : MutableLiveData<Throwable> = MutableLiveData()
    val error : LiveData<Throwable> get() = _error

    private val _mask : MutableLiveData<String?> = MutableLiveData()
    val mask : LiveData<String?> get() = _mask

    init {
        getMask()
    }

    private fun getMask() {
        viewModelScope.launch {
            when (val result = repository.getMask()) {
                is ResponseResult.Error -> _mask.value = null
                is ResponseResult.Success -> _mask.value = result.value.phoneMask
            }
        }
    }

    fun login(phoneNumber : String, password : String) {
        viewModelScope.launch {
            when (val result = repository.authorization(phoneNumber, password)) {
                is ResponseResult.Success -> {
                    _isAuthorized.value = result.value.success
                    if (result.value.success) {
                        prefs.saveUser(phoneNumber, password)
                    }
                }
                is ResponseResult.Error -> _error.value = result.error
            }
        }
    }
}