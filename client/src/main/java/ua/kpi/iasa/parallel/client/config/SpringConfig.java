package ua.kpi.iasa.parallel.client.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan("ua.kpi.iasa.parallel.client")
@PropertySource("classpath:application.properties")
public class SpringConfig {}
