package ua.kpi.iasa.parallel.client.wrapper.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import lombok.Data;

@Data
@SuppressWarnings("unchecked")
public class SocketWrapper implements AutoCloseable {

    private final Socket socket;
    private final ObjectInputStream inputStream;
    private final ObjectOutputStream outputStream;

    public SocketWrapper(Socket socket) {
        this.socket = socket;
        try {
            socket.setSoTimeout(10000);
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T readData(Class<T> clazz) {
        try {
            return ((T) inputStream.readObject());
        } catch (IOException | ClassNotFoundException | ClassCastException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendData(Object data) {
        try {
            outputStream.writeObject(data);
            outputStream.reset();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() throws Exception {
        inputStream.close();
        outputStream.close();
        socket.close();
    }
}
