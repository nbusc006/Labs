// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

package edu.seg2105.client.backend;

import com.lloseng.ocsf.client.*;

import java.io.*;

import edu.seg2105.client.common.*;

/**
 * This class overrides some of the methods defined in the abstract
 * superclass in order to give more functionality to the client.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;
 * @author Fran&ccedil;ois B&eacute;langer
 */
public class ChatClient extends AbstractClient {
  // Instance variables **********************************************

  /**
   * The interface type variable. It allows the implementation of
   * the display method in the client.
   */
  ChatIF clientUI;

  // Constructors ****************************************************

  /**
   * Constructs an instance of the chat client.
   *
   * @param host     The server to connect to.
   * @param port     The port number to connect on.
   * @param clientUI The interface type variable.
   */

  String loginID;

  public ChatClient(String loginID, String host, int port, ChatIF clientUI) throws IOException {
    super(host, port); // Call the superclass constructor
    this.loginID = loginID;
    this.clientUI = clientUI;
    openConnection();
  }

  // Instance methods ************************************************
  @Override
  protected void connectionClosed() {
    clientUI.display("Connection closed.");
  }

  @Override
  protected void connectionException(Exception exception) {
    clientUI.display("The server has shut down.");
    System.exit(0);
  }

  @Override
  protected void connectionEstablished() {
    try {
      sendToServer("#login " + loginID);
    } catch (IOException e) {
      clientUI.display("Error sending login info.");
    }
  }
  /**
   * This method handles all data that comes in from the server.
   *
   * @param msg The message from the server.
   */
  public void handleMessageFromServer(Object msg) {
    clientUI.display(msg.toString());
  }

  /**
   * This method handles all data coming from the UI
   *
   * @param message The message from the UI.
   */
  public void handleMessageFromClientUI(String message) {
    if (message.startsWith("#")) {
      handleCommand(message);
    } else {
      try {
        sendToServer(message);
      } catch (IOException e) {
        clientUI.display("Could not send message to server. Terminating client.");
        quit();
      }
    }
  }

  private void handleCommand(String command) {
    if (command.equals("#quit")) {
      quit();
    } else if (command.equals("#logoff")) {
      try { closeConnection(); } catch (IOException e) {}
    } else if (command.startsWith("#sethost ")) {
      if (isConnected()) clientUI.display("Cannot change host while connected.");
      else setHost(command.substring(9));
    } else if (command.startsWith("#setport ")) {
      if (isConnected()) clientUI.display("Cannot change port while connected.");
      else setPort(Integer.parseInt(command.substring(9)));
    } else if (command.equals("#login")) {
      if (isConnected()) clientUI.display("Already connected.");
      else {
        try { openConnection(); } 
        catch (IOException e) { clientUI.display("ERROR - Can't setup connection! Terminating client."); quit(); }
      }
    } else if (command.equals("#gethost")) {
      clientUI.display("Current host: " + getHost());
    } else if (command.equals("#getport")) {
      clientUI.display("Current port: " + getPort());
    } else {
      clientUI.display("Invalid command.");
    }
  }

  /**
   * This method terminates the client.
   */
  public void quit() {
    try {
      closeConnection();
    } catch (IOException e) {
    }
    System.exit(0);
  }
}
// End of ChatClient class
