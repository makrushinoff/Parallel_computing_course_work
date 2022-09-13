package ua.kpi.iasa.parallel.client.connector;

import java.net.Socket;
import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ua.kpi.iasa.parallel.client.wrapper.network.SocketWrapper;
import ua.kpi.iasa.parallel.dto.constant.RequestOperation;
import ua.kpi.iasa.parallel.dto.index.IndexFilesRequestDto;
import ua.kpi.iasa.parallel.dto.index.IndexFilesResponseDto;
import ua.kpi.iasa.parallel.dto.texts.GetTextsRequestDto;
import ua.kpi.iasa.parallel.dto.texts.GetTextsResponseDto;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
@PropertySource("classpath:application.properties")
public class IndexingClient {

    @Value("${server.host}")
    private String serverHost;

    @Value("${server.port}")
    private int serverPort;

    public long indexFiles(int threadsNumber) throws Exception {
        log.info("Construct request to index files");
        IndexFilesRequestDto request = new IndexFilesRequestDto();
        request.setRequestOperation(RequestOperation.INDEX_FILES);
        request.setThreadsNumber(threadsNumber);

        try (SocketWrapper socketWrapper = new SocketWrapper(new Socket(serverHost, serverPort))) {
            log.debug(
                    "Send request: {} to server {}:{}", request, socketWrapper.getSocket().getLocalAddress(),
                    socketWrapper.getSocket().getLocalPort()
            );
            socketWrapper.sendData(request);
            log.info("Getting back response from indexing files");
            IndexFilesResponseDto response = socketWrapper.readData(IndexFilesResponseDto.class);
            log.debug("Index files response: {}", response);
            return response.getIndexingTime();
        } catch (Exception e) {
            log.error("Something went wrong when send request", e);
            throw e;
        }
    }

    public List<String> getTextsByWord(String keyword) throws Exception {
        log.info("Construct request to get texts by keyword: {}", keyword);
        GetTextsRequestDto request = new GetTextsRequestDto();
        request.setKeyword(keyword);
        request.setRequestOperation(RequestOperation.GET_TEXTS);

        try (SocketWrapper socketWrapper = new SocketWrapper(new Socket(serverHost, serverPort))) {
            log.debug(
                    "Send request: {} to server {}:{}", request, socketWrapper.getSocket().getLocalAddress(),
                    socketWrapper.getSocket().getLocalPort()
            );
            socketWrapper.sendData(request);
            log.info("Getting back response from getting texts");
            GetTextsResponseDto response = socketWrapper.readData(GetTextsResponseDto.class);
            log.debug("Get texts response: {}", response);

            return response.getFileNames();
        } catch (Exception e) {
            log.error("Something went wrong when send request", e);
            throw e;
        }
    }
}
