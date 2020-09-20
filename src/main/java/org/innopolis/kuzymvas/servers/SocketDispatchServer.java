package org.innopolis.kuzymvas.servers;

import org.innopolis.kuzymvas.handlers.HttpRequestHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Класс управляющего сервера, слушающего сокет и создающего новые потоки для обработки входящих подключений к сокету.
 */
public class SocketDispatchServer implements  Runnable {

    private final ServerSocket socket;
    private final HttpRequestHandler handler;

    /**
     * Создает новый управляющий сервер
     * @param socket - сокет для прослушки
     * @param handler - обработчик для входящий подключений
     */
    public SocketDispatchServer(ServerSocket socket, HttpRequestHandler handler) {

        this.socket = socket;
        this.handler = handler;
    }

    /**
     *  Запускает бесконечный цикл прослушки сокета. Каждому входящему подключению выделяет новый поток-демон,
     *  выполняющий его обработку.
     */
    @Override
    public void run() {
        try {
            while (true) {
                Socket requestSocket = socket.accept();
                System.out.println("Connection accepted. Starting new thread to respond");
                SocketRequestProcessor processor = new SocketRequestProcessor(requestSocket, handler);
                Thread processorThread = new Thread(processor);
                processorThread.setDaemon(true);
                processorThread.start();
            }
        } catch (IOException e) {
            System.out.println("Server dispatcher thread failed to accept connection. IO error: " + e.getLocalizedMessage());
            System.out.println("Shutting down...");
        }
    }
}