package it.auth.security.starter;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@AutoConfiguration
@ComponentScan(basePackages = {"it.auth.security.starter"})
@EnableJpaRepositories(basePackages = {"it.auth.security.starter.repository"})
public class AuthSecurityStarterApplication {

}
