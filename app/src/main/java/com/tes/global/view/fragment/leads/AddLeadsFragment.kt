package com.tes.global.view.fragment.leads

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.RadioGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.github.dhaval2404.imagepicker.ImagePicker
import com.tes.global.R
import com.tes.global.databinding.FragmentAddLeadsBinding
import com.tes.global.helper.viewBinding
import com.tes.global.model.Status
import com.tes.global.model.request.AddLeadsRequest
import com.tes.global.view.activity.HomeActivity
import com.tes.global.view.base.BaseFragment
import java.io.File


class AddLeadsFragment : BaseFragment(R.layout.fragment_add_leads) {

    private val binding by viewBinding(FragmentAddLeadsBinding::bind)

    private var selectIdBranchOffice = 0

    private val MAP_BUTTON_REQUEST_CODE = 1

    private var fileIdentity: File?= null
    private var imgUri: Uri?= null

    private var locationAddress = ""

    private var latUser = 0.0
    private var lngUser = 0.0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        locationAddress = arguments?.getString("locationAddress")?: ""

        latUser = arguments?.getDouble("latUser")?: 0.0
        lngUser = arguments?.getDouble("lngUser")?: 0.0

        getBranchOffice()
        setView()
        setListener()
    }

    private fun getBranchOffice(){
        baseViewModel.getBranchOffice().observe(viewLifecycleOwner){
            it?.let { resource ->
                when(resource.status){
                    Status.SUCCESS -> {
                        val data = resource.data?.data
                        if (data?.isNotEmpty() == true){
                            val listOb = mutableListOf<String>()

                            listOb.add("- Select Branch Office -")
                            data.forEach {
                                listOb.add(it.name)
                            }

                            val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
                                requireContext(),
                                android.R.layout.simple_spinner_dropdown_item,
                                listOb
                            )
                            binding.spBranchOffice.adapter = adapter

                            binding.spBranchOffice.onItemSelectedListener = object :
                                AdapterView.OnItemSelectedListener {
                                override fun onItemSelected(
                                    parentView: AdapterView<*>?,
                                    selectedItemView: View?,
                                    position: Int,
                                    id: Long
                                ) {
                                    if (position == 0){
                                        selectIdBranchOffice = 0
                                    } else {
                                        selectIdBranchOffice = data[position - 1].id
//                                        toast("selectIdBranchOffice == $selectIdBranchOffice")
                                    }

                                }

                                override fun onNothingSelected(parentView: AdapterView<*>?) {
                                    // your code here
                                }
                            }
                        }
                    }
                    Status.ERROR -> {
                    }
                    Status.LOADING -> {
                    }
                }
            }
        }
    }

    private fun setView(){
        binding.toolbar.tvTitle.text = "Add Lead"

        if (latUser != 0.0){
            binding.edtLatitude.setText(latUser.toString())
            binding.edtLongitude.setText(lngUser.toString())

            binding.edtAddress.setText(locationAddress)
        }
    }

    private fun setListener(){
        binding.toolbar.imgBack.setOnClickListener {
            activity?.onBackPressed()
        }

        binding.tvSelectLocation.setOnClickListener {
            findNavController().navigate(R.id.mapsFragment)
        }

        binding.lyAddPhoto.setOnClickListener {
            ImagePicker.with(this)
                .galleryOnly()
                .maxResultSize(640, 640)
                .start { resultCode, data ->
                    when (resultCode) {
                        Activity.RESULT_OK -> {
                            fileIdentity = ImagePicker.getFile(data)!!
                            Log.d("dataImage", data?.data.toString())

                            imgUri = data?.data

                            binding.imgImage.setImageURI(data?.data)
                            binding.imgImage.visibility = View.VISIBLE
                        }
                        ImagePicker.RESULT_ERROR -> {
                            Toast.makeText(requireContext(), ImagePicker.getError(data).toString(), Toast.LENGTH_SHORT).show()
                        }
                        else -> {
                            Toast.makeText(requireContext(), "Task Cancelled", Toast.LENGTH_SHORT).show()
                        }
                    }
                }

        }

        binding.lyPickLocation.setOnClickListener {
            getLocationPicker()
        }

        var gender = "male"
        binding.rbGender.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rbMale -> {
                    gender = binding.rbMale.text.toString()
                }
                R.id.rbFemale -> {
                    gender = binding.rbFemale.text.toString()
                }
            }

//            toast("gender " + gender)
        })

        binding.btnNext.setOnClickListener {
            val fullname = binding.edtFullname.text.toString()
            val email = binding.edtEmail.text.toString()
            val phone = binding.edtPhoneNumber.text.toString()
            val address = binding.edtAddress.text.toString()
            val idendityNumber = binding.edtIdentityNumber.text.toString()

            var dataAddLeadsRequest = AddLeadsRequest(
                selectIdBranchOffice.toString(),
                "0",
                "0",
                "0",
                "0",
                "0",
                fullname,
                email,
                phone,
                address,
                latUser.toString(),
                lngUser.toString(),
                "Global Xtreme",
                "",
                gender.toLowerCase(),
                idendityNumber,
                fileIdentity
            )

            findNavController().navigate(R.id.addLeadsContinueFragment, bundleOf("dataAddLeadsRequest" to dataAddLeadsRequest))
        }
    }

    private fun getLocationPicker() {
//        val locationPickerIntent = LocationPickerActivity.Builder()
//            .withSearchZone("id_ID")
//            .withGeolocApiKey(requireContext().getString(R.string.google_maps_key))
////            .withGooglePlacesApiKey(requireContext().getString(R.string.google_maps_key))
//            .withSatelliteViewHidden()
//            .shouldReturnOkOnBackPressed()
//            .withGoogleTimeZoneEnabled()
//            .withUnnamedRoadHidden()
//            .build(requireContext())
//
//        //this is optional if you want to return RESULT_OK if you don't set the latitude/longitude and click back button
//        locationPickerIntent.putExtra("test", "this is a test")
//
//        startActivityForResult(locationPickerIntent, MAP_BUTTON_REQUEST_CODE)
    }


    override fun onResume() {
        super.onResume()
        (activity as HomeActivity).showBottomBar(false)
    }
}