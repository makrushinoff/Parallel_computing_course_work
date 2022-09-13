package ua.kpi.iasa.parallel.server.config;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan("ua.kpi.iasa.parallel.server")
@PropertySource("classpath:application.properties")
public class SpringConfig {

    @Value("${module.name}")
    private String moduleName;

    @Value("${filesToIndex.path}")
    private String[] pathsToFiles;

    @Bean
    public Path filesDirectoryPath() {
        return Paths.get(moduleName, pathsToFiles);
    }

    @Bean
    public ExecutorService executorService() {
        return Executors.newFixedThreadPool(100);
    }

}
