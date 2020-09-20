package org.innopolis.kuzymvas.http;

/**
 * Генерирует заголовки HTTP для ответов сервера
 */
public class HTTPResponseHeadersGenerator {

    /**
     * Добавляет к данному html документу корреткные заголовки для ответа с кодом 200, ОК
     *
     * @param html - строка с телом ответа
     * @return - полный текст HTTP ответа, включая заголовки и тело
     */
    public static String addOKHeaderToHTML(String html) {
        return getStatusLine(HTTPCode.OK_200) +
                getContentTypeHeader() +
                getContentLengthHeader(html.length()) +
                getConnectionCloseHeader() +
                "\r\n" +
                html;
    }

    /**
     * Возвращает строку статуса, соответствующую запрашиваемому коду
     *
     * @param code - код статуса для формируемой строки
     * @return - валидная строка статутса для HTTP 1.1
     */
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

    /**
     * Возвращает заголовок типа контента. В текущей реализации всегда возвращает тип text/html
     *
     * @return - строка заголовка Content-Type
     */
    public static String getContentTypeHeader() {
        return "Content-Type: text/html\r\n";
    }

    /**
     * Возвращает заголовок длины контента
     *
     * @param length - длина контента
     * @return - строка заголовка Content-Length
     */
    public static String getContentLengthHeader(int length) {
        return "Content-Length: " + length + "\r\n";
    }

    /**
     * Возвращает заголовок, указывающий клиенту закрыть соединение
     *
     * @return - строка заголовока  Connection: close
     */
    public static String getConnectionCloseHeader() {
        return "Connection: close\r\n";
    }

    public enum HTTPCode {
        OK_200,
        NOT_FOUND_404
    }
}
