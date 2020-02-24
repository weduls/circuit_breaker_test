package com.wedul.circuit.dto.request;

import lombok.*;

@ToString
@Getter
@Setter
public class WedulRequest {

    private String name;
    private Integer price;

    @Builder
    public WedulRequest(String name, Integer price) {
        this.name = name;
        this.price = price;
    }

}
