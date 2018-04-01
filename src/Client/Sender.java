package Client;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class Sender {
    public static void main(String[] args){
        String inputData,
                respond,
                newLine;
        int port = 1410;
        Socket socket;
        Scanner scanner;

        try {
            scanner = new Scanner(System.in);
            socket = new Socket("127.0.0.1", port);
            Scanner serverScanner = new Scanner(socket.getInputStream());
            PrintStream serverPrint = new PrintStream(socket.getOutputStream());

            System.out.println("Sending message 1010");


            //inputData = scanner.nextLine();
            inputData = "1010";

            serverPrint.println(inputData);

            System.out.println(serverScanner.nextLine());

            scanner.close();
            socket.close();
        }catch (IOException e){
            System.err.println(e);
        }
    }
}
