package io.snabble.pay.network.util

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.verify
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.ZonedDateTime

class ZonedDateTimeAsTextSerializerTest : FreeSpec({

    @OptIn(ExperimentalSerializationApi::class)
    "descriptor is a PrimitiveSerialDescriptor" {
        ZonedDateTimeAsTextSerializer.descriptor.serialName shouldBe "ZonedDateTime"
        ZonedDateTimeAsTextSerializer.descriptor.kind shouldBe PrimitiveKind.STRING
    }

    "serialize() encodes the ZonedDateTime" {
        val encoder = mockk<Encoder>(relaxed = true)
        val encodedTime = "Friday"
        val zonedDateTime = mockk<ZonedDateTime> {
            every { this@mockk.toString() } returns encodedTime
        }

        ZonedDateTimeAsTextSerializer.serialize(
            encoder = encoder,
            value = zonedDateTime
        )

        verify { zonedDateTime.toString() }
        verify(exactly = 1) { encoder.encodeString(encodedTime) }
    }

    "deserialize() parses the give time string" {
        val zonedDateTimeMock = mockk<ZonedDateTime>()
        mockkStatic("java.time.ZonedDateTime")
        every { ZonedDateTime.parse(any()) } returns zonedDateTimeMock
        val encodedTime = "Friday"
        val decoder = mockk<Decoder> {
            every { decodeString() } returns encodedTime
        }

        val zonedDateTime = ZonedDateTimeAsTextSerializer.deserialize(decoder)

        zonedDateTime shouldBe zonedDateTimeMock
        verify { ZonedDateTime.parse(encodedTime) }
        verify { decoder.decodeString() }
    }
})
