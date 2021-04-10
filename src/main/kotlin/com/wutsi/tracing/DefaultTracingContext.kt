package com.wutsi.tracing

class DefaultTracingContext(
    private val requestId: String,
    private val clientId: String = TracingContext.NONE,
    private val deviceId: String = TracingContext.NONE
) : TracingContext {
    override fun requestId() = requestId
    override fun clientId() = clientId
    override fun deviceId() = deviceId
}
