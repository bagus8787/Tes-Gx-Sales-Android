package com.tes.global

import android.content.Context
import androidx.multidex.MultiDexApplication

class MyApplication : MultiDexApplication() {

    companion object {
        var last_opened_tab = 0

        private var instance: MyApplication? = null

        fun getInstance(): MyApplication? {
            return instance
        }

        fun getContext(): Context? {
            return instance
        }
    }

    override fun onCreate() {
        instance = this

        super.onCreate()
    }
}