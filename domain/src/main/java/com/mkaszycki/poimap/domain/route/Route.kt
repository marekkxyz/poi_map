package com.mkaszycki.poimap.domain.route

import com.mkaszycki.poimap.domain.coordinates.LatLngDomain

data class Route(val points: List<LatLngDomain>, val suggestions: List<String>)