package com.example.ete.ui.main

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ete.R
import com.example.ete.data.Constant.ApiObject.IS_WISH_LIST
import com.example.ete.data.Constant.ApiObject.LIBRARY_VIEW_TYPE
import com.example.ete.data.Constant.ApiObject.LIMIT
import com.example.ete.data.Constant.ApiObject.LIMIT_15
import com.example.ete.data.Constant.ApiObject.LIMIT_20
import com.example.ete.data.Constant.ApiObject.PAGE
import com.example.ete.data.Constant.ApiObject.POST_TYPE_ID
import com.example.ete.data.Constant.ApiObject.SEARCH
import com.example.ete.data.Constant.PostType.BOOKMARK
import com.example.ete.data.bean.ApiResponse
import com.example.ete.data.bean.CookieBean
import com.example.ete.data.bean.dropdown.DropDownBean
import com.example.ete.data.bean.library.LibraryBean
import com.example.ete.data.bean.post.PostBean
import com.example.ete.data.bean.user.UserBean
import com.example.ete.data.remote.ApiRepositoryImpl
import com.example.ete.data.remote.helper.Resource
import com.example.ete.di.event.SingleLiveEvent
import com.example.ete.util.cookie.CookieBarType
import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.google.android.libraries.places.api.net.PlacesClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityVM @Inject constructor(private val apiRepoImpl: ApiRepositoryImpl) : ViewModel() {
    val isLoading = mutableStateOf(false)
    val canNavigationBarShow = mutableStateOf(true)
    val showBlur = mutableStateOf(true)
    val showCookies = mutableStateOf(false)
    val cookieBean = mutableStateOf(CookieBean())
    val fieldSearch = mutableStateOf("")

    //Home screen
    val listOfPost = mutableStateListOf<PostBean>()

    //CreatePost
    val typeList = mutableStateListOf<DropDownBean>()
    val listOfLibrary = mutableStateListOf<LibraryBean>()
    val createPostList = mutableStateListOf<String>()
    val selectedType = mutableStateOf<DropDownBean?>(null)
    val selectedLibrary = mutableStateOf<LibraryBean?>(null)
    val selectedLocation = mutableStateOf<String?>(null)
    val descField = mutableStateOf("")

    //Location
    val isGpsDialogShowing = mutableStateOf(false)
    val isPermissionShowing = mutableStateOf(false)
    val locationErrorShow = mutableStateOf(false)
    val locationError = mutableStateOf("")
    val currentLong = mutableDoubleStateOf(0.0)
    val currentLat = mutableDoubleStateOf(0.0)
    val listOfLocation = mutableStateListOf<AutocompletePrediction>()
    val latitude = mutableDoubleStateOf(0.0)
    val longitude = mutableDoubleStateOf(0.0)
    var placesClient: PlacesClient? = null

    private val _obrGetPost = mutableStateOf(Resource<ApiResponse<ArrayList<PostBean>>>())
    val obrGetPost: State<Resource<ApiResponse<ArrayList<PostBean>>>> get() = _obrGetPost

    fun callGetPostApi(page: Int) = viewModelScope.launch {
        val hqMap = HashMap<String, Any>()
        hqMap[PAGE] = page
        hqMap[LIMIT] = 15

        apiRepoImpl.getPost(hqMap).collect {
            _obrGetPost.value = it
        }
    }

    private val _obrGetUserPost = mutableStateOf(Resource<ApiResponse<ArrayList<PostBean>>>())
    val obrGetUserPost: State<Resource<ApiResponse<ArrayList<PostBean>>>> get() = _obrGetUserPost

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
            _obrGetUserPost.value = it
        }
    }

    private val _obrGetUser = SingleLiveEvent<Resource<ApiResponse<UserBean>>>()
    val obrGetUser: LiveData<Resource<ApiResponse<UserBean>>> get() = _obrGetUser
    fun callGetUserApi() = viewModelScope.launch {
        apiRepoImpl.getUserAsync().collect {
            _obrGetUser.postValue(it)
        }
    }

    private val _obrGetLibraryList = mutableStateOf(Resource<ApiResponse<ArrayList<LibraryBean>>>())
    val obrGetLibraryList: State<Resource<ApiResponse<ArrayList<LibraryBean>>>> get() = _obrGetLibraryList
    fun callGetLibraryList(page: Int) = viewModelScope.launch {
        val rqMap = HashMap<String, Any>()
        rqMap[LIMIT] = LIMIT_15
        rqMap[PAGE] = page
        rqMap[LIBRARY_VIEW_TYPE] = "createdAt"
        if (fieldSearch.value.trim().isNotEmpty() == true)
            rqMap[SEARCH] = fieldSearch.value.trim()

        apiRepoImpl.getLibraryList(rqMap).collect {
            _obrGetLibraryList.value = it
        }
    }

    fun isCreatePostValid(context: Context): Boolean {
        if (createPostList.isEmpty()) {
            showCookies.value = true
            cookieBean.value.message = context.getString(R.string.please_select_image)
            return false
        } else if (selectedType.value == null) {
            showCookies.value = true
            cookieBean.value.message = context.getString(R.string.please_select_post_type)
            return false
        } else {
            showCookies.value = false
            return true
        }
        return true
    }
}