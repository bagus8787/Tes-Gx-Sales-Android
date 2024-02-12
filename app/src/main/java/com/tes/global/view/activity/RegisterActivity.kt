package com.tes.global.view.activity

import android.os.Bundle
import com.tes.global.databinding.ActivityRegisterBinding
import com.tes.global.helper.viewBinding
import com.tes.global.view.base.BaseActivity

class RegisterActivity : BaseActivity() {
    private val binding by viewBinding(ActivityRegisterBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setListener()
    }


    private fun setListener(){

    }
}