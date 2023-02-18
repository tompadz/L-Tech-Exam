package com.dapadz.ltechtest.data.api

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

class AuthApi(private val client: HttpClient) {

    suspend fun getMask() : HttpResponse {
        return client.get("/api/v1/phone_masks")
    }

    suspend fun authorization(phoneNumber: String, password: String): HttpResponse {
        return client.post("/api/v1/auth") {
            contentType(ContentType.parse("application/x-www-form-urlencoded"))
            parameter("phone", phoneNumber)
            parameter("password", password)
        }
    }
}