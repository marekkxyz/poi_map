package com.mkaszycki.data

import com.mkaszycki.data.api.wikipedia.response.PoiEntity
import com.mkaszycki.poimap.domain.pois.Poi
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class PoiEntityMapperTest {
    private lateinit var mapper: PoiEntityMapper

    @Before
    fun setUp() {
        mapper = PoiEntityMapper()
    }

    @Test
    fun `PoiEntity into domain Poi`() {
        val input = PoiEntity(
            1,
            "TestPoint",
            1.0,
            2.0
        )
        val expected =
            Poi(1, 1.0, 2.0, "TestPoint")

        Assert.assertEquals(expected, mapper.map(input))
    }
}