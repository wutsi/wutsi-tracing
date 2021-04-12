package com.wutsi.tracing

import org.slf4j.MDC

object TracingMDCHelper {
    fun initMDC(tracingContext: TracingContext) {
        MDC.put("client_id", tracingContext.clientId())
        MDC.put("device_id", tracingContext.deviceId())
        MDC.put("request_id", tracingContext.requestId())
    }
}
