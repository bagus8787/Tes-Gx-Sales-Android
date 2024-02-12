package com.tes.global.view.fragment.leads

import android.app.Dialog
import android.content.Context
import android.graphics.Point
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.tes.global.R
import com.tes.global.databinding.FragmentAddLeadsContinueBinding
import com.tes.global.helper.viewBinding
import com.tes.global.model.Status
import com.tes.global.model.request.AddLeadsRequest
import com.tes.global.view.base.BaseFragment
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class AddLeadsContinueFragment : BaseFragment(R.layout.fragment_add_leads_continue) {

    private val binding by viewBinding(FragmentAddLeadsContinueBinding::bind)

    private var dataAddLeadsRequest: AddLeadsRequest?= null

    private var idSelectType = 0
    private var idSelectChannel = 0
    private var idSelectMedia = 0
    private var idSelectSource = 0
    private var idSelectStatus = 0
    private var idSelectProbability = 0

    private var fileIdentity: File?= null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dataAddLeadsRequest = arguments?.getParcelable("dataAddLeadsRequest")

        getLeadType()
        getLeadChannel()
        getLeadMedia()
        getLeadSource()
        getLeadStatus()
        getLeadProbability()

        setView()
        setListener()
    }

    private fun setView(){
        binding.toolbar.tvTitle.text = "Add Lead"

        if (dataAddLeadsRequest != null){
            fileIdentity = dataAddLeadsRequest?.fileIdentity
        }
    }

    private fun setListener(){
        binding.btnPrevious.setOnClickListener {
            activity?.onBackPressed()
        }

        binding.toolbar.imgBack.setOnClickListener {
            activity?.onBackPressed()
        }

        binding.btnSubmit.setOnClickListener {
            var notes = binding.edtNotes.text.toString()

            val builder = MultipartBody.Builder()
            builder.setType(MultipartBody.FORM)

            builder.addFormDataPart("branchOfficeId", dataAddLeadsRequest?.branchOfficeId.toString())
            builder.addFormDataPart("probabilityId", idSelectProbability.toString())
            builder.addFormDataPart("typeId", idSelectType.toString())
            builder.addFormDataPart("channelId", idSelectChannel.toString())
            builder.addFormDataPart("mediaId", idSelectMedia.toString())
            builder.addFormDataPart("sourceId", idSelectSource.toString())
            builder.addFormDataPart("fullName", dataAddLeadsRequest?.fullName.toString())
            builder.addFormDataPart("email", dataAddLeadsRequest?.email.toString())

            builder.addFormDataPart("phone", dataAddLeadsRequest?.phone.toString())
            builder.addFormDataPart("address", dataAddLeadsRequest?.address.toString())
            builder.addFormDataPart("latitude", dataAddLeadsRequest?.latitude.toString())
            builder.addFormDataPart("longitude", dataAddLeadsRequest?.longitude.toString())

            builder.addFormDataPart("companyName", dataAddLeadsRequest?.companyName.toString())
            builder.addFormDataPart("generalNotes", notes)
            builder.addFormDataPart("gender", dataAddLeadsRequest?.gender.toString())
            builder.addFormDataPart("IDNumber", dataAddLeadsRequest?.IDNumber.toString())

            fileIdentity.let {
                builder.addFormDataPart(
                    "IDNumberPhoto", fileIdentity?.name, fileIdentity!!.asRequestBody("multipart/form-data".toMediaTypeOrNull())
                )
            }

            val body = builder.build()

            baseViewModel.submitLead(body).observe(viewLifecycleOwner){
                it.let { resource ->
                    when(resource.status){
                        Status.SUCCESS -> {
                            pLoading.dismissDialog()

                            if (resource.data?.data != null){
                                showDialogSuccess()
                            }
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

    private fun getLeadType(){
        baseViewModel.getType().observe(viewLifecycleOwner){
            it?.let { resource ->
                when(resource.status){
                    Status.SUCCESS -> {
                        val data = resource.data?.data
                        if (data?.isNotEmpty() == true){
                            val listOb = mutableListOf<String>()

                            listOb.add("- Select Lead Type -")
                            data.forEach {
                                listOb.add(it.name)
                            }

                            val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
                                requireContext(),
                                android.R.layout.simple_spinner_dropdown_item,
                                listOb
                            )
                            binding.spLeadType.adapter = adapter

                            binding.spLeadType.onItemSelectedListener = object :
                                AdapterView.OnItemSelectedListener {
                                override fun onItemSelected(
                                    parentView: AdapterView<*>?,
                                    selectedItemView: View?,
                                    position: Int,
                                    id: Long
                                ) {
                                    if (position == 0){
                                        idSelectType = 0
                                    } else {
                                        idSelectType = data[position - 1].id
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

    private fun getLeadChannel(){
        baseViewModel.getChannel().observe(viewLifecycleOwner){
            it?.let { resource ->
                when(resource.status){
                    Status.SUCCESS -> {
                        val data = resource.data?.data
                        if (data?.isNotEmpty() == true){
                            val listOb = mutableListOf<String>()

                            listOb.add("- Select Lead Channel -")
                            data.forEach {
                                listOb.add(it.name)
                            }

                            val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
                                requireContext(),
                                android.R.layout.simple_spinner_dropdown_item,
                                listOb
                            )
                            binding.spLeadChannel.adapter = adapter

                            binding.spLeadChannel.onItemSelectedListener = object :
                                AdapterView.OnItemSelectedListener {
                                override fun onItemSelected(
                                    parentView: AdapterView<*>?,
                                    selectedItemView: View?,
                                    position: Int,
                                    id: Long
                                ) {
                                    if (position == 0){
                                        idSelectChannel = 0
                                    } else {
                                        idSelectChannel = data[position - 1].id
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

    private fun getLeadMedia(){
        baseViewModel.getMedia().observe(viewLifecycleOwner){
            it?.let { resource ->
                when(resource.status){
                    Status.SUCCESS -> {
                        val data = resource.data?.data
                        if (data?.isNotEmpty() == true){
                            val listOb = mutableListOf<String>()

                            listOb.add("- Select Lead Media -")
                            data.forEach {
                                listOb.add(it.name)
                            }

                            val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
                                requireContext(),
                                android.R.layout.simple_spinner_dropdown_item,
                                listOb
                            )
                            binding.spLeadMedia.adapter = adapter

                            binding.spLeadMedia.onItemSelectedListener = object :
                                AdapterView.OnItemSelectedListener {
                                override fun onItemSelected(
                                    parentView: AdapterView<*>?,
                                    selectedItemView: View?,
                                    position: Int,
                                    id: Long
                                ) {
                                    if (position == 0){
                                        idSelectMedia = 0
                                    } else {
                                        idSelectMedia = data[position - 1].id
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

    private fun getLeadSource(){
        baseViewModel.getSource().observe(viewLifecycleOwner){
            it?.let { resource ->
                when(resource.status){
                    Status.SUCCESS -> {
                        val data = resource.data?.data
                        if (data?.isNotEmpty() == true){
                            val listOb = mutableListOf<String>()

                            listOb.add("- Select Lead Source -")
                            data.forEach {
                                listOb.add(it.name)
                            }

                            val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
                                requireContext(),
                                android.R.layout.simple_spinner_dropdown_item,
                                listOb
                            )
                            binding.spLeadSource.adapter = adapter

                            binding.spLeadSource.onItemSelectedListener = object :
                                AdapterView.OnItemSelectedListener {
                                override fun onItemSelected(
                                    parentView: AdapterView<*>?,
                                    selectedItemView: View?,
                                    position: Int,
                                    id: Long
                                ) {
                                    if (position == 0){
                                        idSelectSource = 0
                                    } else {
                                        idSelectSource = data[position - 1].id
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

    private fun getLeadStatus(){
        baseViewModel.getSource().observe(viewLifecycleOwner){
            it?.let { resource ->
                when(resource.status){
                    Status.SUCCESS -> {
                        val data = resource.data?.data
                        if (data?.isNotEmpty() == true){
                            val listOb = mutableListOf<String>()

                            listOb.add("- Select Lead Status -")
                            data.forEach {
                                listOb.add(it.name)
                            }

                            val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
                                requireContext(),
                                android.R.layout.simple_spinner_dropdown_item,
                                listOb
                            )
                            binding.spLeadStatus.adapter = adapter

                            binding.spLeadStatus.onItemSelectedListener = object :
                                AdapterView.OnItemSelectedListener {
                                override fun onItemSelected(
                                    parentView: AdapterView<*>?,
                                    selectedItemView: View?,
                                    position: Int,
                                    id: Long
                                ) {
                                    if (position == 0){
                                        idSelectStatus = 0
                                    } else {
                                        idSelectStatus = data[position - 1].id
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

    private fun getLeadProbability(){
        baseViewModel.getSource().observe(viewLifecycleOwner){
            it?.let { resource ->
                when(resource.status){
                    Status.SUCCESS -> {
                        val data = resource.data?.data
                        if (data?.isNotEmpty() == true){
                            val listOb = mutableListOf<String>()

                            listOb.add("- Select Lead Probability -")
                            data.forEach {
                                listOb.add(it.name)
                            }

                            val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
                                requireContext(),
                                android.R.layout.simple_spinner_dropdown_item,
                                listOb
                            )
                            binding.spLeadProbability.adapter = adapter

                            binding.spLeadProbability.onItemSelectedListener = object :
                                AdapterView.OnItemSelectedListener {
                                override fun onItemSelected(
                                    parentView: AdapterView<*>?,
                                    selectedItemView: View?,
                                    position: Int,
                                    id: Long
                                ) {
                                    if (position == 0){
                                        idSelectProbability = 0
                                    } else {
                                        idSelectProbability = data[position - 1].id
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

    private fun showDialogSuccess(){
        val pDialog = Dialog(requireContext(), R.style.DialogLight)
        pDialog.window!!.attributes.windowAnimations = R.style.PauseDialogAnimation
        pDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        pDialog.setContentView(R.layout.dialog_success_add_lead)
        pDialog.setCancelable(false)

        val btnOke : Button = pDialog.findViewById(R.id.btnDone)

        val size = Point()
        val wm = requireContext().getSystemService(Context.WINDOW_SERVICE) as WindowManager
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

        btnOke.setOnClickListener {
            pDialog.dismiss()

            findNavController().navigate(R.id.leadFragment)
        }
    }

}