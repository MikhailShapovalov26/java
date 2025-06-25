package ru.netology;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Main {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(60);

        try (final var serverSocket = new ServerSocket(9999)) {

            while (!Thread.currentThread().isInterrupted()) {
                Socket clientSocket = serverSocket.accept();
                executor.submit(new ServerThread(clientSocket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            executor.shutdown();
        }
    }
}