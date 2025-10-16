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

@Configuration
@EnableFeignClients(basePackages = "mss301.fa25.s4.content_service")
@Slf4j
public class FeignConfiguration {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return new AuthenticationRequestInterceptor();
    }

    @Slf4j
    public static class AuthenticationRequestInterceptor implements RequestInterceptor {
        @Override
        public void apply(RequestTemplate template) {
            ServletRequestAttributes servletRequestAttributes =
                    (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

            if (servletRequestAttributes != null) {
                var authHeader = servletRequestAttributes.getRequest().getHeader("Authorization");
                log.info("Authorization Header: {}", authHeader);

                if (StringUtils.hasText(authHeader)) {
                    template.header("Authorization", authHeader);
                }
            }
        }
    }
}