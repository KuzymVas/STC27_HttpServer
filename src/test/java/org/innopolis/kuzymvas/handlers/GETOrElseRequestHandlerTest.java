package org.innopolis.kuzymvas.handlers;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class GETOrElseRequestHandlerTest {

    private final static String answerGET = " GET answer ";
    private final static String answerOthers = " Other answer ";

    private final static String requestGET = "GET some non important words ";
    private final static String requestPOST = "POST some non important words ";
    private final static String requestNotGET = "Definitely not a GET request";

    HttpRequestHandler handler;
    ByteArrayInputStream inRequestGet;
    ByteArrayInputStream inRequestPost;
    ByteArrayInputStream inRequestNotGet;
    ByteArrayOutputStream out;

    @Before
    public void setUp() {
        handler = new GETOrElseRequestHandler(answerGET, answerOthers);
        inRequestGet = new ByteArrayInputStream(requestGET.getBytes());
        inRequestPost = new ByteArrayInputStream(requestPOST.getBytes());
        inRequestNotGet = new ByteArrayInputStream(requestNotGET.getBytes());
        out = new ByteArrayOutputStream();
    }

    @Test
    public void testHandleGet() throws IOException {
        handler.handleConnection(inRequestGet,out);
        String answer = new String(out.toByteArray());
        Assert.assertEquals("Wrong answer for a GET request", answerGET, answer);
    }

    @Test
    public void testHandleOthers() throws IOException {
        handler.handleConnection(inRequestPost,out);
        String answer = new String(out.toByteArray());
        Assert.assertEquals("Wrong answer for a POST request", answerOthers, answer);
    }

    @Test
    public void testHandleTrickyOthers() throws IOException {
        handler.handleConnection(inRequestNotGet,out);
        String answer = new String(out.toByteArray());
        Assert.assertEquals("Wrong answer for a other request containing word GET in its request line", answerOthers, answer);
    }
}