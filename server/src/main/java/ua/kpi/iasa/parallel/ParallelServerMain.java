package ua.kpi.iasa.parallel;

import ua.kpi.iasa.parallel.config.SpringConfig;
import ua.kpi.iasa.parallel.runner.ConsoleAppRunner;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ParallelServerMain {

    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(SpringConfig.class);
        ConsoleAppRunner app = context.getBean(ConsoleAppRunner.class);
        app.runApp();
    }
}
