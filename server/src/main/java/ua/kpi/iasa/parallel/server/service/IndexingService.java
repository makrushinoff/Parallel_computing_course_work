package ua.kpi.iasa.parallel.server.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ua.kpi.iasa.parallel.server.model.InvertedIndex;
import ua.kpi.iasa.parallel.server.thread.ParallelIndexingThread;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class IndexingService {

    @Value("${executorService.waitForTermination.period.seconds}")
    private long waitForTermination;

    private final Path filesDirectoryPath;
    private final InvertedIndex index;

    public synchronized long indexFiles(int threadsNumber) {
        try (final Stream<Path> list = Files.list(filesDirectoryPath)) {
            log.debug("Start to index files");
            long currentTimeMicroseconds = getCurrentTimeMicros();
            List<Path> filePathsToIndex = new ArrayList<>();
            list.forEach(path -> collectPathsToIndex(path, filePathsToIndex));

            ExecutorService executorService = Executors.newFixedThreadPool(threadsNumber);
            getIndexThreads(threadsNumber, filePathsToIndex).forEach(executorService::submit);
            executorService.shutdown();
            if (!executorService.awaitTermination(waitForTermination, TimeUnit.SECONDS)) {
                throw new IllegalStateException("Too little period for waiting in parallel state. Increase period in property file");
            }
            long executionTime = (getCurrentTimeMicros() - currentTimeMicroseconds);
            log.debug("Resulting map with size: {}", index.getSize());
            return executionTime;
        } catch (IOException | InterruptedException | IllegalStateException e) {
            throw new RuntimeException(e);
        }
    }

    private void collectPathsToIndex(Path filePath, List<Path> filePathsToIndex) {
        if (Files.isDirectory(filePath)) {
            Arrays.stream(filePath.toFile().listFiles()).forEach(nestedFile -> collectPathsToIndex(nestedFile.toPath(), filePathsToIndex));
        } else {
            filePathsToIndex.add(filePath);
        }
    }

    private List<Thread> getIndexThreads(int threadsNumber, List<Path> filePathsToIndex) {
        List<Thread> threads = new ArrayList<>();
        int filesPerThread = filePathsToIndex.size() / threadsNumber;
        log.debug("Files per thread: {}, files: {}", filesPerThread, filePathsToIndex.size());
        int filesIndex = 0;
        for (int i = 0; i < threadsNumber; i++) {
            Thread thread;
            if (filesIndex + filesPerThread > filePathsToIndex.size()) {
                thread = new Thread(new ParallelIndexingThread(index, filePathsToIndex, filesIndex, (filePathsToIndex.size() - 1)));
            } else {
                thread = new Thread(new ParallelIndexingThread(index, filePathsToIndex, filesIndex, (filesIndex + filesPerThread - 1)));
            }
            threads.add(thread);
            filesIndex += filesPerThread;
        }
        return threads;
    }

    private long getCurrentTimeMicros() {
        return System.nanoTime() / 1000;
    }

    public boolean isAlreadyIndexed() {
        return index.getSize() > 0;
    }

    public List<String> getFilesFromIndexByKeyword(String keyword) {
        return index.get(keyword);
    }

}
