package org.innopolis.kuzymvas;

import org.innopolis.kuzymvas.handlers.GETOrElseRequestHandler;
import org.innopolis.kuzymvas.handlers.HttpRequestHandler;
import org.innopolis.kuzymvas.http.HTTPResponseHeadersGenerator;
import org.innopolis.kuzymvas.servers.SocketDispatchServer;
import org.innopolis.kuzymvas.stringtrees.FileTreeBuilder;
import org.innopolis.kuzymvas.stringtrees.StringTreeNode;
import org.innopolis.kuzymvas.stringtrees.TreeToHTMLConverter;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;

public class Main {

    private  final  static  int PORT_NUMBER = 7777;

    public static void main(String[] args) {

        StringTreeNode fileTree = FileTreeBuilder.getFileTree(new File(System.getProperty("user.dir")));
        TreeToHTMLConverter converter = new TreeToHTMLConverter(2);
        String responseGetHTML =  "<html>\n<body>\n" + converter.convertTreeToHTML(fileTree) + "<body>\n<html>\n";
        String responseGet = HTTPResponseHeadersGenerator.addOKHeaderToHTML(responseGetHTML);

        String responseOther = HTTPResponseHeadersGenerator.getStatusLine(
                HTTPResponseHeadersGenerator.HTTPCode.NOT_FOUND_404);

        HttpRequestHandler handler = new GETOrElseRequestHandler(responseGet, responseOther);
        SocketDispatchServer server;
        try {
            server = new SocketDispatchServer(new ServerSocket(PORT_NUMBER), handler);
            Thread serverThread = new Thread(server);
            serverThread.setDaemon(true);
            System.out.println("Starting HTTP server on port " + PORT_NUMBER);
            serverThread.start();
            System.out.println("Press any key to shut down server");
            System.in.read();
        } catch (IOException e) {
            System.out.println("Failed to setup server, IO exception: " + e.getLocalizedMessage());
        }

    }
}
