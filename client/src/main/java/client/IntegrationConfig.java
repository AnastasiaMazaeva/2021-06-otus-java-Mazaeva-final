package client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.ip.dsl.Tcp;
import org.springframework.integration.ip.tcp.connection.MessageConvertingTcpMessageMapper;
import org.springframework.integration.ip.tcp.serializer.MapJsonSerializer;
import org.springframework.integration.support.converter.MapMessageConverter;
import org.springframework.messaging.handler.annotation.Header;

@EnableIntegration
@IntegrationComponentScan
@Configuration
public class IntegrationConfig {


    public interface TcpExchanger {

        String exchange(String data, @Header("type") String type);

    }

    @Bean
    public IntegrationFlow client(@Value("${tcp.port:8082}") int port) {
        return IntegrationFlows.from(TcpExchanger.class)
                .handle(Tcp.outboundGateway(Tcp.netClient("localhost", port)
                        .deserializer(jsonMapping())
                        .serializer(jsonMapping())
                        .mapper(mapper())))
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