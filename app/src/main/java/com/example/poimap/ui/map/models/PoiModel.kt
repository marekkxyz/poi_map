package com.example.poimap.ui.map.models

import com.google.android.gms.maps.model.LatLng

data class PoiModel(val id: Int, val latLng: LatLng, val title: String)