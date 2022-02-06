package client.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("tcp")
public class TcpProperties {

    private String host;
    private int port;
}
