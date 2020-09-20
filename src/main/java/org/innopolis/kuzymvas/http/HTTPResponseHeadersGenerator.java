package org.innopolis.kuzymvas.http;

public class HTTPResponseHeadersGenerator {


    public static String addOKHeaderToHTML(String html) {
        return getStatusLine(HTTPCode.OK_200) +
                getContentTypeHeader() +
                getContentLengthHeader(html.length()) +
                getConnectionCloseHeader() +
                "\r\n" +
                html;
    }


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
        builder.append("Content-type: application/json\r\n");
        return builder.toString();
    }

    public static  String getContentTypeHeader(){
        return "Content-Type: text/html\r\n";
    }

    public static String getContentLengthHeader(int length) {
        return "Content-Length: " + length + "\r\n";
    }

    public  static String getConnectionCloseHeader() {
        return  "Connection: close\r\n";
    }

    public enum HTTPCode {
        OK_200,
        NOT_FOUND_404
    }
}
