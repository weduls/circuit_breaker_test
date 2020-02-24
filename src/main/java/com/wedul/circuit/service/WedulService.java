package com.wedul.circuit.service;

import com.wedul.circuit.config.WedulClient;
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

    public Mono<WedulResponse> circuitTest(WedulRequest request) {
        return wedulClient.isWedulExist(request);
    }

}
