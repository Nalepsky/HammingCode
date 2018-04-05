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
    JLabel lShow;
    JTextField tShow;


    public Window(Socket socket) throws IOException {
        this.socket = socket;
        this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        serverPrint = new PrintStream(socket.getOutputStream());

        setSize(340, 250);
        setTitle("Hamming Code");
        setLayout(null);

        tShow = new JTextField();
        tShow.setBounds(70, 20, 200, 20);
        tShow.setToolTipText("Enter text to check");
        add(tShow);

        lShow = new JLabel("TEXT");
        lShow.setBounds(70, 50, 200, 20);
        lShow.setForeground(Color.BLUE);
        lShow.setFont(new Font("SansSerif", Font.BOLD, 10));
        add(lShow);

        bSendCorrect = new JButton("Send correctly");
        bSendCorrect.setBounds(70,80,200,20);
        add(bSendCorrect);
        bSendCorrect.addActionListener(this);

        bSendOneError = new JButton("Send 1 error");
        bSendOneError.setBounds(70,110,200,20);
        add(bSendOneError);
        bSendOneError.addActionListener(this);

        bSendTwoErrors = new JButton("Send 2 errors");
        bSendTwoErrors.setBounds(70,140,200,20);
        add(bSendTwoErrors);
        bSendTwoErrors.addActionListener(this);

        bExit = new JButton("Exit");
        bExit.setBounds(70,170, 200,20);
        add(bExit);
        bExit.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        Object source = e.getSource();

        //TODO: dodać zmiane stringa na bity i na odwrót
        if(source== bSendCorrect) {
            serverPrint.println(tShow.getText());
            tShow.setText("");
            try {
                lShow.setText(input.readLine());
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
                lShow.setText(input.readLine());
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
                lShow.setText(input.readLine());
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
}
