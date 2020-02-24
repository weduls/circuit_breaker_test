package com.wedul.circuit.service;

import com.wedul.circuit.dto.request.WedulRequest;
import com.wedul.circuit.dto.response.WedulResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class ResultService {

    public Mono<WedulResponse> result(WedulRequest request) {
        if (!request.getName().equals("wedul")) {
            throw new RuntimeException("error");
        }

        return Mono.just(WedulResponse.builder().isExist(true).build());
    }

}
