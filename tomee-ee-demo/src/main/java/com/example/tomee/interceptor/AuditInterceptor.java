package com.example.tomee.interceptor;

import jakarta.annotation.Priority;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import java.util.logging.Logger;

@Audited
@Interceptor
@Priority(Interceptor.Priority.APPLICATION)
public class AuditInterceptor {

    private static final Logger LOG = Logger.getLogger(AuditInterceptor.class.getName());

    @AroundInvoke
    public Object logCall(InvocationContext ctx) throws Exception {
        long start = System.nanoTime();
        try {
            return ctx.proceed();
        } finally {
            long elapsedMs = (System.nanoTime() - start) / 1_000_000;
            LOG.info(() -> "[TomEE Audit] " + ctx.getMethod().getName() + " executed in " + elapsedMs + " ms");
        }
    }
}