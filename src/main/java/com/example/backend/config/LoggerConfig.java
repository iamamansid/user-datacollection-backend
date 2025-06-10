package com.example.backend.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;


@Configuration
public class LoggerConfig implements WebMvcConfigurer {

    private static final Logger log = LoggerFactory.getLogger(LoggerConfig.class);

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new RequestLoggingInterceptor());
    }


    private static class RequestLoggingInterceptor extends HandlerInterceptorAdapter {

        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
            try {
                String requestId = UUID.randomUUID().toString();
                request.setAttribute("requestId", requestId);
                
                log.info(
                    "REQUEST [{}] {} {} from {} - User-Agent: {}",
                    requestId,
                    request.getMethod(),
                    request.getRequestURI(),
                    request.getRemoteAddr(),
                    request.getHeader("User-Agent")
                );
                return true;
            } catch (Exception e) {
                log.error("Error in request logging interceptor", e);
                return true;
            }
        }

        @Override
        public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
            try {
                String requestId = (String) request.getAttribute("requestId");
                
                log.info(
                    "RESPONSE [{}] {} {} - Status: {}",
                    requestId,
                    request.getMethod(),
                    request.getRequestURI(),
                    response.getStatus()
                );
                
                if (ex != null) {
                    log.error("EXCEPTION during request processing: {}", ex.getMessage(), ex);
                }
            } catch (Exception e) {
                log.error("Error in response logging interceptor", e);
            }
        }
    }
}
