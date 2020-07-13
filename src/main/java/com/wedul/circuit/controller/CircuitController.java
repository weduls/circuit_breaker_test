package com.wedul.circuit.controller;

import com.wedul.circuit.dto.request.WedulRequest;
import com.wedul.circuit.service.WedulService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/circuit")
@RequiredArgsConstructor
public class CircuitController {

    private final WedulService wedulService;

    @GetMapping("/test")
    public Mono<ResponseEntity> circuitTest(@Valid WedulRequest wedulRequest) {
        return wedulService.circuitTest(wedulRequest)
            .doOnError(e ->
                log.error("에러가 발생했다!!!", e)
            )
            .map(ResponseEntity::ok);
    }

}
