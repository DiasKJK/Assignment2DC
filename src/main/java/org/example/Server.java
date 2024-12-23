package org.example;

import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) {
        int port = 3000;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Running on port " + port);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket.getInetAddress());
                new Thread(new ClientHandler(clientSocket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ClientHandler implements Runnable {
        private final Socket clientSocket;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        @Override
        public void run() {
            try (
                    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)
            ) {
                String input;
                while ((input = in.readLine()) != null) {
                    if (input.equalsIgnoreCase("exit")) {
                        out.println("Goodbye!");
                        break;
                    }

                    String response = processInput(input);
                    out.println(response);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private String processInput(String input) {
            try {
                int number = Integer.parseInt(input);
                return "Processed number: " + (number + 1);
            } catch (NumberFormatException e) {
                return "Processed string: " + input.toUpperCase();
            }
        }
    }
}