package com.wedul.circuit.controller;

import com.wedul.circuit.dto.request.WedulRequest;
import com.wedul.circuit.service.WedulService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("/circuit")
@RequiredArgsConstructor
public class CircuitController {

    private final WedulService wedulService;

    @GetMapping("/test")
    public Mono<ResponseEntity> circuitTest(@Valid WedulRequest wedulRequest) {
        return wedulService.circuitTest(wedulRequest)
            .map(ResponseEntity::ok);
    }

}
