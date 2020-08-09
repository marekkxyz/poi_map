package com.mkaszycki.poimap.domain

interface Mapper<in From, out To> {
    fun map(obj: From): To
}