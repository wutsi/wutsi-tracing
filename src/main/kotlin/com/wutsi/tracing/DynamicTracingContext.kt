package com.wutsi.tracing

import org.springframework.context.ApplicationContext
import java.util.UUID
import javax.servlet.http.HttpServletRequest
import kotlin.concurrent.getOrSet

open class DynamicTracingContext(
    private val context: ApplicationContext
) : TracingContext {
    private val defaultRequestId: ThreadLocal<String> = ThreadLocal()

    override fun requestId() =
        getHttpServletRequest()?.getHeader(TracingContext.HEADER_REQUEST_ID) ?: defaultRequestId.getOrSet { UUID.randomUUID().toString() }

    override fun clientId() = getHttpServletRequest()?.getHeader(TracingContext.HEADER_CLIENT_ID) ?: TracingContext.NONE
    override fun deviceId() = getHttpServletRequest()?.getHeader(TracingContext.HEADER_DEVICE_ID) ?: TracingContext.NONE

    private fun getHttpServletRequest(): HttpServletRequest? {
        try {
            return context.getBean(HttpServletRequest::class.java)
        } catch (ex: Exception) {
            return null
        }
    }
}
