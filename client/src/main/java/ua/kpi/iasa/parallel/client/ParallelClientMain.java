package ua.kpi.iasa.parallel.client;

import lombok.extern.slf4j.Slf4j;
import ua.kpi.iasa.parallel.client.config.SpringConfig;
import ua.kpi.iasa.parallel.client.runner.ConsoleAppRunner;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@Slf4j
public class ParallelClientMain {

    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(SpringConfig.class);
        ConsoleAppRunner app = context.getBean(ConsoleAppRunner.class);
        app.runApp();
    }
}
