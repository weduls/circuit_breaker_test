package com.wedul.circuit.controller;

import com.wedul.circuit.dto.request.WedulRequest;
import com.wedul.circuit.dto.response.WedulResponse;
import com.wedul.circuit.service.ResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class ResultController {

    private final ResultService service;

    @GetMapping("/result")
    public Mono<WedulResponse> result(WedulRequest wedulRequest) {
        return service.result(wedulRequest);
    }

}
