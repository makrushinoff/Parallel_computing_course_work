package ua.kpi.iasa.parallel.server;

import ua.kpi.iasa.parallel.server.config.SpringConfig;
import ua.kpi.iasa.parallel.server.runner.ConsoleAppRunner;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ParallelServerMain {

    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(SpringConfig.class);
        ConsoleAppRunner app = context.getBean(ConsoleAppRunner.class);
        app.runApp();
    }
}
