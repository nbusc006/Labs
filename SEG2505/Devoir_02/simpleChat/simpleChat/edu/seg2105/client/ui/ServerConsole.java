package edu.seg2105.client.ui;

import java.io.*;
import java.util.Scanner;
import edu.seg2105.client.common.ChatIF;
import edu.seg2105.edu.server.backend.EchoServer;

public class ServerConsole implements ChatIF {
    EchoServer server;
    Scanner fromConsole;

    public ServerConsole(int port) {
        server = new EchoServer(port);
        try {
            server.listen();
        } catch (Exception ex) {
            System.out.println("ERROR - Could not listen for clients!");
        }
        fromConsole = new Scanner(System.in);
    }

    public void accept() {
        try {
            String message;
            while (true) {
                message = fromConsole.nextLine();
                server.handleMessageFromServerUI(message);
            }
        } catch (Exception ex) {
            System.out.println("Unexpected error while reading from console!");
        }
    }

    public void display(String message) {
        System.out.println(message);
    }

    public static void main(String[] args) {
        int port = 0;
        try {
            port = Integer.parseInt(args[0]);
        } catch (Throwable t) {
            port = EchoServer.DEFAULT_PORT;
        }
        ServerConsole console = new ServerConsole(port);
        console.accept();
    }
}