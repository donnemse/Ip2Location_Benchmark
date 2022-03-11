package com.yuganji;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class IpLocation implements Comparable<Long> {
    private long start;
    private long end;
    private String code;
    private String country;

    public int compareTo(Long l) {
        if (l < start)
            return 1;
        if (l >= start && l <= end)
            return 0;
        else
            return -1;
    }
}
