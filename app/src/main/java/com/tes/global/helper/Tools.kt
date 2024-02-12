package com.tes.global.helper

import android.annotation.SuppressLint
import android.content.Context
import android.location.LocationManager
import android.widget.Toast
import com.tes.global.R
import java.text.SimpleDateFormat
import java.util.Locale

class Tools {
    companion object {
        @SuppressLint("SimpleDateFormat")
        fun convertToDate(defaultDate: String): String {
//            08/02/2024 07:54
            val id = Locale("in", "ID")
            val localDate = SimpleDateFormat("dd/MM/yyyy hh:mm").parse(defaultDate)
            return SimpleDateFormat("dd MMMM yyyy", id).format(localDate)
        }

        val colors = arrayOf(
            R.color.blue_fadi,
            R.color.red_warning,
            R.color.light_green_600,
            R.color.darkBlue,
            R.color.yellow_parane,
            R.color.blue_fadi,
            R.color.Orange
        )
        val randomColor = colors.random()

        fun checkGps(context: Context): Boolean {
            var isGpsOn = false
            val manager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                Toast.makeText(context, "GPS is disable!", Toast.LENGTH_LONG).show()
            } else {
                isGpsOn = true
                //            Toast.makeText(context, "GPS is Enable!", Toast.LENGTH_LONG).show();
            }
            return isGpsOn
        }
    }
}