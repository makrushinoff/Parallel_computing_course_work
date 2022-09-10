package ua.kpi.iasa.parallel.server.runner;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component("socket")
@Primary
public class SocketApplicationRunner implements ApplicationRunner {

    @Override
    public void runApp() {

    }
}
