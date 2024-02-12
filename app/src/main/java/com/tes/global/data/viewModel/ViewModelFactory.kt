package com.tes.global.data.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tes.global.data.api.ApiInterface
import com.tes.global.data.api.ApiRepository

class ViewModelFactory (private val apiInterface: ApiInterface) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BaseViewModel::class.java)) {
            return BaseViewModel(ApiRepository(apiInterface)) as T
        }

        throw IllegalArgumentException("Unknown class name")
    }
}