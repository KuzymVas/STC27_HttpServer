package org.innopolis.kuzymvas.handlers;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface HttpRequestHandler {

    void handleConnection(InputStream in, OutputStream out) throws IOException;

}
