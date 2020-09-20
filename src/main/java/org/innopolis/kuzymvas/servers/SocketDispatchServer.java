package org.innopolis.kuzymvas.servers;

import org.innopolis.kuzymvas.handlers.HttpRequestHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketDispatchServer implements  Runnable {

    private ServerSocket socket;
    private HttpRequestHandler handler;

    public SocketDispatchServer(ServerSocket socket, HttpRequestHandler handler) {

        this.socket = socket;
        this.handler = handler;
    }

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