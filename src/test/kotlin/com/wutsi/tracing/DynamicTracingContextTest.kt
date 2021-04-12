package com.wutsi.tracing

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.doThrow
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.context.ApplicationContext
import javax.servlet.http.HttpServletRequest

internal class DynamicTracingContextTest {
    private lateinit var request: HttpServletRequest
    private lateinit var tc: TracingContext
    private lateinit var context: ApplicationContext

    @BeforeEach
    fun setUp() {
        request = mock()
        context = mock()
        tc = DynamicTracingContext(context)
    }

    @Test
    fun `return request-id from Header`() {
        doReturn(request).whenever(context).getBean(HttpServletRequest::class.java)
        doReturn("from-header").whenever(request).getHeader(TracingContext.HEADER_REQUEST_ID)
        assertEquals("from-header", tc.requestId())
    }

    @Test
    fun `return request-id from ThreadLocal when request not in Bean context`() {
        doReturn(null).whenever(context).getBean(HttpServletRequest::class.java)
        assertNotNull(tc.requestId())
        assertEquals(36, tc.requestId().length)
    }

    @Test
    fun `return request-id from ThreadLocal on error from ApplicationContext`() {
        doThrow(RuntimeException::class).whenever(context).getBean(HttpServletRequest::class.java)
        assertNotNull(tc.requestId())
        assertEquals(36, tc.requestId().length)
    }

    @Test
    fun `return reques-id from ThreadLocal when not available in request header`() {
        doReturn(request).whenever(context).getBean(HttpServletRequest::class.java)
        assertNotNull(tc.requestId())
        assertEquals(36, tc.requestId().length)
    }

    @Test
    fun `return client-id from header`() {
        doReturn(request).whenever(context).getBean(HttpServletRequest::class.java)
        doReturn("from-header").whenever(request).getHeader(TracingContext.HEADER_CLIENT_ID)
        assertEquals("from-header", tc.clientId())
    }

    @Test
    fun `return NONE as client-id when request not in Bean context`() {
        doReturn(null).whenever(context).getBean(HttpServletRequest::class.java)
        assertEquals(TracingContext.NONE, tc.clientId())
    }

    @Test
    fun `return NONE as client-id when not available in request header`() {
        doReturn(request).whenever(context).getBean(HttpServletRequest::class.java)
        assertEquals(TracingContext.NONE, tc.clientId())
    }

    @Test
    fun `return device-id from header`() {
        doReturn(request).whenever(context).getBean(HttpServletRequest::class.java)
        doReturn("from-header").whenever(request).getHeader(TracingContext.HEADER_DEVICE_ID)
        assertEquals("from-header", tc.deviceId())
    }

    @Test
    fun `return NONE as device-id when request not in Bean context`() {
        doReturn(null).whenever(context).getBean(HttpServletRequest::class.java)
        assertEquals(TracingContext.NONE, tc.deviceId())
    }

    @Test
    fun `return NONE as device-id when not available in request header`() {
        doReturn(request).whenever(context).getBean(HttpServletRequest::class.java)
        assertEquals(TracingContext.NONE, tc.deviceId())
    }
}
