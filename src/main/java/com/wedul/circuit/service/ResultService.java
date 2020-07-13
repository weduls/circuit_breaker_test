package com.wedul.circuit.service;

import com.wedul.circuit.dto.request.WedulRequest;
import com.wedul.circuit.dto.response.WedulResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class ResultService {

    public Mono<WedulResponse> result(WedulRequest request) throws InterruptedException {
        if (!request.getName().equals("wedul")) {
            throw new RuntimeException("error");
        }

        Thread.sleep(5000);
        return Mono.just(WedulResponse.builder().type("normal").isExist(true).build());
    }

    public Mono<WedulResponse> resultTest(WedulRequest request) {
        return Mono.just(WedulResponse.builder().type("normal").isExist(true).build());
    }

}
