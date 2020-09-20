package org.innopolis.kuzymvas.handlers;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Интерфейс для обработчика запроса к HTTP серверу
 */
public interface HttpRequestHandler {

    /**
     * Обрабатывает запрос из входного потока и пишет ответ в выходной поток
     *
     * @param in  - входной поток, содержащий запрос
     * @param out - выходной поток для записи ответа.
     * @throws IOException - при ошибках в работе потоков
     */
    void handleConnection(InputStream in, OutputStream out) throws IOException;
}
