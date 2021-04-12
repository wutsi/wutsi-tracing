package com.wutsi.tracing

import org.slf4j.MDC
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.FilterConfig
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse

class TracingFilter(
    private val tracingContext: TracingContext
) : Filter {
    override fun doFilter(
        req: ServletRequest,
        resp: ServletResponse,
        chain: FilterChain
    ) {
        MDC.put("client_id", tracingContext.clientId())
        MDC.put("device_id", tracingContext.deviceId())
        MDC.put("request_id", tracingContext.requestId())

        chain.doFilter(req, resp)
    }

    override fun init(config: FilterConfig) {
    }

    override fun destroy() {
    }
}
