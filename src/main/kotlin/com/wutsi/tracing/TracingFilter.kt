package com.wutsi.tracing

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
        TracingMDCHelper.initMDC(tracingContext)
        chain.doFilter(req, resp)
    }

    override fun init(config: FilterConfig) {
    }

    override fun destroy() {
    }
}
