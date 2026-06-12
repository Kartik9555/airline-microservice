package com.learning.seat.service.auditing;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Optional;

@Component("auditorAware")
public class HeaderAuditorAware implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {

        ServletRequestAttributes attributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        if (attributes == null) {
            return Optional.empty();
        }

        HttpServletRequest request = attributes.getRequest();

        String email = request.getHeader("X-User-Email");

        if (email != null && email.contains("@")) {
            return Optional.of(email);
        }

        return Optional.empty();
    }
}
