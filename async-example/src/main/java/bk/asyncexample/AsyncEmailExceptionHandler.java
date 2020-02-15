package bk.asyncexample;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

import java.lang.reflect.Method;

@Slf4j
public class AsyncEmailExceptionHandler implements AsyncUncaughtExceptionHandler {

    @Override
    public void handleUncaughtException(final Throwable ex, final Method method, final Object... params) {
        log.error("async email exception message : {}, method : {}, params : {}", ex.getMessage(), method.getName(), params);
    }
}
