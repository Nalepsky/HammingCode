package Server;

import java.io.*;
import java.net.Socket;

public class ServerThread {
    private Socket serverSocket;
    private ServerHammingCode hammingCode;
    private String dataIn;
    private String dataOut;
    private int nOfErrors;
    private boolean firstDataFlag;

    public ServerThread(Socket serverSocket) {
        this.serverSocket = serverSocket;
        this.firstDataFlag = true;
    }

    public void run() {
        try {

            BufferedReader input = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
            PrintStream serverRespond = new PrintStream(serverSocket.getOutputStream());
            dataIn = "";

            while (!dataIn.equals("someBad_AsS_Secur3d+Strin8!")) {
                dataIn = input.readLine();

                    System.out.println("Received message: " + dataIn);
                    hammingCode = new ServerHammingCode(dataIn);

                    nOfErrors = hammingCode.correctMessage();
                    dataOut = hammingCode.getMessage();
                    serverRespond.println("Corrected message " + dataOut + " errors: " + nOfErrors);

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
