package Server;

import java.io.*;
import java.net.Socket;

public class ServerThread {
    private Socket serverSocket;
    private String dataIn;


    public ServerThread(Socket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public void run() {
        String dataOut;
        int nOfErrors;
        try {

            BufferedReader input = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
            PrintStream serverRespond = new PrintStream(serverSocket.getOutputStream());
            dataIn = "";
            ServerHammingCode hammingCode;
            while (!dataIn.equals("someBad_AsS_Secur3d+Strin8!")) {
                dataIn = input.readLine();
                 hammingCode = new ServerHammingCode(dataIn);

                    System.out.println("Received message: " + dataIn);

                    nOfErrors = hammingCode.findErrorInMessage();
                    dataOut = hammingCode.getMessage();
                    serverRespond.println(dataOut);
                    serverRespond.println(nOfErrors);
            }
            serverRespond.println();

            serverSocket.close();
            System.out.println("thread end");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
