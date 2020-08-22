package com.mkaszycki.poimap

import com.google.android.gms.maps.model.LatLng
import com.mkaszycki.poimap.domain.helper.LatLngDomain
import com.mkaszycki.poimap.domain.route.LatLng as DomainLatLng

fun LatLng.toDomain(): DomainLatLng {
    return DomainLatLng(this.latitude, this.longitude)
}

fun LatLng.toLatLngDomain() = LatLngDomain(latitude, longitude)