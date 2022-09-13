package ua.kpi.iasa.parallel.server.runner;

import java.util.concurrent.ExecutorService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ua.kpi.iasa.parallel.server.controller.IndexingController;
import ua.kpi.iasa.parallel.server.thread.RequestHandlingThread;
import ua.kpi.iasa.parallel.server.wrapper.network.ServerSocketWrapper;
import ua.kpi.iasa.parallel.server.wrapper.network.SocketWrapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component("socket")
@Primary
@Slf4j
@RequiredArgsConstructor
public class SocketApplicationRunner implements ApplicationRunner {

    @Value("${server.port}")
    private int serverPort;

    private final ExecutorService executorService;
    private final IndexingController indexingController;

    @Override
    public void runApp() {
        try (ServerSocketWrapper serverSocketWrapper = new ServerSocketWrapper(serverPort)) {
            log.debug(
                    "Server started on host: {}, port: {}", serverSocketWrapper.getServerSocket().getInetAddress(),
                    serverSocketWrapper.getServerSocket().getLocalPort()
            );
            while (true) {
                SocketWrapper socketWrapper = serverSocketWrapper.createConnection();
                executorService.execute(new RequestHandlingThread(indexingController, socketWrapper));
            }
        } catch (Exception e) {
            log.error("Error while server runs", e);
        }
    }
}
