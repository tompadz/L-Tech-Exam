package com.dapadz.ltechtest.data.api

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

class PostApi(private val client: HttpClient) {
    suspend fun getPosts(): HttpResponse {
        return client.get("/api/v1/posts")
    }
}