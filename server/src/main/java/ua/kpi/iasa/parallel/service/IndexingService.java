package ua.kpi.iasa.parallel.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.stream.Stream;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ua.kpi.iasa.parallel.model.InvertedIndex;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class IndexingService {

    public final Path filesDirectoryPath;
    public final InvertedIndex index;

    public void indexFiles() {
        try (final Stream<Path> list = Files.list(filesDirectoryPath)) {
            log.info("Start to index files");
            list.forEach(this::indexFile);
            log.info("Resulting map with size: {}", index.getSize());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void indexFile(Path filePath) {
        if (Files.isDirectory(filePath)) {
            Arrays.stream(filePath.toFile().listFiles()).forEach(nestedFile -> indexFile(nestedFile.toPath()));
        } else {
            try (Stream<String> lines = Files.lines(filePath)) {
                lines.forEach(line -> {
                    String[] words = line.replaceAll("^[.,\\s]+", "").split("[.,\\s]+");
                    Arrays.stream(words).forEach(word -> index.add(word, filePath.toFile().getName()));
                });
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
