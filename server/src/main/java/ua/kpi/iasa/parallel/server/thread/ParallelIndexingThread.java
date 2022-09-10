package ua.kpi.iasa.parallel.server.thread;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ua.kpi.iasa.parallel.server.model.InvertedIndex;

@RequiredArgsConstructor
@Slf4j
public class ParallelIndexingThread implements Runnable {

    private final InvertedIndex index;
    private final List<Path> pathsToIndex;
    private final int startIndex;
    private final int endIndex;

    @Override
    public void run() {
        log.debug("Thread start: {}, end: {}", startIndex, endIndex);
        for (int i = startIndex; i <= endIndex; i++) {
            final Path path = pathsToIndex.get(i);
            try (final Stream<String> lines = Files.lines(path)) {
                lines.forEach(line -> {
                    String[] words = line.replaceAll("^[.,\\s]+", "").split("[.,\\s]+");
                    Arrays.stream(words).forEach(word -> index.add(word, path.toFile().getName()));
                });
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
