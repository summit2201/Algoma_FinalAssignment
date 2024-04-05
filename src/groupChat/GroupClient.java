package groupChat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import javax.swing.JTextArea;

public class GroupClient {
    public PrintWriter joinServer(final JTextArea chatTextArea, String ipAddress, int portNumber) throws IOException {
        chatTextArea.append("Connecting!!!\n");
        Socket clientSocket = null;
        try {
            clientSocket = new Socket(ipAddress, portNumber);
            chatTextArea.append("connected.\n");
        }
        catch(ConnectException e) {
            chatTextArea.append("Connection cannot be made.\n");
            chatTextArea.append("Make sure that server is made. Try again!\n");
        }
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        final BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        new Thread() {
            @Override
            public void run() {
                String message = null;
                while (true) {
                    try {
                        message = in.readLine();
                        if (message == null) {
                            chatTextArea.append("Connection closed \n");
                            break;
                        }
                        
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
