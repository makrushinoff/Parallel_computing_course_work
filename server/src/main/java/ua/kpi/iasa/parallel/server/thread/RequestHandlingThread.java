package ua.kpi.iasa.parallel.server.thread;

import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ua.kpi.iasa.parallel.dto.RequestDto;
import ua.kpi.iasa.parallel.dto.ResponseDto;
import ua.kpi.iasa.parallel.dto.index.IndexFilesRequestDto;
import ua.kpi.iasa.parallel.dto.index.IndexFilesResponseDto;
import ua.kpi.iasa.parallel.dto.texts.GetTextsRequestDto;
import ua.kpi.iasa.parallel.dto.texts.GetTextsResponseDto;
import ua.kpi.iasa.parallel.server.controller.IndexingController;
import ua.kpi.iasa.parallel.server.wrapper.network.SocketWrapper;

@RequiredArgsConstructor
@Slf4j
public class RequestHandlingThread implements Runnable {

    private final IndexingController indexingController;
    private final SocketWrapper socketWrapper;

    @Override
    public void run() {
        log.debug("Receive task to handle : {}", socketWrapper);
        RequestDto requestDto = socketWrapper.readData(RequestDto.class);
        log.debug("Read data: {}", requestDto);
        ResponseDto response = null;
        if(requestDto instanceof IndexFilesRequestDto request) {
            response = handleIndexFiles(request);
        } else if (requestDto instanceof GetTextsRequestDto request) {
            response = handleGetTexts(request);
        }
        socketWrapper.sendData(response); //will never be null
        try {
            socketWrapper.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private ResponseDto handleIndexFiles(IndexFilesRequestDto request) {
        log.info("Receive request to index files with {} threads", request.getThreadsNumber());
        IndexFilesResponseDto response = new IndexFilesResponseDto();
        try {
            long executionTime = indexingController.indexFiles(request.getThreadsNumber());
            response.setIndexingTime(executionTime);
            response.setSuccessful(true);
            socketWrapper.sendData(response);
            log.info("Successfully handled request to index files");
        } catch (IllegalAccessException e) {
            log.error("Error when indexing files", e);
            response.setSuccessful(false);
        }
        return response;
    }

    private ResponseDto handleGetTexts(GetTextsRequestDto request) {
        log.info("Receive request to get texts by keyword : '{}'", request.getKeyword());
        GetTextsResponseDto response = new GetTextsResponseDto();
        try {
            List<String> textsByWord = indexingController.getTextsByWord(request.getKeyword());
            response.setFileNames(textsByWord);
            response.setSuccessful(true);
            socketWrapper.sendData(response);
            log.info("Successfully handled request to index files");
        } catch (Exception e) {
            log.error("Error when indexing files", e);
            response.setSuccessful(false);
        }
        return response;
    }
}
