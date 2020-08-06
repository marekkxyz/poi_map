package com.example.poimap

import com.google.android.gms.maps.model.LatLng
import com.example.poimap.domain.route.LatLng as DomainLatLng

fun LatLng.toDomain(): DomainLatLng {
    return DomainLatLng(this.latitude, this.longitude)
}