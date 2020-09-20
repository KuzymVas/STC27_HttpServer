package org.innopolis.kuzymvas.servers;

import org.innopolis.kuzymvas.handlers.HttpRequestHandler;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class SocketRequestProcessorTest {

    Socket mockSocket;
    InputStream mockIn;
    OutputStream mockOut;
    HttpRequestHandler mockHandler;

    @Before
    public void setUp() throws IOException {
        mockSocket = Mockito.mock(Socket.class);
        Mockito.when(mockSocket.getInputStream()).thenReturn(mockIn);
        Mockito.when(mockSocket.getOutputStream()).thenReturn(mockOut);
        mockHandler = Mockito.mock(HttpRequestHandler.class);
    }

    @Test
    public void testNormalFlow() throws IOException {
        SocketRequestProcessor processor = new SocketRequestProcessor(mockSocket, mockHandler);
        processor.run();
        Mockito.verify(mockHandler, Mockito.times(1)).handleConnection(mockIn, mockOut);
        Mockito.verify(mockSocket, Mockito.times(1)).close();
        Assert.assertEquals("Processor completed the run, but failed to set its state to 'done'.",
                            SocketRequestProcessor.ProcessorState.DONE, processor.getState());
    }

    @Test
    public void testSocketFailures() throws IOException {
        Socket mockSocketOutFailure = Mockito.mock(Socket.class);
        Mockito.when(mockSocketOutFailure.getInputStream()).thenReturn(mockIn);
        Mockito.when(mockSocketOutFailure.getOutputStream()).thenThrow(new IOException());
        SocketRequestProcessor processorForOutFailure = new SocketRequestProcessor(mockSocketOutFailure, mockHandler);
        processorForOutFailure.run();
        Mockito.verify(mockHandler, Mockito.times(0)).handleConnection(mockIn, mockOut);
        Mockito.verify(mockSocketOutFailure, Mockito.times(1)).close();
        Assert.assertEquals("Run was aborted, but processor failed to set its state to 'Failed'.",
                            SocketRequestProcessor.ProcessorState.FAILED, processorForOutFailure.getState());

        Socket mockSocketInFailure = Mockito.mock(Socket.class);
        Mockito.when(mockSocketInFailure.getInputStream()).thenThrow(new IOException());
        Mockito.when(mockSocketInFailure.getOutputStream()).thenReturn(mockOut);
        SocketRequestProcessor processorForInFailure = new SocketRequestProcessor(mockSocketInFailure, mockHandler);
        processorForInFailure.run();
        Mockito.verify(mockHandler, Mockito.times(0)).handleConnection(mockIn, mockOut);
        Mockito.verify(mockSocketInFailure, Mockito.times(1)).close();
        Assert.assertEquals("Run was aborted, but processor failed to set its state to 'Failed'.",
                            SocketRequestProcessor.ProcessorState.FAILED, processorForInFailure.getState());
    }

    @Test
    public void testNegativeFlow() throws IOException {
        Mockito.doThrow(new IOException("Mock exception")).when(mockHandler).handleConnection(mockIn, mockOut);
        SocketRequestProcessor processor = new SocketRequestProcessor(mockSocket, mockHandler);
        processor.run();
        Mockito.verify(mockHandler, Mockito.times(1)).handleConnection(mockIn, mockOut);
        Mockito.verify(mockSocket, Mockito.times(1)).close();
        Assert.assertEquals("Run was aborted, but processor failed to set its state to 'Failed'.",
                            SocketRequestProcessor.ProcessorState.FAILED, processor.getState());
    }

    @Test
    public void testRepeatedRuns() throws IOException {
        SocketRequestProcessor processor = new SocketRequestProcessor(mockSocket, mockHandler);
        processor.run();
        Mockito.verify(mockHandler, Mockito.times(1)).handleConnection(mockIn, mockOut);
        Mockito.verify(mockSocket, Mockito.times(1)).close();
        Assert.assertEquals("Processor completed the run, but failed to set its state to 'done'.",
                            SocketRequestProcessor.ProcessorState.DONE, processor.getState());
        Mockito.clearInvocations(mockHandler);
        Mockito.clearInvocations(mockSocket);
        processor.run();
        Mockito.verify(mockHandler, Mockito.times(0)).handleConnection(mockIn, mockOut);
        Mockito.verify(mockSocket, Mockito.times(0)).close();
    }

    @Test
    public void testSlowRun() throws IOException, InterruptedException {
        Mockito.doAnswer(invocationOnMock -> {
            Thread.sleep(1000);
            return null;
        }).when(mockHandler).handleConnection(mockIn, mockOut);
        SocketRequestProcessor processor = new SocketRequestProcessor(mockSocket, mockHandler);
        Thread thread = new Thread(processor);
        thread.start();
        Thread.sleep(100);
        Assert.assertEquals("Processor didn't set its state to 'Running' after run started.",
                            SocketRequestProcessor.ProcessorState.RUNNING, processor.getState());
        thread.join();
        Assert.assertEquals("Processor completed the run, but failed to set its state to 'done'.",
                            SocketRequestProcessor.ProcessorState.DONE, processor.getState());
    }
}