package org.aim.aimessage.backend;

import org.aim.aimessage.backend.config.ApplicationConfig;
import org.aim.aimessage.backend.config.WebConfig;
import org.aim.aimessage.backend.config.WebSocketConfig;
import org.aim.aimessage.backend.security.WebSocketSecurityConfig;
import org.aim.aimessage.backend.security.SecurityConfig;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.ServletRegistration;

public class AppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[] { SecurityConfig.class, WebSocketSecurityConfig.class, ApplicationConfig.class };
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[] { WebConfig.class, WebSocketConfig.class };
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" };
    }

    @Override
    protected void customizeRegistration(ServletRegistration.Dynamic registration) {
        registration.setInitParameter("dispatchOptionsRequest", "true");
    }

}
