package com.wedul.circuit.config;

import com.wedul.circuit.dto.request.WedulRequest;
import com.wedul.circuit.dto.response.WedulResponse;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.reactor.circuitbreaker.operator.CircuitBreakerOperator;
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
public class WedulClient {

    private final WedulClientProperties properties;
    private final CircuitBreaker circuitBreaker;

    public WedulClient(WedulClientProperties properties, CircuitBreaker circuitBreaker) {
        this.properties = properties;
        this.circuitBreaker = circuitBreaker;
    }

public Mono<WedulResponse> isWedulExist(WedulRequest request) {
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
        .uri(uriBuilder -> uriBuilder.path("/result")
            .queryParam("name", request.getName())
            .queryParam("price", request.getPrice())
            .build()
        )
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .bodyToMono(WedulResponse.class)
//        .transform(CircuitBreakerOperator.of(circuitBreaker))
        .timeout(Duration.ofMillis(properties.getTimeout()))
        .doOnError(error -> {
            log.error("에러 발생!!!", error);
        });
}

}
