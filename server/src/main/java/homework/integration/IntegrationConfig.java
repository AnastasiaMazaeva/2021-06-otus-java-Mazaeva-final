package homework.integration;

import homework.service.interfaces.IntegrationService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class IntegrationConfig {

    private final TcpProperties properties;
    private final IntegrationService integrationService;

    @Bean(initMethod = "start", destroyMethod = "shutdown")
    public Server server() throws IOException {
        Server server = ServerBuilder
                .forPort(properties.getPort())
                .addService(new DocumentService(integrationService))
                .build();
        //server.start();
        return server;
    }

}