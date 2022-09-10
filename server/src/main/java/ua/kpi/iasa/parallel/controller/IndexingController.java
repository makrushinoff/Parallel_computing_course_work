package ua.kpi.iasa.parallel.controller;

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
        final boolean alreadyIndexed = indexingService.isAlreadyIndexed();
        if(alreadyIndexed) {
            throw new IllegalAccessException("Inverted Index is full. Nothing to index");
        }
        return indexingService.indexFiles(threadNumber);
    }

    public void getTextsByWord(String keyword) throws NoSuchMethodException {
        throw new NoSuchMethodException("Not implemented yet");
    }
}
