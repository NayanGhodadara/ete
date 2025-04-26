package com.example.ete.ui.main.post

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
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
class ExplorePostActivityVM @Inject constructor(private val apiRepoImpl: ApiRepositoryImpl) : ViewModel() {

    val listOfPost = mutableStateListOf<PostBean>()

    private val _obrGetUserPost = mutableStateOf(Resource<ApiResponse<ArrayList<PostBean>>>())
    val obrGetUserPost: State<Resource<ApiResponse<ArrayList<PostBean>>>> get() = _obrGetUserPost

    fun callGetUserPostApi(page: Int, isWishList: Boolean = false, postTypeId: Long = 0L) = viewModelScope.launch {
        val hqMap = HashMap<String, Any>()
        hqMap[PAGE] = page
        hqMap[LIMIT] = 15

        if (postTypeId != 0L) {
            if (postTypeId == BOOKMARK) {
                hqMap[IS_WISH_LIST] = isWishList
            } else {
                hqMap[POST_TYPE_ID] = postTypeId
            }
        }

        apiRepoImpl.getUserPost(hqMap).collect {
            _obrGetUserPost.value = it
        }
    }
}