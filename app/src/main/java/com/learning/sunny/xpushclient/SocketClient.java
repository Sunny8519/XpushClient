package com.learning.sunny.xpushclient;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 * @author NiYang
 * @Description:
 * @date 2017/11/21 13:15
 */

public class SocketClient {

    public static void main(String[] args) {
        SocketClient socketClient = new SocketClient();
        socketClient.start();
    }

    private void startServerReplyListener(final BufferedReader inputReader) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String receiveMsg;
                    while ((receiveMsg = inputReader.readLine()) != null) {
                        System.out.println(receiveMsg);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void start() {
        BufferedReader consoleReader = null;
        BufferedWriter outputWriter = null;
        BufferedReader inputReader = null;
        Socket socket = null;
        try {
            consoleReader = new BufferedReader(new InputStreamReader(System.in));
            socket = new Socket("127.0.0.1", 9898);
            outputWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            inputReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            startServerReplyListener(inputReader);
            String text;
            while (!"bye".equals(text = consoleReader.readLine())) {
                outputWriter.write(text + "\n");
                outputWriter.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (consoleReader != null) {
                    consoleReader.close();
                }
                if (outputWriter != null) {
                    outputWriter.close();
                }
                if (inputReader != null) {
                    inputReader.close();
                }
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
