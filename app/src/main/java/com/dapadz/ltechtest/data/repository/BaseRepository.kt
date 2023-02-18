package com.dapadz.ltechtest.data.repository

import android.util.Log
import com.dapadz.ltechtest.utils.HttpException
import com.dapadz.ltechtest.utils.ResponseResult
import io.ktor.client.call.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


open class BaseRepository {
    suspend inline fun <reified T> fetchData(
        noinline request : suspend () -> HttpResponse,
    ) : ResponseResult<T> {
        var result : ResponseResult<T>? = null
        flow<T> {
            val response = request.invoke()
            if (! response.status.isSuccess()) {
                throw HttpException(response.status.value, response.status.description)
            }
            emit(response.body())
        }
            .flowOn(Dispatchers.IO)
            .catch { throwable ->
                Log.e("BaseRepository", throwable.message ?: "unknown error")
                result = ResponseResult.Error(throwable)
            }.collect { data ->
                result = ResponseResult.Success(data)
            }
        return result !!
    }
}