package client;


import client.properties.TcpProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@Slf4j
@ConfigurationPropertiesScan
@SpringBootApplication
public class TcpClientApplication {

    public static void main(String[] args) {
        var ctx = SpringApplication.run(TcpClientApplication.class, args);
        String path = ctx.getEnvironment().getProperty("pth");
        String token = ctx.getEnvironment().getProperty("token");
        TcpProperties properties = ctx.getBean(TcpProperties.class);
        ClientSocket clientSocket = new ClientSocket(token);
        clientSocket.go(path, properties);
        ctx.close();
    }

}
