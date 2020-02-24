package com.wedul.circuit.dto.response;

import lombok.*;

@Getter
@ToString
@NoArgsConstructor
public class WedulResponse {

    private boolean isExist;

    @Builder
    public WedulResponse(boolean isExist) {
        this.isExist = isExist;
    }

}
