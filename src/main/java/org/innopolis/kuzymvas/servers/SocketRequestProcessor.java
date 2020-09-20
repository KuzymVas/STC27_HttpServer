package org.innopolis.kuzymvas.servers;

import org.innopolis.kuzymvas.handlers.HttpRequestHandler;

import java.io.IOException;
import java.net.Socket;

public class SocketRequestProcessor implements  Runnable {

    private final Socket socket;
    private final HttpRequestHandler handler;
    private volatile ProcessorState state;

    public SocketRequestProcessor(Socket socket, HttpRequestHandler handler){
        this.socket = socket;
        this.handler = handler;
        state = ProcessorState.NOT_STARTED;
    }

    @Override
    public void run() {
        if (state != ProcessorState.NOT_STARTED) {
            return;
        }
        try {
            state = ProcessorState.RUNNING;
            handler.handleConnection(socket.getInputStream(), socket.getOutputStream());
            state = ProcessorState.DONE;
        } catch (IOException e) {
            System.out.println("Failed to process request via socket: " + socket
                                       + " IO exception: " + e.getLocalizedMessage());
            state = ProcessorState.FAILED;
        }
    }

    public ProcessorState getState() {
        return state;
    }

    public enum ProcessorState {
        NOT_STARTED,
        RUNNING,
        DONE,
        FAILED
    }
}
