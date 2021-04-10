package com.wutsi.tracing

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class DefaultTracingContextTest {
    val tc = DefaultTracingContext("A", "B", "C")

    @Test
    fun requestId() {
        assertEquals("A", tc.requestId())
    }

    @Test
    fun clientId() {
        assertEquals("B", tc.clientId())
    }

    @Test
    fun deviceId() {
        assertEquals("C", tc.deviceId())
    }
}
