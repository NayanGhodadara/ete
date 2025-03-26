package com.example.ete.ui.screen

import androidx.lifecycle.viewModelScope
import com.example.ete.data.bean.ApiResponse
import com.example.ete.data.remote.helper.Resource
import com.example.ete.di.event.SingleLiveEvent
import com.example.ete.di.viewmodel.BaseViewModel
import kotlinx.coroutines.launch

class MainActivityVM : BaseViewModel() {

    val obrCountry = SingleLiveEvent<Resource<ApiResponse<ArrayList<Any>>>>()

    fun callCreateProfileApi() = viewModelScope.launch {

        apiRepoImpl?.getCountry()?.collect {
            obrCountry.value = it
        }
    }
}