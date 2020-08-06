package com.example.poimap.ui.map.models

import com.google.android.gms.maps.model.LatLng

data class RouteModel(val points: List<LatLng>, val suggestions: List<String>)