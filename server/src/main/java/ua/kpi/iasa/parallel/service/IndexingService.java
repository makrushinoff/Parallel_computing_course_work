package ua.kpi.iasa.parallel.service;

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
import ua.kpi.iasa.parallel.model.InvertedIndex;
import ua.kpi.iasa.parallel.thread.ParallelIndexingThread;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class IndexingService {

    @Value("${executorService.waitForTermination.period.seconds}")
    private long waitForTermination;

    public final Path filesDirectoryPath;
    public final InvertedIndex index;

    public void indexFiles(int threadsNumber) {
        try (final Stream<Path> list = Files.list(filesDirectoryPath)) {
            log.info("Start to index files");
            long currentTimeMicroseconds = getCurrentTimeMicros();
            List<Path> filePathsToIndex = new ArrayList<>();
            list.forEach(path -> collectPathsToIndex(path, filePathsToIndex));
            ExecutorService executorService = Executors.newFixedThreadPool(threadsNumber);
            getIndexThreads(threadsNumber, filePathsToIndex).forEach(executorService::submit);
            executorService.shutdown();
            if(!executorService.awaitTermination(waitForTermination, TimeUnit.SECONDS)) {
                throw new IllegalStateException("Too little period for waiting in parallel state. Increase period in property file");
            }
            log.info("Time for indexing: {} mcs", (getCurrentTimeMicros() - currentTimeMicroseconds));
            log.info("Resulting map with size: {}", index.getSize());
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
        for(int i = 0; i < filePathsToIndex.size(); i += filesPerThread) {
            Thread thread;
            if(i + filesPerThread > filePathsToIndex.size()) {
                thread = new Thread(new ParallelIndexingThread(index, filePathsToIndex, i, (filePathsToIndex.size() - 1)));
            } else {
                thread = new Thread(new ParallelIndexingThread(index, filePathsToIndex, i, (i + filesPerThread)));
            }
            threads.add(thread);
        }
        return threads;
    }

    private long getCurrentTimeMicros() {
        return System.nanoTime() / 1000;
    }

}
