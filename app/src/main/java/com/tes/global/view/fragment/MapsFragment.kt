package com.tes.global.view.fragment

import android.Manifest
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.tes.global.R
import com.tes.global.databinding.FragmentMapsBinding
import com.tes.global.helper.Tools
import com.tes.global.helper.viewBinding
import com.tes.global.view.activity.HomeActivity
import com.tes.global.view.base.BaseFragment
import java.util.Locale

class MapsFragment : BaseFragment(R.layout.fragment_maps), OnMapReadyCallback {

    private val binding by viewBinding(FragmentMapsBinding::bind)

    private var mapAttendance: SupportMapFragment? = null
    private var map: GoogleMap? = null

    private var locationAddress = ""

    private var latUser = 0.0
    private var lngUser = 0.0

    private var isMoveCamera = true

    private lateinit var currentLocation: Location
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val permissionCode = 101

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fusedLocationProviderClient =  LocationServices.getFusedLocationProviderClient(requireActivity())

        if(Tools.checkGps(requireContext())){
            fetchLocation()
        } else {
//            (activity as HomeActivity).buildAlertMessageNoGps()
        }

        setView()
        setListener()
    }

    private fun setView(){

    }

    private fun setListener(){
        binding.tvReset.setOnClickListener {
            locationAddress = ""
            latUser = 0.0
            lngUser = 0.0

            binding.btnSelectLocation.visibility = View.GONE

            map?.clear()
        }

        binding.btnBack.setOnClickListener {
            activity?.onBackPressed()
        }

        binding.btnSelectLocation.setOnClickListener {
            val bundle = bundleOf(
                "latUser" to latUser,
                "lngUser" to lngUser,
                "locationAddress" to locationAddress
            )
            findNavController().navigate(R.id.addLeadsFragment, bundle)
        }
    }

    private fun fetchLocation() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) !=
            PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) !=
            PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), permissionCode)
            return
        }

        val task = fusedLocationProviderClient.lastLocation
        task.addOnSuccessListener() {
            if (it != null) {
                currentLocation = it

                mapAttendance = childFragmentManager.findFragmentById(R.id.map_attendance) as SupportMapFragment
                mapAttendance?.getMapAsync(this)
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        val latLng = LatLng(currentLocation.latitude, currentLocation.longitude)

//        latUser = latLng.latitude
//        lngUser = latLng.longitude

        map?.isMyLocationEnabled = true

        map?.moveCamera(CameraUpdateFactory.newLatLng(latLng))
        map?.animateCamera(CameraUpdateFactory.zoomTo(12f))
//        map?.addMarker(markerOptions)

        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }

        map?.setOnMapClickListener(GoogleMap.OnMapClickListener { point ->
            map?.clear()
            val latLngs = LatLng(point.latitude, point.longitude)

            latUser = latLngs.latitude
            lngUser = latLngs.longitude
            getAddress(latLngs)

            val markerCustom = MarkerOptions().position(latLngs)
            map?.moveCamera(CameraUpdateFactory.newLatLng(latLngs))
            map?.addMarker(markerCustom)

            binding.btnSelectLocation.visibility = View.VISIBLE
        })

    }

    private fun getAddress(latLng: LatLng): String {
        val geocoder = Geocoder(requireContext(), Locale.getDefault())
        val addresses: List<Address>?
        val address: Address?
        var addressText = ""

        addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)

        if (addresses.isNotEmpty()) {
            address = addresses[0]
            addressText = address.getAddressLine(0)
            locationAddress = addressText

//            toast( "== " + locationAddress)
        } else{
            addressText = "its not appear"
        }
        return addressText
    }

    override fun onResume() {
        super.onResume()
        (activity as HomeActivity).showBottomBar(false)
    }
}