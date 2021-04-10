package com.wutsi.tracing

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.slf4j.MDC
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

internal class TracingFilterTest {
    private lateinit var tc: TracingContext
    private lateinit var tracingContextProvider: TracingContextProvider
    private lateinit var filter: Filter
    private lateinit var request: HttpServletRequest
    private lateinit var response: HttpServletResponse
    private lateinit var chain: FilterChain

    @BeforeEach
    fun setUp() {
        request = mock()
        response = mock()
        chain = mock()

        tc = mock()
        tracingContextProvider = mock()
        doReturn(tc).whenever(tracingContextProvider).get()

        filter = TracingFilter(tracingContextProvider)
    }

    @Test
    fun doFilter() {
        doReturn("device-id").whenever(tc).deviceId()
        doReturn("request-id").whenever(tc).requestId()
        doReturn("client-id").whenever(tc).clientId()

        filter.doFilter(request, response, chain)

        verify(chain).doFilter(request, response)
        assertEquals("device-id", MDC.get("device_id"))
        assertEquals("client-id", MDC.get("client_id"))
        assertEquals("request-id", MDC.get("request_id"))
    }
}
