package Server;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Receiver {
    public static void main(String args[]) throws IOException{
        String respond;
        int port = 1410;

        System.out.println("server is running at port " + port);

        ServerSocket serverSocket = null;
        try{
            serverSocket = new ServerSocket(port);

            while(true){
                Socket socket = serverSocket.accept();
                new ServerThread((socket)).start();
            }
        }catch(Exception e){
            System.err.println((e));
        }finally{
            if (serverSocket != null)
                try {
                    serverSocket.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
        }
    }
}
