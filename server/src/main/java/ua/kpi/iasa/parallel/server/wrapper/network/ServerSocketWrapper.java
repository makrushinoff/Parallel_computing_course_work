package ua.kpi.iasa.parallel.server.wrapper.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class ServerSocketWrapper implements AutoCloseable {

    private final ServerSocket serverSocket;

    public ServerSocketWrapper(Integer port) {
        try {
            this.serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public SocketWrapper createConnection() {
        Socket socket;
        try {
            socket = serverSocket.accept();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        log.debug("Receive connection from {}:{}", socket.getInetAddress(), socket.getPort());
        return new SocketWrapper(socket);
    }

    @Override
    public void close() throws Exception {
        serverSocket.close();
    }
}
