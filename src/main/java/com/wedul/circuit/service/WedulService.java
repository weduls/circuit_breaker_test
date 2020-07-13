package com.wedul.circuit.service;

import com.wedul.circuit.config.WedulClient;
import com.wedul.circuit.config.WedulTestClient;
import com.wedul.circuit.dto.request.WedulRequest;
import com.wedul.circuit.dto.response.WedulResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class WedulService {

    private final WedulClient wedulClient;
    private final WedulTestClient wedulTestClient;

    public Mono<WedulResponse> circuitTest(WedulRequest request) {
        return Mono.zip(
            wedulClient.isWedulExist(request)
                .doOnError(e -> log.error("service error", e))
                .defaultIfEmpty(WedulResponse.builder().type("Error Return").build())
                .onErrorReturn(WedulResponse.builder().type("Error Return").build()),
            wedulTestClient.isWedulTestExist(request)
        ).map(
            d -> {
                System.out.println(d.getT2().getPage());
                System.out.println(d.getT1().getType());
                return WedulResponse.builder().type(String.valueOf(d.getT2().getPage())).isExist(d.getT1().isExist()).build();
            }
        ).doOnError(e -> log.error("error {}", e));
    }

}
