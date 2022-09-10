package ua.kpi.iasa.parallel.server;

import ua.kpi.iasa.parallel.server.config.SpringConfig;
import ua.kpi.iasa.parallel.server.runner.ApplicationRunner;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ParallelServerMain {

    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(SpringConfig.class);
        ApplicationRunner runner = args.length < 2 ?
                context.getBean(ApplicationRunner.class) :
                context.getBean(args[2], ApplicationRunner.class);

        runner.runApp();
    }
}
