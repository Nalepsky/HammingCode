package Client;

import javax.swing.*;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class Sender {
    public static void main(String[] args) {
        String inputData,
                respond,
                newLine;
        int port = 1410;
        Socket socket;

        Window window = null;

        try {
            socket = new Socket("127.0.0.1", port);

            window = new Window(socket);
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            window.setVisible(true);
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}
