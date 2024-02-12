package com.tes.global.view.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.tes.global.databinding.ActivitySplashScreenBinding
import com.tes.global.helper.viewBinding
import com.tes.global.view.base.BaseActivity

class SplashScreenActivity : BaseActivity() {
    private val binding by viewBinding(ActivitySplashScreenBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        Handler().postDelayed({
            // This method will be executed once the timer is over
            // Start your app main activity
            if (session.isLogin()){
                val i = Intent(this, HomeActivity::class.java)
                i.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(i)

            } else {
                val i = Intent(this, LoginActivity::class.java)
                i.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(i)
            }
        }, SPLASH_TIME_OUT.toLong())
    }

    companion object {
        // Splash screen timer
        private const val SPLASH_TIME_OUT = 1000
    }
}
