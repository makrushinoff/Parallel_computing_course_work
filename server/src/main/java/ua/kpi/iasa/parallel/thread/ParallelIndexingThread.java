package ua.kpi.iasa.parallel.thread;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import lombok.RequiredArgsConstructor;
import ua.kpi.iasa.parallel.model.InvertedIndex;

@RequiredArgsConstructor
public class ParallelIndexingThread implements Runnable {

    private final InvertedIndex index;
    private final List<Path> pathsToIndex;
    private final int startIndex;
    private final int endIndex;

    @Override
    public void run() {
        for(int i = startIndex; i <= endIndex; i++) {
            final Path path = pathsToIndex.get(i);
            try(final Stream<String> lines = Files.lines(path)) {
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
