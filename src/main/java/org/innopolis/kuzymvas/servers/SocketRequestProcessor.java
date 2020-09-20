package org.innopolis.kuzymvas.servers;

import org.innopolis.kuzymvas.handlers.HttpRequestHandler;

import java.io.IOException;
import java.net.Socket;

/**
 * Класс  сервера обработчика, выполняющего ответ на один запрос по данному ему сокету.
 */
public class SocketRequestProcessor implements Runnable {

    private final Socket socket;
    private final HttpRequestHandler handler;
    private volatile ProcessorState state;

    /**
     * Создает новый сервер обработчика
     *
     * @param socket  - сокет для соединения с клиентом
     * @param handler - обработчик запроса клиента
     */
    public SocketRequestProcessor(Socket socket, HttpRequestHandler handler) {
        this.socket = socket;
        this.handler = handler;
        state = ProcessorState.NOT_STARTED;
    }

    /**
     * Передает приданному HTTP обработчик потоки ввода и вывода данного серверу сокета
     * Закрывает сокет после завершения обработки или при возникновении ошибки ввода\вывода
     */
    @Override
    public void run() {
        if (state != ProcessorState.NOT_STARTED) {
            return;
        }
        try {
            state = ProcessorState.RUNNING;
            handler.handleConnection(socket.getInputStream(), socket.getOutputStream());
            state = ProcessorState.DONE;
            System.out.println("Done responding. Shutting down responder thread");
        } catch (IOException e) {
            System.out.println("Failed to process request via socket: " + socket
                                       + " IO exception: " + e.getLocalizedMessage());
            state = ProcessorState.FAILED;
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.out.println("Failed to close socket. IO exception: " + e.getLocalizedMessage());
                state = ProcessorState.FAILED;
            }
        }
    }

    public ProcessorState getState() {
        return state;
    }

    /**
     * Состояния сервера: не запущен, выполняется, запрос обработан, ошибка при обработке.
     */
    public enum ProcessorState {
        NOT_STARTED,
        RUNNING,
        DONE,
        FAILED
    }
}
