package com.example.courseproject

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class customViewModel: ViewModel() {
    val authorID : MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
}