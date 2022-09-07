package ua.kpi.iasa.parallel.wrapper.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

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
        return new SocketWrapper(socket);
    }

    @Override
    public void close() throws Exception {
        serverSocket.close();
    }
}
