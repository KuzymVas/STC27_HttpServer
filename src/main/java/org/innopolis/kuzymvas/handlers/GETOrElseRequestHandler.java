package org.innopolis.kuzymvas.handlers;

import java.io.*;

public class GETOrElseRequestHandler implements  HttpRequestHandler {

    private final String answerGET;
    private final String answerOthers;

    public GETOrElseRequestHandler(String answerGET, String answerOthers) {
        this.answerGET = answerGET;
        this.answerOthers = answerOthers;
    }


    @Override
    public void handleConnection(InputStream in, OutputStream out) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out));
        String requestLine = br.readLine();
        if (requestLine.startsWith("GET")) {
            bw.write(answerGET);
        } else {
            bw.write(answerOthers);
        }
        bw.flush();
    }
}
