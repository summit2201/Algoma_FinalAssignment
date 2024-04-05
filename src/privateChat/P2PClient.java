package privateChat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import javax.swing.JTextArea;

public class P2PClient {
    public PrintWriter joinServer(final JTextArea chatTextArea, String ipAddress, int portNumber) throws IOException {
        chatTextArea.append("Connecting!!!");
        Socket clientSocket = null;
        try {
            clientSocket = new Socket(ipAddress, portNumber);
            chatTextArea.append("connected.");
        } catch (ConnectException e) {
            chatTextArea.append("Connection cannot be made.");
            chatTextArea.append("Make sure that server is created. Try again!\n");
        }
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        final BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        new Thread() {
            public void run() {
                String message = null;
                while (true) {
                    try {
                        message = in.readLine();
                        if (message == null) {
                            chatTextArea.append("Connection closed \n");
                            break;
                        }
                        message = "Client: " + message;
                        chatTextArea.append(message + "\n");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
        return out;
    } 
}
