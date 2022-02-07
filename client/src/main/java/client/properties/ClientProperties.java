package client.properties;

import lombok.Data;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

@Data
public class ClientProperties {

    private String host;
    private int port;
    private String token;
    private String path;

    public ClientProperties() throws IOException {
        try (FileInputStream iis = new FileInputStream("./application.properties")) {
            Properties properties = new Properties();
            properties.load(iis);
            this.host = properties.getProperty("tcp.host");
            this.port = Integer.parseInt(properties.getProperty("tcp.port"));
            this.token = properties.getProperty("token");
            this.path = properties.getProperty("pth");
        }
    }
}
