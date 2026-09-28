package com.redshell.redshop.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

@Component
public class SecurityEventListener {

    private static final Logger log =
            LoggerFactory.getLogger(SecurityEventListener.class);

    @EventListener
    public void onAuthenticationSuccess(AuthenticationSuccessEvent event) {
        Authentication authentication = event.getAuthentication();

        log.info(
                "SECURITY_EVENT LOGIN_SUCCESS user={} ip={}",
                authentication.getName(),
                getIp(authentication)
        );
    }

    @EventListener
    public void onAuthenticationFailure(AbstractAuthenticationFailureEvent event) {
        Authentication authentication = event.getAuthentication();

        log.warn(
                "SECURITY_EVENT LOGIN_FAILURE username={} ip={}",
                authentication.getName(),
                getIp(authentication)
        );
    }

    private String getIp(Authentication authentication) {

        if (authentication.getDetails() instanceof WebAuthenticationDetails details) {
            return details.getRemoteAddress();
        }

        return "unknown";
    }
}
