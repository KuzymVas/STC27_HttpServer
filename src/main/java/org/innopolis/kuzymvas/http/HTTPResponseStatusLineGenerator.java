package org.innopolis.kuzymvas.http;

public class HTTPResponseStatusLineGenerator {

    public static String getStatusLine(HTTPCode code) {
        StringBuilder builder = new StringBuilder();
        builder.append("HTTP/1.1 ");
        switch (code) {
            case OK_200: {
                builder.append("200 OK");
                break;
            }
            case NOT_FOUND_404:
                builder.append("404 Not Found");
        }
        builder.append("\r\n");
        return builder.toString();
    }


    public enum HTTPCode {
        OK_200,
        NOT_FOUND_404
    }
}
