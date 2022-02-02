package client;


import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class TcpClientApplication {

    public static void main(String[] args) {
//        var ctx = SpringApplication.run(TcpClientApplication.class, args);
//        IntegrationConfig.TcpExchanger exchanger = ctx.getBean(IntegrationConfig.TcpExchanger.class);
//        String result = exchanger.exchange("Blblaba",
//                Character.isLowerCase("Bkbahd".charAt(0)) ? "upper" : "lower");

        try {
            try (Socket clientSocket = new Socket("localhost", 8082)) {
                PrintWriter outputStream = new PrintWriter(clientSocket.getOutputStream(), true);

                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                for (int idx = 0; idx < 3; idx++) {
                    String msg = String.format("##%d - I Believe", idx);
                    System.out.printf("sending to server: %s%n", msg);
                    outputStream.println(msg);

                    String responseMsg = in.readLine();
                    System.out.printf("server response: %s%n", responseMsg);
                    Thread.sleep(TimeUnit.SECONDS.toMillis(3));

                    System.out.println();
                }

                System.out.println("\nstop communication");
                outputStream.println("stop");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
