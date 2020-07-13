package com.wedul.circuit.config;

import com.wedul.circuit.dto.request.WedulRequest;
import com.wedul.circuit.dto.response.WedulSpaceResponse;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.concurrent.TimeUnit;


@Slf4j
@Component
public class WedulTestClient {

    private final WedulTestClientProperties properties;

    public WedulTestClient(WedulTestClientProperties properties) {
        this.properties = properties;
    }

    public Mono<WedulSpaceResponse> isWedulTestExist(WedulRequest request) {
        return WebClient.builder()
            .clientConnector(new ReactorClientHttpConnector(HttpClient.create()
                .tcpConfiguration(tcpClient ->
                    tcpClient.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, properties.getConnectTimeout())
                        .doOnConnected(conn -> conn
                            .addHandlerLast(new ReadTimeoutHandler(properties.getSocketTimeout(), TimeUnit.MILLISECONDS))
                            .addHandlerLast(new WriteTimeoutHandler(properties.getSocketTimeout(), TimeUnit.MILLISECONDS))
                        )
                )
            ))
            .uriBuilderFactory(new DefaultUriBuilderFactory(
                UriComponentsBuilder
                    .newInstance()
                    .scheme("http")
                    .host(properties.getUrl())
                    .port(properties.getPort())))
            .build()
            .get()
            .uri(uriBuilder -> uriBuilder.path("/item/tech/personal")
                .queryParam("page", 0)
                .queryParam("size", 5)
                .build()
            )
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(WedulSpaceResponse.class)
            .timeout(Duration.ofMillis(properties.getTimeout()))
            .doOnError(error -> log.error("wedul space api 에러 발생!!!", error))
            .onErrorReturn(WedulSpaceResponse.builder().page(0).build());
    }

}
