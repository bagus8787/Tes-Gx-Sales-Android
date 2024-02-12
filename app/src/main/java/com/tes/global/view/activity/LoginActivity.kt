package com.tes.global.view.activity

import android.content.Intent
import android.os.Bundle
import com.tes.global.databinding.ActivityLoginBinding
import com.tes.global.helper.viewBinding
import com.tes.global.model.Status
import com.tes.global.view.base.BaseActivity

class LoginActivity : BaseActivity() {
    private val binding by viewBinding(ActivityLoginBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setListener()
    }


    private fun setListener(){
        binding.btnSignIn.setOnClickListener {
            val email = binding.edtEmail.text.toString()
            val pass = binding.edtPassword.text.toString()

            if (email.isEmpty()){
                toast("Email harus diisi!")
                return@setOnClickListener
            }
            if (pass.isEmpty()){
                toast("Password harus diisi!")
                return@setOnClickListener
            }

            baseViewModel.login(
                email, pass
            ).observe(this){
                it?.let { resource ->
                    when(resource.status){
                        Status.SUCCESS -> {
                            val data = resource.data
                            if(data != null){
                                session.token = data.token
                                session.isLogin(true)

                                val i = Intent(this, HomeActivity::class.java)
                                i.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                startActivity(i)
                            }

                            pLoading.dismissDialog()
                        }
                        Status.ERROR -> {
                            pLoading.dismissDialog()
                        }
                        Status.LOADING -> {
                            pLoading.showLoading()
                        }
                    }
                }
            }
        }
    }
}