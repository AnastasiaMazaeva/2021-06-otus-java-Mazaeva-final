package client;

import client.model.Model;
import client.properties.TcpProperties;
import client.utils.FileUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Slf4j
public class ClientSocket {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String token;

    public ClientSocket(String token) {
        this.token = token;
    }

    public void go(String directory, TcpProperties tcpProperties) {
        try {
            try (Socket clientSocket = new Socket(tcpProperties.getHost(), tcpProperties.getPort())) {
                PrintWriter outputStream = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                Path path = Paths.get(directory);
                List<Model> models = FileUtils.handle(token, path);
                log.debug("{}", models);
                if (!models.isEmpty()) {
                    outputStream.println(objectMapper.writeValueAsString(models));
                    String responseMsg = in.readLine();
                    log.debug("server response: {}", responseMsg);
                } else {
                    log.debug("Directory is empty");
                }
            }
        } catch (Exception ex) {
            log.error("Ошибка обмена: ", ex);
        }
    }
}
