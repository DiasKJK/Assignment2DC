package org.example;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        String host = "localhost";
        int port = 3000;

        try (Socket socket = new Socket(host, port);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("Connected to the server on " + host + ":" + port);

            String input;
            while (true) {
                System.out.print("Enter a number, a string, or 'exit' to quit: ");
                input = scanner.nextLine();
                out.println(input);

                if (input.equalsIgnoreCase("exit")) {
                    System.out.println("Exiting...");
                    break;
                }

                String response = in.readLine();
                System.out.println("Response from server: " + response);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}