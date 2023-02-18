package com.dapadz.ltechtest.ui.fragments.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.dapadz.ltechtest.data.models.PostModel
import com.dapadz.ltechtest.data.di.KodeinSDK
import com.dapadz.ltechtest.data.di.modules.repository
import com.dapadz.ltechtest.utils.ResponseResult
import com.dapadz.ltechtest.utils.SortType
import kotlinx.coroutines.launch
import java.util.*

class HomeViewModel(
   application: Application,
   private val state : SavedStateHandle,
) : AndroidViewModel(application) {

    private val repository = KodeinSDK.repository.postRepository
    private val statePosts = "state_posts"
    private val stateSort = "state_sort"

    private var timer: Timer? = null

    private val _posts: MutableLiveData<List<PostModel>?> = state[statePosts]?:MutableLiveData()
    val posts : LiveData<List<PostModel>?> get() = _posts

    private val _error: MutableLiveData<Throwable> = MutableLiveData()
    val error : LiveData<Throwable> get() = _error

    var sortType = state.get<SortType>(stateSort) ?: SortType.DEFAULT
        set(value) {
            field = value
            state[stateSort] = value
            onRefresh()
        }

    init {
        startLongPooling()
    }

    fun onRefresh() {
        startLongPooling()
    }

    private fun getPosts() {
        viewModelScope.launch {
            when(val result = repository.getPosts()) {
                is ResponseResult.Error -> _error.value = result.error
                is ResponseResult.Success -> {
                    _posts.value = when (sortType) {
                        SortType.DATE -> result.value.sortedBy { it.getDateLong() }
                        SortType.DEFAULT -> result.value
                    }
                }
            }
        }
    }

    private fun  stopLongPooling() {
        Log.i("longPooling", "stop Long Pooling")
        timer?.cancel()
        timer = null
    }

    private fun startLongPooling() {
        Log.i("longPooling", "start Long Pooling")
        stopLongPooling()
        timer = Timer()
        timer?.schedule(object : TimerTask() {
            override fun run() {
                Log.i("longPooling", "start new request")
                getPosts()
            }
        }, 0, 5000)
    }

    override fun onCleared() {
        super.onCleared()
        timer?.cancel()
        Log.i("longPooling", "timer cancel")
    }
}