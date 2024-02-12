package com.tes.global.view.activity

import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import com.tes.global.MyApplication.Companion.last_opened_tab
import com.tes.global.R
import com.tes.global.databinding.ActivityHomeBinding
import com.tes.global.helper.viewBinding
import com.tes.global.model.Status
import com.tes.global.view.base.BaseActivity
import com.tes.global.view.fragment.HomeFragment
import com.tes.global.view.fragment.leads.AddLeadsFragment

class HomeActivity : BaseActivity() {
    private val binding by viewBinding(ActivityHomeBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        checkPermission()
        getProfileMe()
        setListener()
    }

    private fun checkPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            val permissionlistener = object : PermissionListener {
                override fun onPermissionGranted() {

                }

                override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {

                }

            }

            TedPermission.create()
                .setPermissionListener(permissionlistener)
                .setPermissions(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                )
                .check()
        }
    }

    private fun setListener() {
        binding.bottomBar.setActiveItem(0)
        binding.bottomBar.onItemSelected = {
            last_opened_tab = it
            when (it) {
                0 -> findNavController(R.id.nav_host_fragment).navigate(R.id.homeFragment)
                1 -> findNavController(R.id.nav_host_fragment).navigate(R.id.leadFragment)
                2 -> {

                }
                3 -> findNavController(R.id.nav_host_fragment).navigate(R.id.shopFragment)
                4 -> showBottomSheetProfile()
            }
        }

        binding.imgAdd.setOnClickListener {
            findNavController(R.id.nav_host_fragment).navigate(R.id.addLeadsFragment)
        }
    }

    private fun getProfileMe(){
        baseViewModel.getProfileMe().observe(this){
            it?.let { resource ->
                when(resource.status){
                    Status.SUCCESS -> {
                        val data = resource.data
                        if (data != null){
//                            if(session.dataUser == null){
                                session.dataUser = data.data
//                            }
                        }

                    }
                    Status.ERROR -> {
                        toast("Sesi anda telah habis. Silahkan login kembali!")

                        session.isLogin(false)
                        session.token = ""

                        val i = Intent(this, LoginActivity::class.java)
                        i.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(i)
                    }
                    Status.LOADING -> {

                    }
                }
            }
        }
    }

    private fun showBottomSheetProfile(){
        val dialog = BottomSheetDialog(this, R.style.AppBottomSheetDialogTheme)

        val view = layoutInflater.inflate(R.layout.bottom_sheet_profile, null)

        val tvName = view.findViewById<TextView>(R.id.tvName)
        val tvEmail = view.findViewById<TextView>(R.id.tvEmail)

        val btnLogout = view.findViewById<Button>(R.id.btnLogout)

        tvName.text = session.dataUser?.name
        tvEmail.text = session.dataUser?.email

        tvName.setOnClickListener {
            dialog.dismiss()
        }

        btnLogout.setOnClickListener {
            session.isLogin(false)
            session.token = ""

            val i = Intent(this, LoginActivity::class.java)
            i.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(i)
        }

        dialog.setCancelable(true)
        dialog.setContentView(view)
        dialog.show()

    }


    fun showBottomBar(show: Boolean){
        binding.bottomBar.visibility = if (show){
            View.VISIBLE
        } else {
            View.GONE
        }

        binding.imgAdd.visibility = if (show){
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    fun dialogLogout() {
        val builder1: AlertDialog.Builder = AlertDialog.Builder(this)
        builder1.setMessage("Yakin ingin keluar dari Aplikasi?")
        builder1.setCancelable(true)
        builder1.setPositiveButton(
            "Ya"
        ) { dialog, id ->
            finish()
            dialog.cancel()
        }

        builder1.setNegativeButton(
            "Tidak"
        ) { dialog, id -> dialog.cancel() }
        val alert11: AlertDialog = builder1.create()
        alert11.show()
    }

    override fun onBackPressed() {
        when(getForegroundFragment()){
            is HomeFragment -> {
                dialogLogout()
            }
            is AddLeadsFragment -> {
                findNavController(R.id.nav_host_fragment).navigate(R.id.homeFragment)
            }

            else -> {
                super.onBackPressed()
            }
        }

    }

    // to check current active fragment
    private fun getForegroundFragment(): Fragment? {
        val navHostFragment: Fragment? =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        return navHostFragment?.childFragmentManager?.fragments?.get(0)
    }

}