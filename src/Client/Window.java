package Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.net.Socket;
import java.net.SocketException;
import java.util.Random;

public class Window extends JFrame implements ActionListener {
    Socket socket;
    BufferedReader input;
    PrintStream serverPrint;
    JButton bSendCorrect;
    JButton bSendOneError;
    JButton bSendTwoErrors;
    JButton bExit;
    JLabel receiveShow;
    JLabel titleShow;
    JLabel sentShow;
    JTextField tShow;


    public Window(Socket socket) throws IOException {
        this.socket = socket;
        this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        serverPrint = new PrintStream(socket.getOutputStream());

        setSize(500, 320);
        setTitle("Hamming Code");
        setLayout(null);

        titleShow = new JLabel("Type binary number:");
        titleShow.setBounds(70, 20, 360, 20);
        titleShow.setForeground(Color.BLUE);
        titleShow.setFont(new Font("SansSerif", Font.BOLD, 10));
        add(titleShow);

        tShow = new JTextField();
        tShow.setBounds(70, 50, 360, 20);
        tShow.setToolTipText("Enter text to check");
        add(tShow);

        sentShow = new JLabel("Number sent: ");
        sentShow.setBounds(70, 80, 360, 20);
        sentShow.setForeground(Color.BLUE);
        sentShow.setFont(new Font("SansSerif", Font.BOLD, 10));
        add(sentShow);

        receiveShow = new JLabel("Number received: ");
        receiveShow.setBounds(70, 110, 360, 20);
        receiveShow.setForeground(Color.BLUE);
        receiveShow.setFont(new Font("SansSerif", Font.BOLD, 10));
        add(receiveShow);

        bSendCorrect = new JButton("Send correctly");
        bSendCorrect.setBounds(70, 140, 360, 20);
        add(bSendCorrect);
        bSendCorrect.addActionListener(this);

        bSendOneError = new JButton("Send 1 error");
        bSendOneError.setBounds(70, 170, 360, 20);
        add(bSendOneError);
        bSendOneError.addActionListener(this);

        bSendTwoErrors = new JButton("Send 2 errors");
        bSendTwoErrors.setBounds(70, 200, 360, 20);
        add(bSendTwoErrors);
        bSendTwoErrors.addActionListener(this);

        bExit = new JButton("Exit");
        bExit.setBounds(70, 230, 360, 20);
        add(bExit);
        bExit.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        HammingCodeCreator hammingCodeCreator = new HammingCodeCreator("");
        Random random = new Random();
        Object source = e.getSource();

        if (source == bSendCorrect) {

            String message = tShow.getText();
            if (validateInput(message)) {
                if(validateBigInput(message)) {

                    message = hammingCodeCreator.createHammingCode(message);
                    serverPrint.println(message);

                    sentShow.setText("Number sent: " + convertToDecimal(tShow.getText()));

                    displayMessage(message);
                }
                else {
                    sentShow.setText("ENTER BINARY NUMBER WITH MAXIMUM 10 DIGITS");
                }
            } else {
                sentShow.setText("ENTER CORRECT BINARY NUMBER!");
            }
        } else if (source == bSendOneError) {

            String message = tShow.getText();

            if (validateInput(message)) {
                if(validateBigInput(message)) {

                    message = hammingCodeCreator.createHammingCode(message);

                    int rand = random.nextInt(message.length() - 1) + 1;
                    message = changeBit(message, rand);
                    serverPrint.println(message);


                    sentShow.setText("Number sent: " + convertToDecimal(tShow.getText()) + " with error on " + rand + "th bit");

                    displayMessage(message);
                }
                else {
                    sentShow.setText("ENTER BINARY NUMBER WITH MAXIMUM 10 DIGITS");
                }
            } else {
                sentShow.setText("ENTER CORRECT BINARY NUMBER!");
            }
        } else if (source == bSendTwoErrors) {

            String message = tShow.getText();

            if (validateInput(message)) {
                if(validateBigInput(message)) {
                    message = hammingCodeCreator.createHammingCode(message);

                    int rand = random.nextInt(message.length() - 1) + 1;
                    int rand2;
                    do {
                        rand2 = random.nextInt(message.length() - 1) + 1;
                    } while (rand2 == rand);

                    message = changeBit(message, rand);
                    message = changeBit(message, rand2);
                    serverPrint.println(message);

                    sentShow.setText("Number sent: " + convertToDecimal(tShow.getText()) + " with error on " + rand + "th and " + rand2 + "th bits");

                    displayMessage(message);
                }
                else {
                    sentShow.setText("ENTER BINARY NUMBER WITH MAXIMUM 10 DIGITS");
                }
            } else {
                sentShow.setText("ENTER CORRECT BINARY NUMBER!");
            }
        } else if (source == bExit) {

            serverPrint.println("someBad_AsS_Secur3d+Strin8!");

            try {
                socket.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            dispose();
        }
    }

    private boolean validateInput(String message) {
        if (message.length() < 1)
            return false;

        char[] bits = message.toCharArray();

        for (char i : bits) {
            if (i == '0' || i == '1')
                continue;
            return false;
        }
        return true;
    }

    private boolean validateBigInput(String message) {
        return message.length() <= 10;
    }

    private String changeBit(String message, int rand) {
        char[] bits = message.toCharArray();
        String result = "";

        if (bits[bits.length - rand] == '1')
            bits[bits.length - rand] = '0';
        else
            bits[bits.length - rand] = '1';

        for (int i = 0; i < bits.length; i++) {
            result = result + bits[i];
        }
        return result;
    }

    private void displayMessage(String message) {
        HammingCodeCreator hammingCodeCreator = new HammingCodeCreator("");
        tShow.setText("");
        try {
            String answer = hammingCodeCreator.deleteControlBits(input.readLine());
            int errorNumber = Integer.parseInt(input.readLine());
            if (errorNumber == 0) {
                receiveShow.setText("Number received: " + convertToDecimal(answer) + " with no error found");
            } else if (errorNumber == -1) {
                receiveShow.setText("ERROR");
            } else {
                receiveShow.setText("Number received: " + convertToDecimal(answer) + " with error found on " + errorNumber + "th bit");
            }
        } catch (SocketException se) {
            receiveShow.setText("SocketException");
        } catch (IOException e1) {
            receiveShow.setText("IOException");
        } catch (NumberFormatException nfe) {
            receiveShow.setText("NumberFormatException");
        }
    }

    private int convertToDecimal(String stringNumber) {
        int binary;

        binary = Integer.valueOf(stringNumber);

        int result = 0;
        int multiplier = 1;

        while (binary > 0) {
            int residue = binary % 10;
            binary = binary / 10;
            result = result + residue * multiplier;
            multiplier = multiplier * 2;
        }


        return result;
    }
}
