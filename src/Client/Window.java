package Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.SocketException;

public class Window extends JFrame implements ActionListener {
    Socket socket;
    BufferedReader input;
    PrintStream serverPrint;
    JButton bSendCorrect;
    JButton bSendOneError;
    JButton bSendTwoErrors;
    JButton bExit;
    JLabel receiveShow;
    JLabel sentShow;
    JTextField tShow;


    public Window(Socket socket) throws IOException {
        this.socket = socket;
        this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        serverPrint = new PrintStream(socket.getOutputStream());

        setSize(500, 280);
        setTitle("Hamming Code");
        setLayout(null);

        tShow = new JTextField();
        tShow.setBounds(70, 20, 360, 20);
        tShow.setToolTipText("Enter text to check");
        add(tShow);

        sentShow = new JLabel("Number sent: ");
        sentShow.setBounds(70, 50, 360, 20);
        sentShow.setForeground(Color.BLUE);
        sentShow.setFont(new Font("SansSerif", Font.BOLD, 10));
        add(sentShow);

        receiveShow = new JLabel("Number received: ");
        receiveShow.setBounds(70, 80, 360, 20);
        receiveShow.setForeground(Color.BLUE);
        receiveShow.setFont(new Font("SansSerif", Font.BOLD, 10));
        add(receiveShow);

        bSendCorrect = new JButton("Send correctly");
        bSendCorrect.setBounds(70,110,360,20);
        add(bSendCorrect);
        bSendCorrect.addActionListener(this);

        bSendOneError = new JButton("Send 1 error");
        bSendOneError.setBounds(70,140,360,20);
        add(bSendOneError);
        bSendOneError.addActionListener(this);

        bSendTwoErrors = new JButton("Send 2 errors");
        bSendTwoErrors.setBounds(70,170,360,20);
        add(bSendTwoErrors);
        bSendTwoErrors.addActionListener(this);

        bExit = new JButton("Exit");
        bExit.setBounds(70,200, 360,20);
        add(bExit);
        bExit.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        HammingCodeCreator hammingCodeCreator = new HammingCodeCreator("");

        Object source = e.getSource();

        if(source== bSendCorrect) {

            String message = tShow.getText();
            char[] bits = message.toCharArray();

            serverPrint.println(hammingCodeCreator.createHammingCode(message));
            tShow.setText("");
            try {
                //sentShow.setText("Number sent: " + convertToDecimal(bits));
                sentShow.setText("Number sent: " + hammingCodeCreator.createHammingCode(message));
                receiveShow.setText("Number received: " + input.readLine());
            }catch (SocketException se){
                se.printStackTrace();
            }catch (IOException e1) {
                e1.printStackTrace();
            }

        }
        //TODO: dodać pojedyncze przekłamanie bitów
        //TODO: dodać zmiane stringa na bity i na odwrót
        else if(source == bSendOneError){
            serverPrint.println(tShow.getText());
            tShow.setText("");
            try {
                receiveShow.setText(input.readLine());
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        //TODO: dodać pojedyncze przekłamanie bitów
        //TODO: dodać zmiane stringa na bity i na odwrót
        else if(source == bSendTwoErrors){
            serverPrint.println(tShow.getText());
            tShow.setText("");
            try {
                receiveShow.setText(input.readLine());
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } else if(source==bExit){

            serverPrint.println("someBad_AsS_Secur3d+Strin8!");

            try {
                socket.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            dispose();
        }
    }

    private int convertToDecimal(char[] binaryNumber) {
        int binary;
        //binaryNumber = swapChars(binaryNumber);
        String tempString ="";
        for(int i = 0; i<binaryNumber.length; i++){
            tempString = tempString + binaryNumber[i];
        }
        binary = Integer.valueOf(tempString);

        int result = 0;
        int multiplier = 1;

        while(binary > 0){
            int residue = binary % 10;
            binary     = binary / 10;
            result      = result + residue * multiplier;
            multiplier  = multiplier * 2;
        }

        System.out.println("whole result:");
        return result;
    }
}
