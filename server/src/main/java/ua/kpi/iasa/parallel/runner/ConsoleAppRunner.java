package ua.kpi.iasa.parallel.runner;

import lombok.RequiredArgsConstructor;
import ua.kpi.iasa.parallel.service.IndexingService;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ConsoleAppRunner {

    public final IndexingService indexingService;

    public void runApp() {
        indexingService.indexFiles(5);
    }
}
