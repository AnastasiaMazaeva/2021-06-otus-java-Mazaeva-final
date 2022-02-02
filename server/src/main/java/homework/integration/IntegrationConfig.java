package homework.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import homework.service.interfaces.IntegrationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.ip.dsl.Tcp;
import org.springframework.integration.ip.dsl.TcpServerConnectionFactorySpec;
import org.springframework.integration.ip.tcp.serializer.TcpCodecs;
import org.springframework.messaging.MessageHeaders;

import java.io.IOException;

@Slf4j
@Configuration
@EnableIntegration
@RequiredArgsConstructor
public class IntegrationConfig {

    private final TcpProperties properties;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final IntegrationService integrationService;


    @Bean
    public IntegrationFlow server(ServerSocketHandler serverSocketHandler) {
        TcpServerConnectionFactorySpec connectionFactory =
                Tcp.netServer(properties.getPort())
                        .deserializer(TcpCodecs.crlf(10485760))
                        .serializer(TcpCodecs.crlf(10485760))
                        .soTcpNoDelay(true);

        return IntegrationFlows
                .from(Tcp.inboundGateway(connectionFactory))
                .handle(serverSocketHandler::handleMessage)
                .get();
    }

    @Bean
    public ServerSocketHandler serverSocketHandler() {
        return new ServerSocketHandler();
    }

    public class ServerSocketHandler {

        public String handleMessage(byte[] message, MessageHeaders messageHeaders) {
            try {
                Model[] models = objectMapper.readValue(message, Model[].class);
                integrationService.handleIncome(models);
            } catch (IOException e) {
                log.error("Ошибка обработки входящего сообщения", e);
            }
            return "Message handled";
        }
    }

}