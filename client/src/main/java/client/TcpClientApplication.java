package client;


import client.properties.ClientProperties;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class TcpClientApplication {

    public static void main(String[] args) throws IOException {
        ClientProperties properties = new ClientProperties();
        GrpcClient client = new GrpcClient(properties);
        client.go();
    }

}
