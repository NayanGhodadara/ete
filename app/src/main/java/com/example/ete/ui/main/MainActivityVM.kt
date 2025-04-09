package com.example.ete.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ete.data.Constant.ApiObject.IS_WISH_LIST
import com.example.ete.data.Constant.ApiObject.LIMIT
import com.example.ete.data.Constant.ApiObject.LIMIT_20
import com.example.ete.data.Constant.ApiObject.PAGE
import com.example.ete.data.Constant.ApiObject.POST_TYPE_ID
import com.example.ete.data.Constant.PostType.BOOKMARK
import com.example.ete.data.bean.ApiResponse
import com.example.ete.data.bean.post.PostBean
import com.example.ete.data.bean.user.UserBean
import com.example.ete.data.remote.ApiRepositoryImpl
import com.example.ete.data.remote.helper.Resource
import com.example.ete.di.event.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityVM @Inject constructor(private val apiRepoImpl: ApiRepositoryImpl) : ViewModel() {

    /** Social login **/
    private val _obrGetUserPost = MutableLiveData<Resource<ApiResponse<ArrayList<PostBean>>>>()
    val obrGetUserPost: LiveData<Resource<ApiResponse<ArrayList<PostBean>>>> get() = _obrGetUserPost

    fun callGetUserPostApi(page: Int, isWishList: Boolean = false, postTypeId: Long = 0L) = viewModelScope.launch {
        val hqMap = HashMap<String, Any>()
        hqMap[PAGE] = page
        hqMap[LIMIT] = LIMIT_20

        if (postTypeId != 0L) {
            if (postTypeId == BOOKMARK) {
                hqMap[IS_WISH_LIST] = isWishList
            } else {
                hqMap[POST_TYPE_ID] = postTypeId
            }
        }

        apiRepoImpl.getUserPost(hqMap).collect {
            _obrGetUserPost.postValue(it)
        }
    }

    private val _obrGetUser = SingleLiveEvent<Resource<ApiResponse<UserBean>>>()
    val obrGetUser: LiveData<Resource<ApiResponse<UserBean>>> get() = _obrGetUser
    fun callGetUserApi() = viewModelScope.launch {
        apiRepoImpl.getUserAsync().collect {
            _obrGetUser.postValue(it)
        }
    }
}