package mss301.fa25.s4.content_service.configuration;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import feign.Logger;

@Configuration
@EnableFeignClients(basePackages = "mss301.fa25.s4.content_service")
@Slf4j
public class FeignConfiguration {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return new AuthenticationRequestInterceptor();
    }

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    @Slf4j
    public static class AuthenticationRequestInterceptor implements RequestInterceptor {
        @Override
        public void apply(RequestTemplate template) {
            log.debug("Applying Feign request interceptor for: {}", template.url());
            
            ServletRequestAttributes servletRequestAttributes =
                    (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

            if (servletRequestAttributes != null) {
                var authHeader = servletRequestAttributes.getRequest().getHeader("Authorization");
                
                if (StringUtils.hasText(authHeader)) {
                    log.info("Forwarding Authorization header to Feign client");
                    template.header("Authorization", authHeader);
                } else {
                    log.warn("No Authorization header found in request");
                }
            } else {
                log.warn("No ServletRequestAttributes available - cannot forward Authorization header");
            }
        }
    }
}