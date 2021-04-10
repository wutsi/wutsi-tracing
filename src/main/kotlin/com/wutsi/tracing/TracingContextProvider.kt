package com.wutsi.tracing

import org.springframework.context.ApplicationContext
import java.util.UUID
import kotlin.concurrent.getOrSet

open class TracingContextProvider(
    private val context: ApplicationContext
) {
    private val tc: ThreadLocal<TracingContext> = ThreadLocal()

    open fun get(): TracingContext =
        try {
            context.getBean(RequestTracingContext::class.java)
        } catch (ex: Exception) {
            getDefault()
        }

    private fun getDefault(): TracingContext =
        tc.getOrSet {
            DefaultTracingContext(requestId = UUID.randomUUID().toString())
        }
}
