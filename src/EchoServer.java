import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {
    public static void main(String[] args){
        final int PORT = 12345;
        try(ServerSocket serverSocket = new ServerSocket(PORT)){
            System.out.println("System listing to the Port..." + PORT);
            while(true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Accepted Connection From: " + clientSocket.getInetAddress() + " : " + clientSocket.getPort());
                new Thread(() -> {
                    try {
                        clientSocket.close();
                    } catch (IOException e){
                        e.printStackTrace();
                    }
                }).start();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    private static void handleClient(Socket clientSocket){
        try(
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        ){
            String line;
            while((line = in.readLine()) != null){
                out.println(line); //echo back
            }
            System.out.println("Client Disconneected");
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            try {clientSocket.close(); } catch (IOException ignored) {}
        }
    }
}
// Here's the comment