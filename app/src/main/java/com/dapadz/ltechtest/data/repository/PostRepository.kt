package com.dapadz.ltechtest.data.repository

import com.dapadz.ltechtest.data.models.PostModel
import com.dapadz.ltechtest.data.api.PostApi
import com.dapadz.ltechtest.utils.ResponseResult

class PostRepository(private val api: PostApi) : BaseRepository() {
    suspend fun getPosts() : ResponseResult<List<PostModel>> {
        return fetchData {
            api.getPosts()
        }
    }
}