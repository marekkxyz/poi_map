package com.mkaszycki.poimap

import com.google.android.gms.maps.model.LatLng
import com.mkaszycki.poimap.domain.coordinates.LatLngDomain

fun LatLng.toLatLngDomain() = LatLngDomain(latitude, longitude)