package ua.kpi.iasa.parallel.config;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan("ua.kpi.iasa.parallel")
@PropertySource("classpath:application.properties")
public class SpringConfig {

    private static final String MODULE_NAME = "server";

    @Value("${filesToIndex.path}")
    private String[] pathsToFiles;

    @Bean
    public Path filesDirectoryPath() {
        return Paths.get(MODULE_NAME, pathsToFiles);
    }

}
