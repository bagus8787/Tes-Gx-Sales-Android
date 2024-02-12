package com.tes.global.view.base

import android.app.Activity
import android.content.Context
import android.graphics.Typeface
import android.os.Bundle
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.tes.global.data.api.ApiClient
import com.tes.global.data.api.ApiInterface
import com.tes.global.data.viewModel.BaseViewModel
import com.tes.global.data.viewModel.ViewModelFactory
import com.tes.global.helper.Loading
import com.tes.global.helper.SessionManager
import io.github.inflationx.viewpump.ViewPumpContextWrapper

open class BaseActivity : AppCompatActivity(){

    private var toast: Toast? = null
    lateinit var pLoading : Loading

    lateinit var session: SessionManager
    lateinit var progressBar: ProgressBar
    lateinit var apiInterface: ApiInterface

    lateinit var baseViewModel: BaseViewModel

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        pLoading = Loading(this)
        session = SessionManager(this)
        apiInterface = ApiClient.client.create(ApiInterface::class.java)
        progressBar = ProgressBar(this)

        initViewModel()
    }

    private fun initViewModel(){
        baseViewModel = ViewModelProviders.of(
            this, ViewModelFactory(apiInterface)
        ).get(BaseViewModel::class.java)
    }

    fun toast(@StringRes message: Int) {
        toast(getString(message))
    }

    override fun onDestroy() {
        super.onDestroy()
    }


    fun setOveridePendingTransisi(context: Activity) {
        try {
            context.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    fun toast(toastMessage: String?) {
        if (toastMessage != null && !toastMessage.isEmpty()) {
            if (toast != null) toast!!.cancel()
            toast = Toast.makeText(this.applicationContext, toastMessage, Toast.LENGTH_LONG)
            toast!!.show()

        }
    }

}
