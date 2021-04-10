package com.wutsi.tracing

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import feign.RequestTemplate
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class TracingRequestInterceptorTest {
    private lateinit var tc: TracingContext
    private lateinit var tracingContextProvider: TracingContextProvider
    private lateinit var interceptor: TracingRequestInterceptor

    @BeforeEach
    fun setUp() {
        tc = mock()
        tracingContextProvider = mock()
        doReturn(tc).whenever(tracingContextProvider).get()

        interceptor = TracingRequestInterceptor("foo", tracingContextProvider)
    }

    @Test
    fun apply() {
        doReturn("device-id").whenever(tc).deviceId()
        doReturn("request-id").whenever(tc).requestId()

        val template = RequestTemplate()

        interceptor.apply(template)

        assertTrue(template.headers()[TracingContext.HEADER_DEVICE_ID]!!.contains("device-id"))
        assertTrue(template.headers()[TracingContext.HEADER_REQUEST_ID]!!.contains("request-id"))
        assertTrue(template.headers()[TracingContext.HEADER_CLIENT_ID]!!.contains("foo"))
    }
}
