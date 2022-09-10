package ua.kpi.iasa.parallel.client.runner;

import java.text.MessageFormat;
import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ua.kpi.iasa.parallel.client.connector.IndexingConnector;
import ua.kpi.iasa.parallel.client.ui.ConsoleUtil;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ConsoleAppRunner implements Runner {

    private final IndexingConnector indexingConnector;

    @Override
    public void runApp() {
        ConsoleUtil.printGreetings();
        while (true) {
            switch (ConsoleUtil.chooseMenuOptions()) {
                case 1 -> handleIndexingOption();
                case 2 -> handleGetTextsOption();

                default -> System.exit(0);
            }
        }
    }

    private void handleIndexingOption() {
        try {
            long indexingMicrosecondsTime = indexingConnector.indexFiles(ConsoleUtil.handleIndexFiles());
            ConsoleUtil.sendMessage(MessageFormat.format("Indexing completed successful by {0}mcs.", indexingMicrosecondsTime));
        } catch (Exception e) {
            log.warn("Error during indexing", e);
            ConsoleUtil.sendMessage(e.getMessage());
        }
    }

    private void handleGetTextsOption() {
        try {
            String keyword = ConsoleUtil.handleGetTextsByWord();
            List<String> textsByWord = indexingConnector.getTextsByWord(keyword);
            ConsoleUtil.showFileNamesForKeyword(keyword, textsByWord);
        } catch (Exception e) {
            log.warn("Error during indexing", e);
            ConsoleUtil.sendMessage(e.getMessage());
        }
    }

}
