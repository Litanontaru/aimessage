package org.aim.aimessage.backend.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ComponentScan("org.aim.aimessage")
@ImportResource("classpath:spring/application-context.xml")
public class ApplicationConfig {
}
