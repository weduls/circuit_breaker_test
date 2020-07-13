package com.wedul.circuit.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class WedulSpaceResponse {
    private int page;
    private boolean last;

    @Builder
    public WedulSpaceResponse(int page, boolean last) {
        this.page = page;
        this.last = last;
    }
}
