package com.dapadz.ltechtest.data.repository

import com.dapadz.ltechtest.data.api.AuthApi
import com.dapadz.ltechtest.data.models.AuthorizationResponseModel
import com.dapadz.ltechtest.data.models.MaskModel
import com.dapadz.ltechtest.utils.ResponseResult

class AuthRepository(private val api: AuthApi) : BaseRepository() {

    suspend fun getMask() : ResponseResult<MaskModel> {
        return fetchData {
            api.getMask()
        }
    }

    suspend fun authorization(phoneNumber: String, password: String) : ResponseResult<AuthorizationResponseModel> {
        return fetchData {
            api.authorization(phoneNumber, password)
        }
    }
}