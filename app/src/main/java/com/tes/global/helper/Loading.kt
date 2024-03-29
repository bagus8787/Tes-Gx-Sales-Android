package com.tes.global.helper

import android.app.Dialog
import android.content.Context
import android.graphics.Point
import android.view.Gravity
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import com.tes.global.R

class Loading(private val context: Context) {
    lateinit var pDialog: Dialog

    fun showLoading(pesan: String = "Mohon tunggu ...", cancelable: Boolean= false) {
        pDialog = Dialog(context, R.style.DialogLight)
        pDialog.window!!.attributes.windowAnimations = R.style.PauseDialogAnimation
        pDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        pDialog.setContentView(R.layout.item_loading_dialog)
        pDialog.setCancelable(cancelable)

        val tvLoading : TextView = pDialog.findViewById(R.id.tvLoading)

        tvLoading.text = pesan

        val size = Point()
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = wm.defaultDisplay
        display.getSize(size)
        val mWidth = size.x

        val window = pDialog.window
        val wlp = window!!.attributes as WindowManager.LayoutParams

        wlp.gravity = Gravity.CENTER
        wlp.x = 0
        wlp.y = 0
        wlp.width = mWidth
        window.attributes = wlp
        pDialog.show()
    }

    fun dismissDialog() {
        pDialog.dismiss()
    }
}
