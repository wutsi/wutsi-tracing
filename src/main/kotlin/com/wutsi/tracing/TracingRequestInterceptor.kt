package com.wutsi.tracing

import feign.RequestInterceptor
import feign.RequestTemplate

class TracingRequestInterceptor(
    private val clientId: String,
    private val tracingContextProvider: TracingContextProvider
) : RequestInterceptor {
    override fun apply(template: RequestTemplate) {
        val tracingContext = tracingContextProvider.get()
        template.header(TracingContext.HEADER_CLIENT_ID, clientId)
        template.header(TracingContext.HEADER_DEVICE_ID, tracingContext.deviceId())
        template.header(TracingContext.HEADER_REQUEST_ID, tracingContext.requestId())
    }
}
