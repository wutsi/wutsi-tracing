package com.wutsi.tracing

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.doThrow
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.context.ApplicationContext
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

internal class TracingContextProviderTest {
    private lateinit var context: ApplicationContext
    private lateinit var provider: TracingContextProvider

    @BeforeEach
    fun setUp() {
        context = mock()
        provider = TracingContextProvider(context)
    }

    @Test
    fun `return TracingContext from sprint context`() {
        val tc = mock<RequestTracingContext>()
        doReturn(tc).whenever(context).getBean(RequestTracingContext::class.java)

        val result = provider.get()
        assertEquals(tc, result)
    }

    @Test
    fun `return default TracingContext when not available in spring context`() {
        doReturn(null).whenever(context).getBean(RequestTracingContext::class.java)

        val result = provider.get()
        assertNotNull(result)
        assertTrue(result is DefaultTracingContext)
    }

    @Test
    fun `return default TracingContext when context request fails`() {
        doThrow(RuntimeException::class).whenever(context).getBean(RequestTracingContext::class.java)

        val result = provider.get()
        assertNotNull(result)
        assertTrue(result is DefaultTracingContext)
    }
}
