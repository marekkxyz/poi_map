package com.example.poimap.ui.map

import android.Manifest
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.poimap.R
import com.example.poimap.ui.map.models.PoiModel
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.bottom_sheet_poi_details.*
import kotlinx.android.synthetic.main.bottom_sheet_route_suggestions.*
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.OnNeverAskAgain
import permissions.dispatcher.RuntimePermissions
import timber.log.Timber
import javax.inject.Inject

@RuntimePermissions
class MapActivity : DaggerAppCompatActivity(), OnMapReadyCallback {

    @Inject
    lateinit var viewModelFactory: MapViewModelFactory

    // Map
    private lateinit var mMap: GoogleMap
    private lateinit var viewModel: MapViewModel
    private val markerToPoi = HashMap<String, PoiModel>()
    private var polyline: Polyline? = null
    private val mapStateObserver = Observer<MapViewState> { state ->
        when (state) {
            is GetPoisState -> onGetPois(state)
            is GetPoiDetailsState -> onGetPoiDetails(state)
            is GetRouteState -> onGetRoute(state)
            is ErrorState -> onError(state.type)
        }
    }

    // details
    private lateinit var poiDetailsSheetBehavior: BottomSheetBehavior<View>

    // suggestions
    private lateinit var routeSuggestionsSheetBehavior: BottomSheetBehavior<View>

    private fun onGetRoute(state: GetRouteState) {
        Timber.tag(tag)
            .d("onGetRoute, points = ${state.routeModel.points.size}, suggestions = ${state.routeModel.suggestions.size}")
        showRouteOnMap(state)
    }

    private fun onGetPois(state: GetPoisState) {
        Timber.tag(tag).d("onGetPois, POIs amount = ${state.pois.size}")
        showPoisOnMap(state)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupMap()
        setupPoiDetails()
        setupRouteSuggestions()
    }

    private fun setupMap() {
        viewModel = ViewModelProvider(this, viewModelFactory).get(MapViewModel::class.java)
        viewModel.state.observe(this, mapStateObserver)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    private fun setupRouteSuggestions() {
        routeSuggestionsRecyclerView.layoutManager = LinearLayoutManager(this)
        routeSuggestionsSheetBehavior = BottomSheetBehavior.from(routeSuggestionsBottomSheet)
        hideRouteSheet()
    }

    private fun setupPoiDetails() {
        poiDetailsImagesRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        poiDetailsSheetBehavior = BottomSheetBehavior.from(poiDetailsBottomSheet)
        hidePoiDetailsSheet()
        poiDetailsSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {}

            override fun onStateChanged(bottomSheet: View, newState: Int) =
                onPoiDetailsSheetStateChanged(newState)
        })

