package homework.integration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.handler.LoggingHandler;
import org.springframework.integration.ip.dsl.Tcp;
import org.springframework.integration.ip.tcp.connection.MessageConvertingTcpMessageMapper;
import org.springframework.integration.ip.tcp.serializer.MapJsonSerializer;
import org.springframework.integration.support.converter.MapMessageConverter;

@EnableIntegration
@IntegrationComponentScan
@Configuration
@RequiredArgsConstructor
public class IntegrationConfig {

    private final TcpProperties properties;

    @Bean
    public IntegrationFlow server() {
        return IntegrationFlows.from(Tcp.inboundGateway(Tcp.netServer(properties.getPort())
                .deserializer(jsonMapping())
                .serializer(jsonMapping())
                .mapper(mapper())))
                .log(LoggingHandler.Level.INFO, "exampleLogger", "'Received type header:' + headers['type']")
                .route("headers['type']", r -> r
                        .subFlowMapping("upper",
                                subFlow -> subFlow.transform(String.class, String::toUpperCase))
                        .subFlowMapping("lower",
                                subFlow -> subFlow.transform(String.class, String::toLowerCase)))
                .get();
    }

    @Bean
    public MapJsonSerializer jsonMapping() {
        return new MapJsonSerializer();
    }

    @Bean
    public MessageConvertingTcpMessageMapper mapper() {
        MapMessageConverter converter = new MapMessageConverter();
        converter.setHeaderNames("type");
        return new MessageConvertingTcpMessageMapper(converter);
    }
}