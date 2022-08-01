package sample;

import javafx.scene.layout.VBox;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class Client {

    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private final Encryption encryption;

    public Client() {
        encryption = new Encryption();
    }

    public void connectionSocket(Socket socket) {
        try {
            this.socket = socket;
            ArrayList<Character> key;
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            Object object = objectInputStream.readObject();
            key = (ArrayList<Character>) object;
            encryption.setKey(key);
            System.out.println("> Received encryption key from server.");

            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("> Failed to create Client");
            e.printStackTrace();
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    public void sendMessageToServer(String messageToServer) {
        try{
            String encryptedMessage = encryption.encrypt(messageToServer);
            bufferedWriter.write(encryptedMessage);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException e) {
            //e.printStackTrace();
            System.out.println("> Failed to send message to Server. Stream closed.");
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    public void receiveMessageFromServer(VBox vBox) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (socket.isConnected()) {
                    try {
                        String encryptedMessage = bufferedReader.readLine();
                        String messageFromServer = encryption.decrypt(encryptedMessage);
                        Controller.addLabel(messageFromServer, vBox);
                    } catch (IOException e) {
                        System.out.println("> Connection socket is closed.");
                        closeEverything(socket, bufferedReader, bufferedWriter);
                        break;
                    }
                }
            }
        }).start();
    }

    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        try {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeSocket(Socket socket) {
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
