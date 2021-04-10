package com.wutsi.tracing

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import javax.servlet.http.HttpServletRequest
import kotlin.test.assertEquals

internal class RequestTracingContextTest {
    private lateinit var request: HttpServletRequest
    private lateinit var tc: TracingContext

    @BeforeEach
    fun setUp() {
        request = mock()
        tc = RequestTracingContext(request)
    }

    @Test
    fun `return request-id from Header`() {
        doReturn("from-header").whenever(request).getHeader(TracingContext.HEADER_REQUEST_ID)
        assertEquals("from-header", tc.requestId())
    }

    @Test
    fun `return random request-id when not available in header`() {
        assertEquals(36, tc.requestId().length)
    }

    @Test
    fun `return client-id from header`() {
        doReturn("from-header").whenever(request).getHeader(TracingContext.HEADER_CLIENT_ID)
        assertEquals("from-header", tc.clientId())
    }

    @Test
    fun `return NONE as client-id when not available in header`() {
        assertEquals(TracingContext.NONE, tc.clientId())
    }

    @Test
    fun `return device-id from header`() {
        doReturn("from-header").whenever(request).getHeader(TracingContext.HEADER_DEVICE_ID)
        assertEquals("from-header", tc.deviceId())
    }

    @Test
    fun `return NONE as device-id when not available in header`() {
        assertEquals(TracingContext.NONE, tc.deviceId())
    }
}
