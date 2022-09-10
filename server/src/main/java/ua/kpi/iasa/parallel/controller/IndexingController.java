package ua.kpi.iasa.parallel.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ua.kpi.iasa.parallel.service.IndexingService;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class IndexingController {

    private final IndexingService indexingService;

    public long indexFiles(int threadNumber) throws IllegalAccessException {
        log.debug("Attempt to index files");
        boolean alreadyIndexed = indexingService.isAlreadyIndexed();
        if (alreadyIndexed) {
            throw new IllegalAccessException("Inverted Index is full. Nothing to index");
        }
        long executionTime = indexingService.indexFiles(threadNumber);
        log.info("Time for indexing: {} mcs", executionTime);
        return executionTime;
    }

    public List<String> getTextsByWord(String keyword) throws IllegalAccessException {
        log.debug("Attempt to get texts from index be keyword: {}", keyword);
        boolean alreadyIndexed = indexingService.isAlreadyIndexed();
        if (!alreadyIndexed) {
            throw new IllegalAccessException("Inverted Index is empty. Index files first!");
        }
        List<String> filesFromIndexByKeyword = indexingService.getFilesFromIndexByKeyword(keyword);
        log.debug("Keyword: {}, files: {}", keyword, filesFromIndexByKeyword);
        return filesFromIndexByKeyword;
    }
}
