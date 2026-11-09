package edu.seg2105.edu.server.backend;
// This file contains material supporting section 3.7 of the textbook:

import java.io.IOException;

// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

import com.lloseng.ocsf.server.*;

/**
 * This class overrides some of the methods in the abstract
 * superclass in order to give more functionality to the server.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;re
 * @author Fran&ccedil;ois B&eacute;langer
 * @author Paul Holden
 */
public class EchoServer extends AbstractServer {
  // Class variables *************************************************

  /**
   * The default port to listen on.
   */
  final public static int DEFAULT_PORT = 5555;

  // Constructors ****************************************************

  /**
   * Constructs an instance of the echo server.
   *
   * @param port The port number to connect on.
   */
  public EchoServer(int port) {
    super(port);
  }

  // Instance methods ************************************************

  /**
   * This method handles any messages received from the client.
   *
   * @param msg    The message received from the client.
   * @param client The connection from which the message originated.
   */
  public void handleMessageFromClient(Object msg, ConnectionToClient client) {
    String message = msg.toString();
    String loginID = (String) client.getInfo("loginID");

    if (loginID == null) {
      if (message.startsWith("#login ")) {
        loginID = message.substring(7);
        client.setInfo("loginID", loginID);
        System.out.println("Message received: " + message + " from null.");
        System.out.println(loginID + " has logged on.");
        sendToAllClients(loginID + " has logged on.");
      } else {
        try {
          client.sendToClient("SERVER MSG> ERROR - First message must be #login");
          client.close();
        } catch (IOException e) {}
      }
    } else {
      if (message.startsWith("#login ")) {
        try {
          client.sendToClient("SERVER MSG> ERROR - You are already logged in.");
          client.close();
        } catch (IOException e) {}
      } else {
        System.out.println("Message received: " + message + " from " + loginID);
        this.sendToAllClients(loginID + "> " + message);
      }
    }
  }

  public void handleMessageFromServerUI(String message) {
    if (message.startsWith("#")) {
      if (message.equals("#quit")) {
        try { close(); } catch (IOException e) {}
        System.exit(0);
      } else if (message.equals("#stop")) {
        stopListening();
      } else if (message.equals("#close")) {
        try { close(); } catch (IOException e) {}
      } else if (message.startsWith("#setport ")) {
        if (!isClosed() && isListening()) System.out.println("Cannot change port while server is active.");
        else setPort(Integer.parseInt(message.substring(9)));
      } else if (message.equals("#start")) {
        if (!isListening()) {
          try { listen(); } catch (Exception ex) { System.out.println("Error listening for clients!"); }
        }
      } else if (message.equals("#getport")) {
        System.out.println("Current port: " + getPort());
      }
    } else {
      String formattedMsg = "SERVER MESSAGE> " + message;
      System.out.println(formattedMsg);
      sendToAllClients(formattedMsg);
    }
  }

  @Override
  protected void clientConnected(ConnectionToClient client) {
    System.out.println("A new client has connected to the server.");
  }

  @Override
  synchronized protected void clientDisconnected(ConnectionToClient client) {
    String loginID = (String) client.getInfo("loginID");
    if (loginID != null) System.out.println(loginID + " has disconnected.");
  }

  @Override
  synchronized protected void clientException(ConnectionToClient client, Throwable exception) {
    clientDisconnected(client);
  }

  /**
   * This method overrides the one in the superclass. Called
   * when the server starts listening for connections.
   */
  protected void serverStarted() {
    System.out.println("Server listening for connections on port " + getPort());
  }

  /**
   * This method overrides the one in the superclass. Called
   * when the server stops listening for connections.
   */
  protected void serverStopped() {
    System.out.println("Server has stopped listening for connections.");
  }
}
// End of EchoServer class
