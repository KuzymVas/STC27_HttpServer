package org.innopolis.kuzymvas.handlers;

import java.io.*;

/**
 * Обработчик запроса, реализующий логику выдачи одного статического ответа на все запросы GET
 * и другого на прочие запросы. Не проверяет валидность запроса стандартам HTTP, а лишь наличие метода
 * в первой строке запроса.
 */
public class GETOrElseRequestHandler implements HttpRequestHandler {

    private final String answerGET;
    private final String answerOthers;

    /**
     * Создает новый обработчик
     *
     * @param answerGET    - HTTP ответ на запросы типа GET
     * @param answerOthers - HTTP ответ на прочие запросы
     */
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