        routeBtn.setOnClickListener {
            onRouteButtonClicked()
        }
    }

    private fun onRouteButtonClicked() {
        hidePoiDetailsSheet()
        routeSuggestionsSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        viewModel.getRoute()
    }

    private fun onPoiDetailsSheetStateChanged(newState: Int) {
        when (newState) {
            BottomSheetBehavior.STATE_EXPANDED -> {
                poiDetailsTitleTextView.isSingleLine = false
                poiDetailsDescriptionTxtView.isSingleLine = false
            }
            BottomSheetBehavior.STATE_COLLAPSED -> {
                poiDetailsTitleTextView.isSingleLine = true
                poiDetailsDescriptionTxtView.isSingleLine = true
            }
            else -> {
            }
        }
    }

    private fun onError(type: MapErrorType) {
        hidePoiDetailsSheet()
        hideRouteSheet()
        val errorMsgId = when (type) {
            MapErrorType.GET_POI_ERROR -> R.string.error_pois
            MapErrorType.GET_POI_DETAILS_ERROR -> R.string.error_poi_details
            MapErrorType.GET_ROUTE_ERROR -> R.string.error_route
        }

        Snackbar.make(rootView, getString(errorMsgId), Snackbar.LENGTH_SHORT).show()
    }

    private fun showRouteOnMap(state: GetRouteState) {
        polyline?.remove()
        polyline = mMap.addPolyline(PolylineOptions().addAll(state.routeModel.points))
        polyline!!.color = Color.BLUE

        val latLngBoundsBuilder = LatLngBounds.builder()
        state.routeModel.points.forEach {
            latLngBoundsBuilder.include(it)
        }
        moveCamera(latLngBoundsBuilder.build())

        routeSuggestionsRecyclerView.adapter =
            RouteSuggestionsListAdapter(state.routeModel.suggestions)
    }

    private fun onGetPoiDetails(state: GetPoiDetailsState) {
        Timber.tag(tag).d("onGetPoiDetails: show details for ${state.details.title}")
        hideRouteSheet()
        showPoiDetailsBottomSheet(state)
    }

    private fun showPoiDetailsBottomSheet(state: GetPoiDetailsState) {
        state.details.run {
            poiDetailsTitleTextView.text = title
            poiDetailsDescriptionTxtView.text = ""
            description?.let {
                poiDetailsDescriptionTxtView.text = it
            }
            poiDetailsImagesRecyclerView.adapter = PoiDetailsImageListAdapter(images)
        }

        if (poiDetailsSheetBehavior.state == BottomSheetBehavior.STATE_EXPANDED) {
            return
        }

        poiDetailsSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    override fun onResume() {
        super.onResume()
        val status = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this)
        if (status != ConnectionResult.SUCCESS) {
            GoogleApiAvailability.getInstance().getErrorDialog(this, status, 0).show()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        onRequestPermissionsResult(requestCode, grantResults)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        Timber.tag(tag).d("onMapReady")
        mMap = googleMap
        mMap.setOnMarkerClickListener { marker ->
            onMarkerClicked(marker)
        }
        requestPointOfInterestsWithPermissionCheck()
    }

    private fun onMarkerClicked(marker: Marker): Boolean {
        Timber.tag(tag)
            .d("onMarkerClicked : ${marker.title} clicked, camera zoom = ${mMap.cameraPosition.zoom}")
        val poi = markerToPoi[marker.id]
        poi?.let {
            if (mMap.cameraPosition.zoom > cameraPoiDetailsZoom) {
                mMap.animateCamera(CameraUpdateFactory.newLatLng(poi.latLng))
            } else {
                mMap.animateCamera(
                    CameraUpdateFactory.newLatLngZoom(poi.latLng, cameraPoiDetailsZoom)
                )
            }
            viewModel.getPoiDetails(it)
        }
        return true
    }

    @NeedsPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    fun requestPointOfInterests() {
        enableMyLocationIndicatior()
        viewModel.getPois()
    }

    private fun enableMyLocationIndicatior() {
        mMap.isMyLocationEnabled = true
        mMap.uiSettings.isMyLocationButtonEnabled = true
    }

    @OnNeverAskAgain(Manifest.permission.ACCESS_FINE_LOCATION)
    fun onLocationPermissionDenied() =
        Toast.makeText(this, R.string.permission_location_denied, Toast.LENGTH_LONG).show()

    private fun showPoisOnMap(state: GetPoisState) {
        val latLngBoundsBuilder = LatLngBounds.builder()
        state.pois.forEach { poi ->
            latLngBoundsBuilder.include(poi.latLng)
            val marker = mMap.addMarker(
                MarkerOptions().position(poi.latLng).title(poi.title).icon(
                    BitmapDescriptorFactory.fromResource(R.drawable.blue_dot)
                )
            )
            markerToPoi[marker.id] = poi
        }
        moveCamera(latLngBoundsBuilder.build())
    }

    private fun moveCamera(latLngBounds: LatLngBounds) {
        mMap.animateCamera(
            CameraUpdateFactory.newLatLngBounds(
                latLngBounds,
                cameraPadding
            ), cameraAnimationDuration, null
        )
    }

    private fun hidePoiDetailsSheet() {
        poiDetailsSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
    }

    private fun hideRouteSheet() {
        routeSuggestionsSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
    }

    override fun onBackPressed() {
        if (routeSuggestionsSheetBehavior.state == BottomSheetBehavior.STATE_EXPANDED) {
            routeSuggestionsSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            return
        }
        if (poiDetailsSheetBehavior.state == BottomSheetBehavior.STATE_EXPANDED) {
            poiDetailsSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            return
        }
        super.onBackPressed()
    }

    companion object {
        const val tag = "MapActivity"
        const val cameraPadding = 100
        const val cameraAnimationDuration = 1500
        const val cameraPoiDetailsZoom = 14.0f
    }
}
