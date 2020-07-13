package com.wedul.circuit.dto.response;

import lombok.*;

@Getter
@ToString
@NoArgsConstructor
public class WedulResponse {

    private String type;
    private boolean isExist;

    @Builder
    public WedulResponse(String type, boolean isExist) {
        this.type = type;
        this.isExist = isExist;
    }

}
