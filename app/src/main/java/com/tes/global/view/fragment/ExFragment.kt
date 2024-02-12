package com.tes.global.view.fragment

import android.os.Bundle
import android.view.View
import com.tes.global.R
import com.tes.global.databinding.FragmentExBinding
import com.tes.global.helper.viewBinding
import com.tes.global.view.base.BaseFragment

class ExFragment : BaseFragment(R.layout.fragment_ex) {

    private val binding by viewBinding(FragmentExBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setView()
        setListener()
    }

    private fun setView(){

    }

    private fun setListener(){

    }
}